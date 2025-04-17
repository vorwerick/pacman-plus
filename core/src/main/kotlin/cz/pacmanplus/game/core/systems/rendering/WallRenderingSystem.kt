package cz.pacmanplus.game.core.systems.rendering

import com.artemis.Aspect
import com.artemis.BaseSystem
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import cz.pacmanplus.game.core.components.control.CharacterComponent
import cz.pacmanplus.game.core.components.graphics.DrawableEffectComponent
import cz.pacmanplus.game.core.components.graphics.DrawableStateComponent
import cz.pacmanplus.game.core.components.objects.ItemComponent
import cz.pacmanplus.game.core.components.objects.WallComponent
import cz.pacmanplus.game.core.components.physics.CircleCollisionComponent
import cz.pacmanplus.game.core.components.physics.PositionComponent
import cz.pacmanplus.game.core.systems.PlayerSystem
import cz.pacmanplus.game.core.systems.physics.collisions.entities

class WallRenderingSystem(val spriteBatch: SpriteBatch, val shapeRenderer: ShapeRenderer) : BaseSystem() {


    override fun processSystem() {
        val entities = world.aspectSubscriptionManager.get(
            Aspect.one(DrawableStateComponent::class.java, DrawableEffectComponent::class.java)
                .one(WallComponent::class.java, CharacterComponent::class.java, ItemComponent::class.java)
        ).entities(world)

        spriteBatch.begin()

        val sortedEntities = entities.sortedBy { it.getComponent(PositionComponent::class.java).y * -1 }

        sortedEntities.forEach { entity ->
            entity.getComponent(PositionComponent::class.java)?.let { position ->
                entity.getComponent(DrawableStateComponent::class.java)?.let { drawableComponent ->
                    val center = position.getCenterPosition(entity.getComponent(CircleCollisionComponent::class.java))
                    drawableComponent.draw(spriteBatch, center.x, center.y)
                }
                entity.getComponent(DrawableEffectComponent::class.java)?.let { effectComponent ->
                    val center = position.getCenterPosition(entity.getComponent(CircleCollisionComponent::class.java))
                    effectComponent.draw(spriteBatch, center.x, center.y)
                }
            }
        }
        spriteBatch.end()
    }
}
