package cz.pacmanplus.game.core.systems.physics

import com.artemis.Aspect
import com.artemis.Entity
import com.artemis.systems.EntityProcessingSystem
import com.badlogic.gdx.math.Vector2
import cz.pacmanplus.game.GameState
import cz.pacmanplus.game.core.components.physics.*
import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.LoggerFactory

class PushPhysicsSystem :
    EntityProcessingSystem(
        Aspect.all(
            PushableComponent::class.java,
            PositionComponent::class.java,
            RectangleCollisionComponent::class.java,
        )
    ) {
    val log = LoggerFactory.getLogger("MovementPhysicsSystem")


    override fun process(e: Entity?) {

        val gameState: GameState = getKoin().get()
        if (gameState.paused) {
            return
        }

        val friction = 1f
        e?.let { entity: Entity ->

            val rectCollisionComponent = entity.getComponent(RectangleCollisionComponent::class.java)
            val positionComponent = entity.getComponent(PositionComponent::class.java)
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


}

