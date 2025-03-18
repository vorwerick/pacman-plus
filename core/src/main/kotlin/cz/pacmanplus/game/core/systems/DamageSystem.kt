package cz.pacmanplus.game.core.systems

import com.artemis.Aspect
import com.artemis.Entity
import com.artemis.systems.EntityProcessingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Rectangle
import cz.pacmanplus.GameConfig
import cz.pacmanplus.game.GameState
import cz.pacmanplus.game.core.components.physics.*
import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.LoggerFactory

class DamageSystem :
    EntityProcessingSystem(
        Aspect.all(
            PositionComponent::class.java,
            CircleCollisionComponent::class.java,
            HealthComponent::class.java,
        )
    ) {
    val log = LoggerFactory.getLogger("MovementSystem")


    override fun process(e: Entity?) {

        val gameState = getKoin().get<GameState>()
        if (gameState.paused) {
            return
        }

        e?.let { entity: Entity ->
            val positionComponent = entity.getComponent(PositionComponent::class.java)
            val healthComponent = entity.getComponent(HealthComponent::class.java)
            val circleCollisionComponent = entity.getComponent(CircleCollisionComponent::class.java)

            if(healthComponent.lives < 0){
                world.deleteEntity(entity)
            }

            healthComponent.bleeding = false
            if (healthComponent.invulnerability) {
                healthComponent.invulnerabilityTimer -=  Gdx.graphics.deltaTime * 1000f
                if (healthComponent.invulnerabilityTimer <= 0) {
                    healthComponent.invulnerabilityTimer = 0f
                    healthComponent.invulnerability = false
                }
            }
            val circle = Circle(positionComponent.x, positionComponent.y, circleCollisionComponent.radius)


            val damageAreas = world.aspectSubscriptionManager.get(
                Aspect.all(
                    RectangleCollisionComponent::class.java,
                    PositionComponent::class.java,
                    DamageComponent::class.java
                )
            )

            if(healthComponent.invulnerability.not()){
                (0 until damageAreas.entities.size()).forEach { index ->
                    val id = damageAreas.entities.get(index)
                    val rectangleEntity = world.getEntity(id)
                    val rectangleCollisionComponent = rectangleEntity.getComponent(RectangleCollisionComponent::class.java)

                    val rpComponent = rectangleEntity.getComponent(PositionComponent::class.java)

                    val rect = Rectangle(
                        rpComponent.x,
                        rpComponent.y,
                        rectangleCollisionComponent.width,
                        rectangleCollisionComponent.height
                    )


                    if (Intersector.overlaps(circle, rect)) {
                        healthComponent.lives -= 1
                        healthComponent.bleeding = true
                        healthComponent.invulnerability = true
                        healthComponent.invulnerabilityTimer = GameConfig.INVULNERABILITY_TIMEOUT
                    }
                    // Test kolize pro osu X

                }

            }


        }


    }
}
