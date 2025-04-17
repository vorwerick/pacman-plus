package cz.pacmanplus.game.core.systems.physics.collisions

import com.artemis.Aspect
import com.artemis.Entity
import com.artemis.World
import com.artemis.systems.EntityProcessingSystem
import com.artemis.utils.IntBag
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import cz.pacmanplus.game.GameState
import cz.pacmanplus.game.core.components.attributes.PressureComponent
import cz.pacmanplus.game.core.components.attributes.ButtonComponent
import cz.pacmanplus.game.core.components.graphics.DrawableStateComponent
import cz.pacmanplus.game.core.components.physics.*
import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.LoggerFactory

/// For entities who can trigger
class ButtonSystem : EntityProcessingSystem(
    Aspect.one(
        //CircleCollisionComponent::class.java,
        RectangleCollisionComponent::class.java
    ).all(PositionComponent::class.java, PressureComponent::class.java)
) {
    val log = LoggerFactory.getLogger("ButtonSystem")


    override fun process(e: Entity?) {

        e?.let { entity: Entity ->
            val positionComponent = entity.getComponent(PositionComponent::class.java)
            val buttons = world.findButtons()

            (0 until buttons.size()).forEach { index ->

                val id = buttons.get(index)
                val button = world.getEntity(id)
                val triggerablePosition = button.getComponent(PositionComponent::class.java)

                entity.getComponent(CircleCollisionComponent::class.java)?.let { circle ->
                    val colliderCircle = Circle(positionComponent.x, positionComponent.y, circle.radius)
                    if (colliderCircle.contains(Vector2(triggerablePosition.x, triggerablePosition.y))) {
                        triggered(button)
                    } else {
                        untriggered(button)
                    }
                }

                entity.getComponent(RectangleCollisionComponent::class.java)?.let { rectangle ->
                    val colliderRect =
                        Rectangle(positionComponent.x, positionComponent.y, rectangle.width, rectangle.height)
                    if (colliderRect.contains(Vector2(triggerablePosition.x, triggerablePosition.y))) {
                        triggered(button)
                    } else {
                        untriggered(button)
                    }
                }
            }
        }
    }

    private fun untriggered(button: Entity) {
        button.getComponent(ButtonComponent::class.java)?.let { triggerableComponent ->
            triggerableComponent.triggered = false

            button.triggerEffect()
        }
    }

    private fun triggered(button: Entity) {
        button.getComponent(ButtonComponent::class.java)?.let { triggerableComponent ->
            triggerableComponent.triggered = true

            button.triggerEffect()
        }
    }

}

fun World.findButtons(): IntBag {
    return this.aspectSubscriptionManager.get(
        Aspect.all(ButtonComponent::class.java, PositionComponent::class.java)
    ).entities ?: IntBag()
}


fun Entity.triggerEffect() {
    this.getComponent(DrawableStateComponent::class.java)?.let { drawableState ->
        drawableState.changeState()
    }

    //todo play audio
}
