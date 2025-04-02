package cz.pacmanplus.game.core.systems.lifecycle

import com.artemis.Aspect
import com.artemis.Entity
import com.artemis.systems.EntityProcessingSystem
import com.badlogic.gdx.Gdx
import cz.pacmanplus.game.GameState
import cz.pacmanplus.game.core.components.attributes.DelayComponent
import cz.pacmanplus.game.core.components.attributes.LifespanComponent
import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.LoggerFactory

class CountdownLifecycleSystem :
    EntityProcessingSystem(
        Aspect.one(
            DelayComponent::class.java, LifespanComponent::class.java,
        )
    ) {
    val log = LoggerFactory.getLogger("ClockSystem")


    override fun process(e: Entity?) {

        val gameState: GameState = getKoin().get()
        if (gameState.paused) {
            return
        }

        e?.let { entity: Entity ->
            val delta = Gdx.graphics.deltaTime
            entity.getComponent(DelayComponent::class.java)?.tick(delta)
            entity.getComponent(LifespanComponent::class.java)?.spent()

        }
    }


}

