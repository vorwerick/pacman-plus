package cz.pacmanplus.game.core.systems.update

import com.artemis.Aspect
import com.artemis.Entity
import com.artemis.systems.EntityProcessingSystem
import com.badlogic.gdx.Gdx
import cz.pacmanplus.game.GameState
import cz.pacmanplus.game.core.components.attributes.ActivateComponent
import cz.pacmanplus.game.core.components.attributes.DelayComponent
import cz.pacmanplus.game.core.components.attributes.LifespanComponent
import cz.pacmanplus.game.core.components.physics.RectangleCollisionComponent
import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.LoggerFactory

class GateSystem :
    EntityProcessingSystem(
        Aspect.all(
            ActivateComponent::class.java, RectangleCollisionComponent::class.java
        )
    ) {
    val log = LoggerFactory.getLogger("GateSystem")


    override fun process(e: Entity?) {

        val gameState: GameState = getKoin().get()
        if (gameState.paused) {
            return
        }


        e?.let { entity: Entity ->
            val delta = Gdx.graphics.deltaTime
            val activateComponent = e.getComponent(ActivateComponent::class.java)
            val rectangleCollisionComponent = e.getComponent(RectangleCollisionComponent::class.java)

            activateComponent?.let {
                rectangleCollisionComponent.solid = it.activated

            }

        }
    }


}

