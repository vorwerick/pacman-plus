package cz.pacmanplus.game.core.systems.update

import com.artemis.Aspect
import com.artemis.Entity
import com.artemis.systems.EntityProcessingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Rectangle
import cz.pacmanplus.game.GameState
import cz.pacmanplus.game.core.components.attributes.DelayComponent
import cz.pacmanplus.game.core.components.attributes.LifespanComponent
import cz.pacmanplus.game.core.components.physics.HitPoint
import cz.pacmanplus.game.core.components.physics.HitPointsComponent
import cz.pacmanplus.game.core.components.physics.PositionComponent
import cz.pacmanplus.game.core.components.physics.RectangleCollisionComponent
import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.LoggerFactory

class ExplosionSystem :
    EntityProcessingSystem(
        Aspect.all(
            LifespanComponent::class.java, PositionComponent::class.java,
        )
    ) {
    val log = LoggerFactory.getLogger("ClockSystem")


    override fun process(e: Entity?) {
        if (e == null) return

        val gameState: GameState = getKoin().get()
        if (gameState.paused) {
            return
        }

        e.let { entity: Entity ->
            val delta = Gdx.graphics.deltaTime
            val lifespanComponent = entity.getComponent(LifespanComponent::class.java)
            val positionComponent = entity.getComponent(PositionComponent::class.java)

            if (lifespanComponent.isFinished()) {
                world.delete(e.id)
            }

            val collision = world.aspectSubscriptionManager.get(Aspect.all(RectangleCollisionComponent::class.java, HitPointsComponent::class.java)).entities(world)
            collision.forEach { entity ->
                val pos = entity.getComponent(PositionComponent::class.java)
                val rectangleCollisionComponent = entity.getComponent(RectangleCollisionComponent::class.java)
                if(Rectangle(pos.x, pos.y, rectangleCollisionComponent.width, rectangleCollisionComponent.height).contains(positionComponent.x, positionComponent.y)) {
                    val hitPointsComponent = entity.getComponent(HitPointsComponent::class.java)
                    if(hitPointsComponent.state is HitPoint.Alive){
                        (hitPointsComponent.state as HitPoint.Alive).value -= 1
                    }
                }

            }
        }
    }


}

