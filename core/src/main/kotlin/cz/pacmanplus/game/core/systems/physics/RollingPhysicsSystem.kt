package cz.pacmanplus.game.core.systems.physics

import com.artemis.Aspect
import com.artemis.Entity
import com.artemis.EntitySubscription
import com.artemis.systems.EntityProcessingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import cz.pacmanplus.game.GameState
import cz.pacmanplus.game.core.components.attributes.TriggerableComponent
import cz.pacmanplus.game.core.components.control.ForcePushingComponent
import cz.pacmanplus.game.core.components.control.PlayerInputComponent
import cz.pacmanplus.game.core.components.physics.*
import cz.pacmanplus.utils.Coords
import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.LoggerFactory
import kotlin.math.abs

class RollingPhysicsSystem :
    EntityProcessingSystem(
        Aspect.all()
    ) {
    val log = LoggerFactory.getLogger("MovementPhysicsSystem")


    override fun process(e: Entity?) {

        val gameState: GameState = getKoin().get()
        if (gameState.paused) {
            return
        }

        val friction = 1f
        e?.let { entity: Entity ->
            val delta = Gdx.graphics.deltaTime
            val positionComponent = entity.getComponent(PositionComponent::class.java)
            val movementComponent = entity.getComponent(MovementComponent::class.java)
            val inputComponent = entity.getComponent(PlayerInputComponent::class.java)
            val forcePushingComponent = entity.getComponent(ForcePushingComponent::class.java)
            val pushComponent = entity.getComponent(PushComponent::class.java)

            val circleCollisionComponent = entity.getComponent(CircleCollisionComponent::class.java)




        }
    }


}

