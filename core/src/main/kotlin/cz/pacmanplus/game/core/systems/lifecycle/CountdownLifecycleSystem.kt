package cz.pacmanplus.game.core.systems.lifecycle

import com.artemis.Aspect
import com.artemis.Entity
import com.artemis.systems.EntityProcessingSystem
import com.badlogic.gdx.Gdx
import cz.pacmanplus.game.GameState
import cz.pacmanplus.game.core.components.attributes.DelayComponent
import cz.pacmanplus.game.core.components.attributes.LifespanComponent
import cz.pacmanplus.utils.delete
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


        e?.let { entity: Entity ->
            val delta = Gdx.graphics.deltaTime
            entity.getComponent(LifespanComponent::class.java)?.let { lifespan ->
                lifespan.spent()
                if(lifespan.isFinished()){
                    delete(entity.id, "Object lifespan has ended up")
                }
            }


        }
    }


}

