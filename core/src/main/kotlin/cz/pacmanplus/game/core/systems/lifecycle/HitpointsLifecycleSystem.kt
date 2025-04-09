package cz.pacmanplus.game.core.systems.lifecycle

import com.artemis.Aspect
import com.artemis.Entity
import com.artemis.systems.EntityProcessingSystem
import cz.pacmanplus.game.GameState
import cz.pacmanplus.game.core.components.physics.*
import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.LoggerFactory

class HitpointsLifecycleSystem :
    EntityProcessingSystem(
        Aspect.all(
            HitPointsComponent::class.java,
        )
    ) {
    val log = LoggerFactory.getLogger("HitpointsSystem")


    override fun process(e: Entity?) {

        e?.let { entity: Entity ->
            val hitPointsComponent = entity.getComponent(HitPointsComponent::class.java)

            if(hitPointsComponent != null) {
                if(hitPointsComponent.state is HitPoint.Alive){
                    if((hitPointsComponent.state as HitPoint.Alive).value < 0){
                        hitPointsComponent.state = HitPoint.Dead
                    }
                }

                if( hitPointsComponent.state == HitPoint.Dead){
                    entity.deleteFromWorld()
                }
            }


        }


    }
}
