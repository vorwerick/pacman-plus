package cz.pacmanplus.game.core.systems.physics.collisions

import com.artemis.Aspect
import com.artemis.Entity
import com.artemis.systems.EntityProcessingSystem
import com.badlogic.gdx.Gdx
import cz.pacmanplus.game.core.components.attributes.TimeComponent
import cz.pacmanplus.game.core.components.attributes.DirectionComponent
import cz.pacmanplus.game.core.components.attributes.SpeedComponent
import cz.pacmanplus.game.core.components.physics.PositionComponent
import cz.pacmanplus.game.core.entity.FloorObjects
import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.LoggerFactory

//find all Turrets and make them behaviour
class TurretSystem :
    EntityProcessingSystem(
        Aspect.all(
            DirectionComponent::class.java,
            PositionComponent::class.java,
            TimeComponent::class.java,
            SpeedComponent::class.java
        )
    ) {
    val log = LoggerFactory.getLogger("TurretSystem")


    override fun process(e: Entity?) {
        if (e == null) return


        e.let { entity: Entity ->
            val delta = Gdx.graphics.deltaTime
            val positionComponent = entity.getComponent(PositionComponent::class.java)
            val timeComponent = entity.getComponent(TimeComponent::class.java)
            val directionComponent = entity.getComponent(DirectionComponent::class.java)
            val speedComponent = entity.getComponent(SpeedComponent::class.java)

            val floorObjects: FloorObjects = getKoin().get()

            if (timeComponent.isFinished()) {
                timeComponent.reset()
                floorObjects.projectile(
                    positionComponent.x,
                    positionComponent.y,
                    speedComponent.speed,
                    directionComponent.direction
                )

            } else {
                timeComponent.tick(delta)
            }

        }
    }


}

