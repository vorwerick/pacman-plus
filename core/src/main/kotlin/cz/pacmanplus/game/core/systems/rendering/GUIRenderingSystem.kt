package cz.pacmanplus.game.core.systems.rendering

import com.artemis.Aspect
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import cz.pacmanplus.game.PlayerCamera
import cz.pacmanplus.game.core.components.physics.DamageComponent
import cz.pacmanplus.game.core.components.physics.PositionComponent
import cz.pacmanplus.game.core.components.physics.PushableComponent
import cz.pacmanplus.game.core.components.physics.RectangleCollisionComponent
import org.koin.java.KoinJavaComponent.getKoin

class GUIRenderingSystem(val configuration: RenderingSystemConfiguration) : IteratingSystem(
    Aspect.all(
        PositionComponent::class.java,
        RectangleCollisionComponent::class.java
    )
) {




    override fun process(entityId: Int) {
        val cam: PlayerCamera = getKoin().get<PlayerCamera>()

        configuration.shapeRenderer.projectionMatrix = cam.camera.combined
        configuration.spriteBatch.projectionMatrix = cam.camera.combined

        val positionComponent = world.getEntity(entityId).getComponent(PositionComponent::class.java)
        val damageComponent = world.getEntity(entityId).getComponent(DamageComponent::class.java)
        val pushableComponent = world.getEntity(entityId).getComponent(PushableComponent::class.java)
        val rectangleCollisionComponent =
            world.getEntity(entityId).getComponent(RectangleCollisionComponent::class.java)

        configuration.shapeRenderer.setAutoShapeType(true)
        configuration.shapeRenderer.begin(ShapeType.Line)
        configuration.shapeRenderer.color = Color.BLUE
        if (rectangleCollisionComponent.solid) {
            configuration.shapeRenderer.color = Color.CYAN
        }
        if (pushableComponent != null) {
            configuration.shapeRenderer.color = Color.WHITE
            configuration.shapeRenderer.line(
                positionComponent.x + 16,
                positionComponent.y + 16,
                positionComponent.x + 16 + (pushableComponent.pushDirection.x * pushableComponent.pushPotential),
                positionComponent.y + 16 + (pushableComponent.pushDirection.y * pushableComponent.pushPotential)
            )
            configuration.shapeRenderer.color = Color.ORANGE

        }
        if (damageComponent != null) {
            configuration.shapeRenderer.color = Color.RED
        }
        configuration.shapeRenderer.rect(
            positionComponent.x,
            positionComponent.y,
            rectangleCollisionComponent.width,
            rectangleCollisionComponent.height
        )
        configuration.shapeRenderer.end()


    }


}
