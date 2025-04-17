package cz.pacmanplus.game.core.systems.rendering

import com.artemis.Aspect
import com.artemis.BaseSystem
import cz.pacmanplus.game.core.components.control.CharacterComponent
import cz.pacmanplus.game.core.components.graphics.DrawableStateComponent
import cz.pacmanplus.game.core.components.physics.CircleCollisionComponent
import cz.pacmanplus.game.core.components.physics.PositionComponent
import cz.pacmanplus.game.core.systems.physics.collisions.entities

class CharacterRenderingSystem(val configuration: RenderingSystemConfiguration) : BaseSystem() {


    override fun processSystem() {
        val entities =
            world.aspectSubscriptionManager.get(
                Aspect.all(
                    CharacterComponent::class.java,
                    DrawableStateComponent::class.java
                )
            )
                .entities(world)

        configuration.spriteBatch.begin()
        entities.forEach { entity ->
            entity.getComponent(PositionComponent::class.java)?.let { position ->
                entity.getComponent(DrawableStateComponent::class.java)?.let { drawableComponent ->
                    val center = position.getCenterPosition(entity.getComponent(CircleCollisionComponent::class.java))
                    drawableComponent.draw(configuration.spriteBatch, center.x, center.y)

                }

            }
        }

        configuration.spriteBatch.end()
    }
}
