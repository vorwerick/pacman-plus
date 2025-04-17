package cz.pacmanplus.game.core.systems.lifecycle

import com.artemis.Aspect
import com.artemis.Entity
import com.artemis.systems.EntityProcessingSystem
import cz.pacmanplus.game.core.components.physics.*
import cz.pacmanplus.utils.delete
import org.slf4j.LoggerFactory

class LifecycleSystem :
    EntityProcessingSystem(
        Aspect.all(
            LifecycleComponent::class.java,
        )
    ) {
    val log = LoggerFactory.getLogger("HitpointsSystem")


    override fun process(e: Entity?) {

        e?.let { entity: Entity ->
            entity.getComponent(LifecycleComponent::class.java)?.let { lifecycle ->
                if (lifecycle.isDead()) {
                    delete(entity.id, "Entity's lifecycle was dead")
                }
            }
        }
    }
}
