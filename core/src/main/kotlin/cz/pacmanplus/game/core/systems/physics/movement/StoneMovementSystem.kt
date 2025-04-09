package cz.pacmanplus.game.core.systems.physics.movement

import com.artemis.Aspect
import com.artemis.Entity
import com.artemis.systems.EntityProcessingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import cz.pacmanplus.game.GameState
import cz.pacmanplus.game.core.components.physics.*
import org.koin.java.KoinJavaComponent.getKoin
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

            val rectCollisionComponent = entity.getComponent(RectangleCollisionComponent::class.java)
            val positionComponent = entity.getComponent(PositionComponent::class.java)
            val movementComponent = entity.getComponent(MovementComponent::class.java)
            if (rectCollisionComponent != null) {
                val pushableComponent = entity.getComponent(PushableComponent::class.java)
                val delta = Gdx.graphics.deltaTime
                val isPushable = pushableComponent != null
                if (isPushable) {
                    if (pushableComponent.pushDirection != Vector2.Zero) {
                      //  println("TAK CO JE")
                        positionComponent.x += (pushableComponent.pushDirection.x) * 200 * delta
                        positionComponent.y += (pushableComponent.pushDirection.y) * 200 * delta
                        // pushableComponent.pushDirection = Vector2.Zero
                    }

                    // println(pushableComponent.pushPotential)
                    //pushableComponent.pushPotential.coerceAtLeast(0f)
                }
            }


        }
    }


}

