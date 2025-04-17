package cz.pacmanplus.game.core.systems.physics.movement

import com.artemis.Aspect
import com.artemis.Entity
import com.artemis.EntitySubscription
import com.artemis.systems.EntityProcessingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import cz.pacmanplus.game.GameState
import cz.pacmanplus.game.core.components.attributes.DirectionComponent
import cz.pacmanplus.game.core.components.attributes.SpeedComponent
import cz.pacmanplus.game.core.components.common.BaseComponent
import cz.pacmanplus.game.core.components.physics.*
import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.LoggerFactory

// movement for all projectiles
class ProjectileMovementSystem :
    EntityProcessingSystem(
        Aspect.all(
            SpeedComponent::class.java,
            DirectionComponent::class.java,
            PositionComponent::class.java,
            MovementComponent::class.java,
            RectangleCollisionComponent::class.java,
        )
    ) {
    val log = LoggerFactory.getLogger("ProjectileMovementSystem")


    override fun process(e: Entity?) {


        e?.let { entity: Entity ->

            val rectCollisionComponent = entity.getComponent(RectangleCollisionComponent::class.java)
            val name = entity.getComponent(BaseComponent::class.java)
            val positionComponent = entity.getComponent(PositionComponent::class.java)

            if (rectCollisionComponent != null) {
                val speedComponent = entity.getComponent(SpeedComponent::class.java)
                val directionComponent = entity.getComponent(DirectionComponent::class.java)

                val newX = (directionComponent.direction.x) * speedComponent.speed * Gdx.graphics.deltaTime
                val newY = (directionComponent.direction.y) * speedComponent.speed * Gdx.graphics.deltaTime

                positionComponent.y += newY
                positionComponent.x += newX
            }


        }
    }


}

