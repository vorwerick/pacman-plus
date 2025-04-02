package cz.pacmanplus.game.core.systems.update

import com.artemis.Aspect
import com.artemis.Entity
import com.artemis.systems.EntityProcessingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Rectangle
import cz.pacmanplus.game.GameState
import cz.pacmanplus.game.core.components.attributes.*
import cz.pacmanplus.game.core.components.physics.HitPoint
import cz.pacmanplus.game.core.components.physics.HitPointsComponent
import cz.pacmanplus.game.core.components.physics.PositionComponent
import cz.pacmanplus.game.core.components.physics.RectangleCollisionComponent
import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.LoggerFactory

class AffectionSystem :
    EntityProcessingSystem(
        Aspect.all(
             ActivateComponent::class.java,
        )
    ) {
    val log = LoggerFactory.getLogger("ClockSystem")


    override fun process(e: Entity?) {
        if (e == null) return

        val gameState: GameState = getKoin().get()
        if (gameState.paused) {
            return
        }

        e.let { entity: Entity ->

            val activateComponent = entity.getComponent(ActivateComponent::class.java)
            val triggerables =
                world.aspectSubscriptionManager.get(Aspect.all(TriggerableComponent::class.java)).entities(world)
            triggerables.forEach { trigger ->
              trigger.getComponent(ActivateComponent::class.java)?.let { ac ->
                  if( activateComponent.activated){
                      ac.activated = true
                  } else {
                      ac.activated = false
                  }
                }


            }
        }
    }


}

