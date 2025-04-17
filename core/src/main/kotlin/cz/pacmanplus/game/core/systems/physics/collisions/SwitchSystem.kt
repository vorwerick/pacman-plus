package cz.pacmanplus.game.core.systems.physics.collisions

import com.artemis.Aspect
import com.artemis.BaseSystem
import com.artemis.Entity
import com.artemis.World
import com.artemis.systems.EntityProcessingSystem
import com.artemis.utils.IntBag
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Vector2
import cz.pacmanplus.game.GameState
import cz.pacmanplus.game.core.components.attributes.PressureComponent
import cz.pacmanplus.game.core.components.attributes.SwitchableComponent
import cz.pacmanplus.game.core.components.graphics.DrawableStateComponent
import cz.pacmanplus.game.core.components.physics.*
import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.LoggerFactory

/// For entities who can switch
class SwitchSystem : EntityProcessingSystem(
    Aspect.one(CircleCollisionComponent::class.java).all(PositionComponent::class.java, PressureComponent::class.java)
) {
    val log = LoggerFactory.getLogger("SwitchSystem")


    override fun process(e: Entity?) {

        e?.let { entity: Entity ->
            val positionComponent = entity.getComponent(PositionComponent::class.java)
            val switchables = world.findSwitchablePoints()

            (0 until switchables.size()).forEach { index ->
                val id = switchables.get(index)
                val switchable = world.getEntity(id)
                val switchablePosition = switchable.getComponent(PositionComponent::class.java)

                entity.getComponent(CircleCollisionComponent::class.java)?.let { circle ->
                    val colliderCircle = Circle(positionComponent.x, positionComponent.y, circle.radius)
                    if (colliderCircle.contains(Vector2(switchablePosition.x, switchablePosition.y))) {
                        used(switchable, entity)
                    } else {
                        notUsed(switchable, entity)
                    }
                }


            }
        }
    }

    private fun used(switchable: Entity, switcher: Entity) {
        switchable.getComponent(SwitchableComponent::class.java)?.let { switchableComponent ->
            if (!switchableComponent.usingEntities.contains(switcher.id)) {
                switchableComponent.enabled = !switchableComponent.enabled
                switchableComponent.usingEntities.add(switcher.id)

                switchable.switchEffect()
                println("USED")
                switcher.useEffect()
            }
        }
    }

    private fun notUsed(switchable: Entity, switcher: Entity) {
        switchable.getComponent(SwitchableComponent::class.java)?.let { switchableComponent ->
            if (switchableComponent.usingEntities.contains(switcher.id)) {
                switchableComponent.usingEntities.remove(switcher.id)
            }
        }
    }


}

fun World.findSwitchablePoints(): IntBag {
    return this.aspectSubscriptionManager.get(
        Aspect.all(SwitchableComponent::class.java, PositionComponent::class.java)
    ).entities ?: IntBag()
}


fun Entity.switchEffect() {
    this.getComponent(DrawableStateComponent::class.java)?.let { drawableState ->
        drawableState.changeState()
    }

    //todo play audio
}

fun Entity.useEffect() {
    // use effect

    //todo play audio
}

