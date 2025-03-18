package cz.pacmanplus.game.core.systems

import com.artemis.Aspect
import com.artemis.Entity
import com.artemis.systems.EntityProcessingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Rectangle
import cz.pacmanplus.game.GameState
import cz.pacmanplus.game.core.components.physics.CircleCollisionComponent
import cz.pacmanplus.game.core.components.physics.MovementComponent
import cz.pacmanplus.game.core.components.physics.PositionComponent
import cz.pacmanplus.game.core.components.physics.RectangleCollisionComponent
import cz.pacmanplus.game.core.components.pickup.PickupComponent
import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.LoggerFactory
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sign
import kotlin.math.sin

class PickupSystem :
    EntityProcessingSystem(
        Aspect.all(
            MovementComponent::class.java,
            PositionComponent::class.java,
            CircleCollisionComponent::class.java
        )
    ) {
    val log = LoggerFactory.getLogger("MovementSystem")


    override fun process(e: Entity?) {

        val gameState = getKoin().get<GameState>()
        if (gameState.paused) {
            return
        }

        e?.let { entity: Entity ->
            val positionComponent = entity.getComponent(PositionComponent::class.java)
            val movementComponent = entity.getComponent(MovementComponent::class.java)
            val circleCollisionComponent = entity.getComponent(CircleCollisionComponent::class.java)

            val circle = Circle(positionComponent.x, positionComponent.y, circleCollisionComponent.radius)



            val pickupComponents = world.aspectSubscriptionManager.get(
                Aspect.all(
                    CircleCollisionComponent::class.java,
                    PositionComponent::class.java,
                    PickupComponent::class.java
                )
            )

            (0 until pickupComponents.entities.size()).forEach { index ->
                val id = pickupComponents.entities.get(index)
                val pickupEntity = world.getEntity(id)
                val pickupCircleComponent = pickupEntity.getComponent(CircleCollisionComponent::class.java)
                val pickupPositionComponent = pickupEntity.getComponent(PositionComponent::class.java)
                val pickupComponent = pickupEntity.getComponent(PickupComponent::class.java)
                val pickupCircle =
                    Circle(pickupPositionComponent.x, pickupPositionComponent.y, pickupCircleComponent.radius)

                if (Intersector.overlaps(circle, pickupCircle)) {
                    world.delete(id)
                }

            }

        }


    }
}
