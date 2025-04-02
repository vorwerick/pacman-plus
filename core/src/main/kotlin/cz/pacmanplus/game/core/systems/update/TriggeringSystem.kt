package cz.pacmanplus.game.core.systems.update

import com.artemis.Aspect
import com.artemis.Entity
import com.artemis.systems.EntityProcessingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Rectangle
import cz.pacmanplus.game.GameState
import cz.pacmanplus.game.core.components.attributes.DelayComponent
import cz.pacmanplus.game.core.components.attributes.EnabledComponent
import cz.pacmanplus.game.core.components.attributes.LifespanComponent
import cz.pacmanplus.game.core.components.attributes.TriggerableComponent
import cz.pacmanplus.game.core.components.physics.*
import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.LoggerFactory

class TriggeringSystem :
    EntityProcessingSystem(
        Aspect.all(
            TriggerableComponent::class.java,
        )
    ) {
    val log = LoggerFactory.getLogger("TriggeringSystem")


    override fun process(e: Entity?) {
        if (e == null) return

        val gameState: GameState = getKoin().get()
        if (gameState.paused) {
            return
        }

        e.let { entity: Entity ->
            val delta = Gdx.graphics.deltaTime
            val triggerableComponent = entity.getComponent(TriggerableComponent::class.java)
            val positionComponent = entity.getComponent(PositionComponent::class.java)
            val rectangleCollisionComponent = entity.getComponent(RectangleCollisionComponent::class.java)
            val enabledComponent = entity.getComponent(EnabledComponent::class.java)
            val rectangle = Rectangle(
                positionComponent.x,
                positionComponent.y,
                rectangleCollisionComponent.width,
                rectangleCollisionComponent.height
            )

            if(enabledComponent != null) {
                val colliders =
                    world.aspectSubscriptionManager.get(Aspect.all(MovementComponent::class.java)).entities(world)
                colliders.forEach { es ->
                    val pos = es.getComponent(PositionComponent::class.java)
                    if (rectangle.contains(pos.x, pos.y)) {
                        triggerableComponent.triggered =  !triggerableComponent.triggered
                    }

                }
            } else {
                triggerableComponent.triggered = false
                val colliders =
                    world.aspectSubscriptionManager.get(Aspect.all(MovementComponent::class.java)).entities(world)
                colliders.forEach { es ->
                    val pos = es.getComponent(PositionComponent::class.java)
                    if (rectangle.contains(pos.x, pos.y)) {
                        triggerableComponent.triggered = true
                    }

                }
            }




        }
    }


}

