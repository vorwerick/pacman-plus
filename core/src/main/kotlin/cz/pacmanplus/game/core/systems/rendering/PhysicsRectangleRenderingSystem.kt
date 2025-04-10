package cz.pacmanplus.game.core.systems.rendering

import com.artemis.Aspect
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import cz.pacmanplus.game.PlayerCamera
import cz.pacmanplus.game.core.components.attributes.ActivateComponent
import cz.pacmanplus.game.core.components.attributes.LifespanComponent
import cz.pacmanplus.game.core.components.attributes.SwitchableComponent
import cz.pacmanplus.game.core.components.attributes.ButtonComponent
import cz.pacmanplus.game.core.components.physics.*
import org.koin.java.KoinJavaComponent.getKoin

class PhysicsRectangleRenderingSystem(val configuration: RenderingSystemConfiguration) : IteratingSystem(
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
        val hitPointsComponent = world.getEntity(entityId).getComponent(HitPointsComponent::class.java)
        val lootComponent = world.getEntity(entityId).getComponent(LootComponent::class.java)
        val lifespanComponent = world.getEntity(entityId).getComponent(LifespanComponent::class.java)
        val activateComponent = world.getEntity(entityId).getComponent(ActivateComponent::class.java)
        val rectangleCollisionComponent =
            world.getEntity(entityId).getComponent(RectangleCollisionComponent::class.java)
        val buttonComponent = world.getEntity(entityId).getComponent(ButtonComponent::class.java)
        val switchableComponent = world.getEntity(entityId).getComponent(SwitchableComponent::class.java)

        configuration.shapeRenderer.setAutoShapeType(true)
        configuration.shapeRenderer.begin(ShapeType.Line)
        if (rectangleCollisionComponent.solid) {
            val isInvulnerable = hitPointsComponent.state == HitPoint.Invulnerable
            val isPushable = pushableComponent != null
            val isLoot = lootComponent != null
            val isActiveable = activateComponent != null
            configuration.shapeRenderer.color = Color.GRAY
            if (isInvulnerable) {
                configuration.shapeRenderer.color = Color.DARK_GRAY
            }
            if (isPushable) {
                configuration.shapeRenderer.color = Color.LIGHT_GRAY
            }
            if(isLoot){
                configuration.shapeRenderer.color =  Color.ORANGE
            }
            if(isActiveable){
                configuration.shapeRenderer.color = Color.FOREST
            }
        } else {
            if(lifespanComponent != null) {
                configuration.shapeRenderer.color = Color.PINK
            } else {
                configuration.shapeRenderer.color = Color.BROWN
            }
            if(buttonComponent != null) {
                if(buttonComponent.triggered){
                    configuration.shapeRenderer.color = Color.GREEN
                } else {
                    configuration.shapeRenderer.color = Color.WHITE
                }
            }
            if(switchableComponent != null) {
                if(switchableComponent.enabled){
                    configuration.shapeRenderer.color = Color.BLUE
                } else {
                    configuration.shapeRenderer.color = Color.RED
                }
            }

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
