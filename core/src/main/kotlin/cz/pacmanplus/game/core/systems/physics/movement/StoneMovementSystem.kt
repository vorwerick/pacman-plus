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
import cz.pacmanplus.game.core.components.physics.*
import cz.pacmanplus.utils.delete
import org.slf4j.LoggerFactory

// movement for all movable stones
class StoneMovementSystem :
    EntityProcessingSystem(
        Aspect.all(
            MovementComponent::class.java,
            PushableComponent::class.java,
            PositionComponent::class.java,
            RectangleCollisionComponent::class.java,
        )
    ) {
    val log = LoggerFactory.getLogger("MovementSystem")


    override fun process(e: Entity?) {


        val friction = 1f
        e?.let { entity: Entity ->

            val rectangleCollisionSubscriber = world.aspectSubscriptionManager.get(
                Aspect.all(RectangleCollisionComponent::class.java, PositionComponent::class.java)
            )

            val rectCollisionComponent = entity.getComponent(RectangleCollisionComponent::class.java)
            val positionComponent = entity.getComponent(PositionComponent::class.java)
            val movementComponent = entity.getComponent(MovementComponent::class.java)
            if (rectCollisionComponent != null) {
                val pushableComponent = entity.getComponent(PushableComponent::class.java)
                val delta = Gdx.graphics.deltaTime
                val isPushable = pushableComponent != null
                if (isPushable) {
                    if (pushableComponent.pushDirection != Vector2.Zero) {
                        val newX = (pushableComponent.pushDirection.x) * 200 * Gdx.graphics.deltaTime
                        val newY = (pushableComponent.pushDirection.y) * 200 * Gdx.graphics.deltaTime


                        val collider: Collider? = getCollider(
                            rectangleCollisionSubscriber,
                            Rectangle(
                                positionComponent.x +newX,
                                positionComponent.y + newY,
                                rectCollisionComponent.width,
                                rectCollisionComponent.height
                            )
                        )
                        if (collider != null) {
                           // delete(entity.id, "Stone has collided with obstacle")
                        }


                        positionComponent.y += newY
                        positionComponent.x += newX


                    }

                    // println(pushableComponent.pushPotential)
                    //pushableComponent.pushPotential.coerceAtLeast(0f)
                }
            }


        }
    }

    private fun getCollider(
        rectangleCollisionSubscriber: EntitySubscription,
        rect: Rectangle,
    ): Collider? {

        var collider: Collider? = null

        (0 until rectangleCollisionSubscriber.entities.size()).forEach { index ->
            val id = rectangleCollisionSubscriber.entities.get(index)
            val rectangleEntity = world.getEntity(id)
            val rectangleCollisionComponent =
                rectangleEntity.getComponent(RectangleCollisionComponent::class.java)
            val rpComponent = rectangleEntity.getComponent(PositionComponent::class.java)


            val testRect = Rectangle(
                rpComponent.x,
                rpComponent.y,
                rectangleCollisionComponent.width,
                rectangleCollisionComponent.height
            )

            if (Intersector.overlaps(testRect, rect) && rectangleCollisionComponent.solid) {
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


}

