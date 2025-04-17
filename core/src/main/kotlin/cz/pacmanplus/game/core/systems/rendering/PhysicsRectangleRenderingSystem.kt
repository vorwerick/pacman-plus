package cz.pacmanplus.game.core.systems.rendering

import com.artemis.Aspect
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import cz.pacmanplus.game.PlayerCamera
import cz.pacmanplus.game.core.components.attributes.ActivateComponent
import cz.pacmanplus.game.core.components.attributes.LifespanComponent
import cz.pacmanplus.game.core.components.attributes.SwitchableComponent
import cz.pacmanplus.game.core.components.attributes.ButtonComponent
import cz.pacmanplus.game.core.components.physics.*
import org.koin.java.KoinJavaComponent.getKoin

class PhysicsRectangleRenderingSystem(val spriteBatch: SpriteBatch, val shapeRenderer: ShapeRenderer) : IteratingSystem(
    Aspect.all(
        PositionComponent::class.java,
        RectangleCollisionComponent::class.java
    )
) {


    override fun process(entityId: Int) {
        val cam: PlayerCamera = getKoin().get<PlayerCamera>()

        shapeRenderer.projectionMatrix = cam.camera.combined
        spriteBatch.projectionMatrix = cam.camera.combined

        val positionComponent = world.getEntity(entityId).getComponent(PositionComponent::class.java)
        val damageComponent = world.getEntity(entityId).getComponent(DamageComponent::class.java)
        val pushableComponent = world.getEntity(entityId).getComponent(PushableComponent::class.java)
        val lifecycleComponent = world.getEntity(entityId).getComponent(LifecycleComponent::class.java)
        val lootComponent = world.getEntity(entityId).getComponent(LootComponent::class.java)
        val lifespanComponent = world.getEntity(entityId).getComponent(LifespanComponent::class.java)
        val activateComponent = world.getEntity(entityId).getComponent(ActivateComponent::class.java)
        val rectangleCollisionComponent =
            world.getEntity(entityId).getComponent(RectangleCollisionComponent::class.java)
        val buttonComponent = world.getEntity(entityId).getComponent(ButtonComponent::class.java)
        val switchableComponent = world.getEntity(entityId).getComponent(SwitchableComponent::class.java)

        shapeRenderer.setAutoShapeType(true)
        shapeRenderer.begin(ShapeType.Line)
        if (rectangleCollisionComponent.solid) {
            val isInvulnerable = lifecycleComponent.isInvulnerable()
            val isPushable = pushableComponent != null
            val isLoot = lootComponent != null
            val isActiveable = activateComponent != null
            shapeRenderer.color = Color.GRAY
            if (isInvulnerable) {
                shapeRenderer.color = Color.DARK_GRAY
            }
            if (isPushable) {
                shapeRenderer.color = Color.LIGHT_GRAY
            }
            if (isLoot) {
                shapeRenderer.color = Color.ORANGE
            }
            if (isActiveable) {
                shapeRenderer.color = Color.FOREST
            }
        } else {
            if (lifespanComponent != null) {
                shapeRenderer.color = Color.PINK
            } else {
                shapeRenderer.color = Color.BROWN
            }
            if (buttonComponent != null) {
                if (buttonComponent.triggered) {
                    shapeRenderer.color = Color.GREEN
                } else {
                    shapeRenderer.color = Color.WHITE
                }
            }
            if (switchableComponent != null) {
                if (switchableComponent.enabled) {
                    shapeRenderer.color = Color.BLUE
                } else {
                    shapeRenderer.color = Color.RED
                }
            }

        }

        if (damageComponent != null) {
            shapeRenderer.color = Color.RED
        }
        shapeRenderer.rect(
            positionComponent.x,
            positionComponent.y,
            rectangleCollisionComponent.width,
            rectangleCollisionComponent.height
        )
        shapeRenderer.end()


    }


}
