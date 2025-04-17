package cz.pacmanplus.game.core.systems.physics.movement

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
import cz.pacmanplus.game.core.components.attributes.ButtonComponent
import cz.pacmanplus.game.core.components.attributes.InventoryComponent
import cz.pacmanplus.game.core.components.control.InputComponent
import cz.pacmanplus.game.core.components.physics.*
import cz.pacmanplus.utils.Coords
import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.LoggerFactory
import kotlin.math.abs
import kotlin.math.sign

//TODO refactor, split input system and movement system - movement for all MovementComponent entities
class MovementSystem :
    EntityProcessingSystem(
        Aspect.all(
            InputComponent::class.java,
            MovementComponent::class.java,
            PositionComponent::class.java,
            CircleCollisionComponent::class.java
        )
    ) {
    val log = LoggerFactory.getLogger("MovementSystem")


    override fun process(e: Entity?) {

        val friction = 1f
        e?.let { entity: Entity ->
            val delta = Gdx.graphics.deltaTime
            val positionComponent = entity.getComponent(PositionComponent::class.java)
            val movementComponent = entity.getComponent(MovementComponent::class.java)
            val inputComponent = entity.getComponent(InputComponent::class.java)

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
                        //ForcePushingComponent::class.java
                    )
                )


                val possibleDir = Coords(inputComponent.dir.x.toInt(), inputComponent.dir.y.toInt())

                val targetX = movementComponent.xTile * 32
                val targetY = movementComponent.yTile * 32

                val effect = getAreaEffectsOnPoint(areaEffects, positionComponent.x, positionComponent.y)
                if (effect != null) {
                    val ex = world.getEntity(effect.entityId)
                    val triggerable = ex.getComponent(ButtonComponent::class.java)
                    if (triggerable != null) {
                        triggerable.triggered = true
                    }
                }

                val effectForce = Vector2.Zero

                if (effect != null) {
                    if (effect is AreaEffect.PushForceEffect) {
                        val ef = (effect as AreaEffect.PushForceEffect)
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
                            //  possibleDir.y = ((diffToCurrentTile.y).sign.toInt() * -1)  causes wall trough move and stucking!!
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
                           //  possibleDir.x = ((diffToCurrentTile.x).sign.toInt() * -1)  causes wall trough move and stucking!!
                        } else {
                            possibleDir.x = 0
                            collider = colliderVertical
                        }
                        possibleDir.y = 0
                    }
                }
                entity.getComponent(PassiveAbilitiesComponent::class.java)?.let { passiveAbilities ->
                    if (collider != null) {
                        val en = world.getEntity(collider.entityId)
                        val pushableComponent =
                            world.getEntity(collider.entityId).getComponent(PushableComponent::class.java)


                        if (collider.isPushable) {
                            passiveAbilities.pushForce += delta * 30
                            passiveAbilities.pushForce = passiveAbilities.pushForce.coerceAtMost(100f);
                            if (passiveAbilities.pushForce >= 30f) {
                                passiveAbilities.pushForce = 0f
                                pushableComponent.pushDirection = Vector2(inputComponent.dir.x, inputComponent.dir.y)
                                println(pushableComponent.pushDirection)
                            }
                        }

                        if (collider.isUnlockable) {
                            passiveAbilities.unlockingValue += delta * 30
                            passiveAbilities.unlockingValue = passiveAbilities.unlockingValue.coerceAtMost(100f);
                            if (passiveAbilities.unlockingValue >= 20f) {
                                passiveAbilities.unlockingValue = 0f
                                val un = world.getEntity(collider.entityId)
                                un.getComponent(UnlockableComponent::class.java).availableKeys = entity.getComponent(InventoryComponent::class.java).keyring
                            }
                        }


                    } else {
                        passiveAbilities.unlockingValue -= delta * 100f
                        passiveAbilities.unlockingValue = passiveAbilities.unlockingValue.coerceAtLeast(0f);
                    }

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
                    rectangleEntity.getComponent(UnlockableComponent::class.java) != null,
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
    val isUnlockable: Boolean,
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
