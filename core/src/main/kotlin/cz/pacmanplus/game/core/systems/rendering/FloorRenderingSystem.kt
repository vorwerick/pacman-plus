package cz.pacmanplus.game.core.systems.rendering

import com.artemis.Aspect
import com.artemis.BaseSystem
import cz.pacmanplus.game.core.components.graphics.DrawableStateComponent
import cz.pacmanplus.game.core.components.objects.FloorComponent
import cz.pacmanplus.game.core.components.physics.CircleCollisionComponent
import cz.pacmanplus.game.core.components.physics.PositionComponent
import cz.pacmanplus.game.core.systems.physics.collisions.entities

class FloorRenderingSystem(val configuration: RenderingSystemConfiguration) : BaseSystem() {


    override fun processSystem() {
        val entities =
            world.aspectSubscriptionManager.get(
                Aspect.all(
                    FloorComponent::class.java,
                    DrawableStateComponent::class.java
                )
            )
                .entities(world)

        configuration.spriteBatch.begin()
        entities.forEach { entity ->
            entity.getComponent(PositionComponent::class.java)?.let { position ->

                entity.getComponent(DrawableStateComponent::class.java)?.let { animation ->
                    var offsetX = 0f
                    var offsetY = 0f
                    animation.draw(configuration.spriteBatch, position.x - offsetX, position.y - offsetY)
                }

            }
        }

        configuration.spriteBatch.end()
    }
}
