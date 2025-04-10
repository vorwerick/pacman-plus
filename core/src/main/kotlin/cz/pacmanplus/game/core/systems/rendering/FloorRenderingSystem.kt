package cz.pacmanplus.game.core.systems.rendering

import com.artemis.Aspect
import com.artemis.BaseSystem
import com.artemis.Entity
import cz.pacmanplus.game.core.components.graphics.TexturesComponent
import cz.pacmanplus.game.core.components.objects.FloorComponent
import cz.pacmanplus.game.core.components.objects.WallComponent
import cz.pacmanplus.game.core.components.physics.CircleCollisionComponent
import cz.pacmanplus.game.core.components.physics.PositionComponent
import cz.pacmanplus.game.core.components.physics.RectangleCollisionComponent
import cz.pacmanplus.game.core.systems.physics.collisions.entities

class FloorRenderingSystem(val configuration: RenderingSystemConfiguration) : BaseSystem() {


    override fun processSystem() {
        val entities =
            world.aspectSubscriptionManager.get(Aspect.all(FloorComponent::class.java, TexturesComponent::class.java))
                .entities(world)

        configuration.spriteBatch.begin()
        entities.forEach { entity ->
            entity.getComponent(PositionComponent::class.java)?.let { position ->
                entity.getComponent(TexturesComponent::class.java)?.let { texturesComponent ->
                    var offsetX = 0f
                    var offsetY = 0f
                    entity.getComponent(CircleCollisionComponent::class.java)?.let { collisionComponent ->
                        offsetX = collisionComponent.radius/2
                        offsetY = collisionComponent.radius/2
                    }
                    configuration.spriteBatch.draw(texturesComponent.textures[0], position.x-offsetX, position.y-offsetY)
                }

            }
        }

        configuration.spriteBatch.end()
    }
}
