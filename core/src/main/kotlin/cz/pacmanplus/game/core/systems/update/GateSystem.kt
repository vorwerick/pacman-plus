package cz.pacmanplus.game.core.systems.update

import com.artemis.Aspect
import com.artemis.Entity
import com.artemis.systems.EntityProcessingSystem
import com.badlogic.gdx.Gdx
import cz.pacmanplus.game.GameState
import cz.pacmanplus.game.core.components.attributes.*
import cz.pacmanplus.game.core.components.physics.RectangleCollisionComponent
import cz.pacmanplus.game.core.systems.physics.collisions.findSwitchablePoints
import cz.pacmanplus.game.core.systems.physics.collisions.findButtons
import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.LoggerFactory

class GateSystem :
    EntityProcessingSystem(
        Aspect.all(
            ActivateComponent::class.java, RectangleCollisionComponent::class.java,
        )
    ) {
    val log = LoggerFactory.getLogger("GateSystem")


    override fun process(e: Entity?) {



        e?.let { entity: Entity ->
            val delta = Gdx.graphics.deltaTime
            val activateComponent = entity.getComponent(ActivateComponent::class.java)
            val rectangleCollisionComponent = entity.getComponent(RectangleCollisionComponent::class.java)

            activateComponent?.let { activate ->
                val triggerables = world.findButtons()
                (0 until triggerables.size()).forEach { index ->

                    val id = triggerables.get(index)
                    val triggerable = world.getEntity(id)
                    triggerable.getComponent(ButtonComponent::class.java)?.let { tr ->
                        if (tr.groupId == activate.group) {
                            rectangleCollisionComponent.solid = !tr.triggered
                        }
                    }


                }

                val switchables = world.findSwitchablePoints()
                (0 until switchables.size()).forEach { index ->

                    val id = switchables.get(index)
                    val switchable = world.getEntity(id)
                    switchable.getComponent(SwitchableComponent::class.java)?.let { sw ->
                        if (sw.groupId == activate.group) {
                            rectangleCollisionComponent.solid = !sw.enabled
                        }
                    }


                }

            }

        }
    }


}

