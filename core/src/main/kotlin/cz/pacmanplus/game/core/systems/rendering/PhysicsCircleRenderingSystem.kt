package cz.pacmanplus.game.core.systems.rendering

import com.artemis.Aspect
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import cz.pacmanplus.game.PlayerCamera
import cz.pacmanplus.game.core.components.control.ComputerPathComponent
import cz.pacmanplus.game.core.components.physics.CircleCollisionComponent
import cz.pacmanplus.game.core.components.physics.HealthComponent
import cz.pacmanplus.game.core.components.physics.MovementComponent
import cz.pacmanplus.game.core.components.physics.PositionComponent
import org.koin.java.KoinJavaComponent.getKoin

class PhysicsCircleRenderingSystem(val configuration: RenderingSystemConfiguration) : IteratingSystem(
    Aspect.all(
        PositionComponent::class.java, CircleCollisionComponent::class.java
    )
) {

    override fun process(entityId: Int) {
        val cam: PlayerCamera = getKoin().get<PlayerCamera>()
        configuration.shapeRenderer.projectionMatrix = cam.camera.combined
        configuration.spriteBatch.projectionMatrix = cam.camera.combined


        val positionComponent = world.getEntity(entityId).getComponent(PositionComponent::class.java)
        val circleCollisionComponent = world.getEntity(entityId).getComponent(CircleCollisionComponent::class.java)
        val movementComponent = world.getEntity(entityId).getComponent(MovementComponent::class.java)
       val healthComponent = world.getEntity(entityId).getComponent(HealthComponent::class.java)
        val computerPathComponent = world.getEntity(entityId).getComponent(ComputerPathComponent::class.java)

        configuration.shapeRenderer.setAutoShapeType(true)
        configuration.shapeRenderer.begin(ShapeType.Line)
        configuration.shapeRenderer.color = Color.GREEN
        if (circleCollisionComponent.colliding) {
            configuration.shapeRenderer.color = Color.FOREST
        }
        if(healthComponent != null) {
            if(healthComponent.bleeding){
                configuration.shapeRenderer.color = Color.ORANGE
            }
            if(healthComponent.invulnerability){
                configuration.shapeRenderer.color = Color.WHITE
            }

        }
        if(computerPathComponent != null){
            configuration.shapeRenderer.color = Color.RED
        }
        configuration.shapeRenderer.circle(positionComponent.x, positionComponent.y, circleCollisionComponent.radius)

        //configuration.shapeRenderer.color = Color.CYAN

        /*
        movementComponent?.let {
            configuration.shapeRenderer.line(
                positionComponent.x,
                positionComponent.y,
                movementComponent.xTile * 32f,
                movementComponent.yTile * 32f,
            )

            configuration.shapeRenderer.color = Color.RED
            configuration.shapeRenderer.line(
                movementComponent.xTile * 32f,
                movementComponent.yTile * 32f,
                (movementComponent.targetXTile * 32f),
                 (movementComponent.targetYTile * 32f))



        }

         */
        configuration.shapeRenderer.end()


    }


}
