package cz.pacmanplus.game.core.systems.physics

import com.artemis.Aspect
import com.artemis.Entity
import com.artemis.EntitySubscription
import com.artemis.systems.EntityProcessingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import cz.pacmanplus.game.GameState
import cz.pacmanplus.game.core.components.attributes.TriggerableComponent
import cz.pacmanplus.game.core.components.control.ForcePushingComponent
import cz.pacmanplus.game.core.components.control.PlayerInputComponent
import cz.pacmanplus.game.core.components.physics.*
import cz.pacmanplus.utils.Coords
import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.LoggerFactory
import kotlin.math.abs

class MovementPhysicsSystem :
    EntityProcessingSystem(
        Aspect.all(
            MovementComponent::class.java,
            PositionComponent::class.java,
        ).one(RectangleCollisionComponent::class.java, CircleCollisionComponent::class.java)
    ) {
    val log = LoggerFactory.getLogger("MovementPhysicsSystem")


    override fun process(e: Entity?) {

        val gameState: GameState = getKoin().get()
        if (gameState.paused) {
            return
        }

        val friction = 1f
        e?.let { entity: Entity ->
            val delta = Gdx.graphics.deltaTime
            val positionComponent = entity.getComponent(PositionComponent::class.java)
            val movementComponent = entity.getComponent(MovementComponent::class.java)
            val inputComponent = entity.getComponent(PlayerInputComponent::class.java)
            val forcePushingComponent = entity.getComponent(ForcePushingComponent::class.java)
            val pushComponent = entity.getComponent(PushComponent::class.java)

            val circleCollisionComponent = entity.getComponent(CircleCollisionComponent::class.java)
            if (circleCollisionComponent != null) {

                circleCollisionComponent.colliding = false

                val circle = Circle(positionComponent.x, positionComponent.y, circleCollisionComponent.radius)

                movementComponent.xTile = ((positionComponent.x + 16) / 32).toInt()
                movementComponent.yTile = ((positionComponent.y + 16) / 32).toInt()


                val rectangleCollisionSubscriber = world.aspectSubscriptionManager.get(
                    Aspect.all(RectangleCollisionComponent::class.java, PositionComponent::class.java)
                )

                val areaEffects = world.aspectSubscriptionManager.get(
                    Aspect.all(
                        RectangleCollisionComponent::class.java,
                        PositionComponent::class.java,
                        ForcePushingComponent::class.java
                    )
                )


                val possibleDir = Coords(inputComponent.dir.x.toInt(), inputComponent.dir.y.toInt())

                val targetX = movementComponent.xTile * 32
                val targetY = movementComponent.yTile * 32

                val effect = getAreaEffectsOnPoint(areaEffects, positionComponent.x, positionComponent.y)
                if(effect != null){
                    println("CUCUC")
                   val ex =  world.getEntity(effect.entityId)
                   val triggerable = ex.getComponent(TriggerableComponent::class.java)
                    if(triggerable != null){
                        triggerable.triggered = true
                    }
                }

                val effectForce = Vector2.Zero

                if (effect != null) {
                    if (effect is AreaEffect.PushForceEffect) {
                        val ef =  (effect as AreaEffect.PushForceEffect)
                        effectForce.x = ef.x * ef.mass
                        effectForce.y = ef.y * ef.mass
                    }
                }

                val diffToCurrentTile = Vector2(
                    (targetX) - positionComponent.x,
                    (targetY) - positionComponent.y
                )

                var newY = movementComponent.yTile + possibleDir.y
                var newX = movementComponent.xTile + possibleDir.x

                val colliderHorizontal: Collider? = getCollider(
                    rectangleCollisionSubscriber,
                    (newX * 32f),
                    movementComponent.yTile * 32f,
                    circle
                )
                val colliderVertical: Collider? = getCollider(
                    rectangleCollisionSubscriber,
                    movementComponent.xTile * 32f,
                    (newY * 32f),
                    circle
                )

                var collider: Collider? = null

                if ((abs(diffToCurrentTile.x) <= 2)) {
                    if (colliderHorizontal != null) {
                        if (inputComponent.vertical && (colliderVertical == null)) {
                            //  possibleDir.y = ((diffToCurrentTile.y).sign.toInt() * -1)
                        } else {
                            possibleDir.y = 0
                            collider = colliderHorizontal
                        }
                        possibleDir.x = 0

                    }

                }
                if ((abs(diffToCurrentTile.y) <= 2)) {
                    if (colliderVertical != null) {
                        if (inputComponent.horizontal && (colliderHorizontal == null)) {
                            // possibleDir.x = ((diffToCurrentTile.x).sign.toInt() * -1)
                        } else {
                            possibleDir.x = 0
                            collider = colliderVertical
                        }
                        possibleDir.y = 0
                    }
                }

                if (collider != null) {
                    if (collider.isPushable) {

                        val en = world.getEntity(collider.entityId)
                        val pushableComponent = world.getEntity(collider.entityId).getComponent(PushableComponent::class.java)
                        if(pushComponent != null){
                            pushComponent.pushAmount += delta * pushComponent.pushForce
                            pushComponent.pushAmount = pushComponent.pushAmount.coerceAtMost(100f);
                            if(pushComponent.pushAmount >= 30f){
                                pushComponent.pushAmount = 0f

                                pushableComponent.pushDirection = Vector2(inputComponent.dir.x,inputComponent.dir.y)
                                println(pushableComponent.pushDirection)
                                //positionComponent.x += (pushableComponent.pushDirection.x) * 32
                                //positionComponent.y += (pushableComponent.pushDirection.y) * 32
                            }
                        }
                    }
                } else {
                    pushComponent.pushAmount -= delta * 100f
                    pushComponent.pushAmount = pushComponent.pushAmount.coerceAtLeast(0f);
                }



                newY = movementComponent.yTile + (possibleDir.y)
                newX = movementComponent.xTile + (possibleDir.x)

                if ((abs(diffToCurrentTile.x) <= 2)) {
                    if (colliderVertical == null) {
                        movementComponent.targetYTile = newY
                    }

                }
                if ((abs(diffToCurrentTile.y) <= 2)) {
                    if (colliderHorizontal == null) {
                        movementComponent.targetXTile = newX
                    }
                }


                val testTwo = Vector2(
                    (movementComponent.targetXTile * 32f) - positionComponent.x,
                    (movementComponent.targetYTile * 32f) - positionComponent.y
                )

                val tDir = testTwo.cpy().nor()

                val speed = if (possibleDir.x != 0 || possibleDir.y != 0) 140 else 0

                val moveVector = Vector2(tDir.x, tDir.y).scl(speed * delta)


                movementComponent.lastDir.x = possibleDir.x.toFloat()
                movementComponent.lastDir.y = possibleDir.y.toFloat()


                //input move force - grid based, obstacle based
                if (abs(testTwo.x) < 3f) {
                    positionComponent.y += moveVector.y

                }
                if (abs(testTwo.y) < 3f) {
                    positionComponent.x += moveVector.x
                }


            }

            val rectCollisionComponent = entity.getComponent(RectangleCollisionComponent::class.java)

            if (rectCollisionComponent != null) {
                val pushableComponent = entity.getComponent(PushableComponent::class.java)

                val isPushable = pushableComponent != null
                if (isPushable) {
                    if (pushableComponent.pushDirection != Vector2.Zero) {
                        println("TAK CO JE")
                        positionComponent.x += (pushableComponent.pushDirection.x) * 32
                        positionComponent.y += (pushableComponent.pushDirection.y) * 32
                       pushableComponent.pushDirection = Vector2.Zero
                    }

                    // println(pushableComponent.pushPotential)
                    //pushableComponent.pushPotential.coerceAtLeast(0f)
                }
            }


        }
    }

    fun resolveCircleRectangleCollision(circle: Circle, rect: Rectangle) {

    }

    private fun getAreaEffectsOnPoint(
        rectangleCollisionSubscriber: EntitySubscription,
        testX: Float,
        testY: Float,
    ): AreaEffect? {

        var effect: AreaEffect? = null

        (0 until rectangleCollisionSubscriber.entities.size()).forEach { index ->
            val id = rectangleCollisionSubscriber.entities.get(index)
            val rectangleEntity = world.getEntity(id)
            val rectangleCollisionComponent =
                rectangleEntity.getComponent(RectangleCollisionComponent::class.java)
            val rpComponent = rectangleEntity.getComponent(PositionComponent::class.java)


            val rect = Rectangle(
                rpComponent.x,
                rpComponent.y,
                rectangleCollisionComponent.width,
                rectangleCollisionComponent.height
            )



            if (rect.contains(testX, testY)) {
                val pfc = rectangleEntity.getComponent(PushableComponent::class.java)
                if (pfc != null) {
                    effect = AreaEffect.PushForceEffect(
                        entityId = id,
                        rect,
                        pfc.pushDirection,
                        pfc.mass
                    )
                }

            }


        }
        return effect
    }


    private fun getCollider(
        rectangleCollisionSubscriber: EntitySubscription,
        testX: Float,
        testY: Float,
        circle: Circle
    ): Collider? {

        var collider: Collider? = null

        (0 until rectangleCollisionSubscriber.entities.size()).forEach { index ->
            val id = rectangleCollisionSubscriber.entities.get(index)
            val rectangleEntity = world.getEntity(id)
            val rectangleCollisionComponent =
                rectangleEntity.getComponent(RectangleCollisionComponent::class.java)
            val rpComponent = rectangleEntity.getComponent(PositionComponent::class.java)


            val rect = Rectangle(
                rpComponent.x,
                rpComponent.y,
                rectangleCollisionComponent.width,
                rectangleCollisionComponent.height
            )

            if (rect.contains(testX, testY) && rectangleCollisionComponent.solid) {
                collider = Collider(
                    entityId = id,
                    rect,
                    rectangleEntity.getComponent(PushableComponent::class.java) != null,
                    rpComponent.x,
                    rpComponent.y
                )
            }


        }
        return collider
    }

    fun moveAxis(posAxis: Float, dirAxis: Float): Float {
        return (posAxis + (dirAxis * Gdx.graphics.deltaTime))
    }

    private fun testVertical(troughX: Boolean, troughY: Boolean) {

    }

    private fun testHorizontal(troughX: Boolean, troughY: Boolean) {

    }

    private fun canPass(circle: Circle, rectangle: Rectangle): Boolean {
        return !(Intersector.overlaps(
            circle,
            rectangle
        ))
    }
}

class Collider(
    val entityId: Int,
    val rectangle: Rectangle,
    val isPushable: Boolean,
    val x: Float = 0f,
    val y: Float = 0f
) {

}

sealed class AreaEffect(
    val entityId: Int,
    val rectangle: Rectangle,

    val x: Float = 0f,
    val y: Float = 0f
) {
    class PushForceEffect(entityId: Int, rectangle: Rectangle, val pushDirection: Vector2, val mass: Int) :
        AreaEffect(entityId, rectangle)

}
