package cz.pacmanplus.game.core.systems.physics.movement

import com.artemis.Aspect
import com.artemis.Entity
import com.artemis.EntitySubscription
import com.artemis.systems.EntityProcessingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import cz.pacmanplus.game.GameState
import cz.pacmanplus.game.core.components.control.ComputerPathComponent
import cz.pacmanplus.game.core.components.physics.*
import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.LoggerFactory

// movement for all movable boxes
class PathMovementSystem : EntityProcessingSystem(
    Aspect.all(
        ComputerPathComponent::class.java,
        PositionComponent::class.java,
    )
) {
    val log = LoggerFactory.getLogger("PathMovementSystem")


    override fun process(e: Entity?) {


        val friction = 1f
        e?.let { entity: Entity ->

            val positionComponent = entity.getComponent(PositionComponent::class.java)

            val computerPathComponent = entity.getComponent(ComputerPathComponent::class.java)
            if (computerPathComponent.path != null && computerPathComponent.path!!.isNotEmpty()) {

                val target1 = computerPathComponent.path!!.first()
                val targetX = target1.first * 32f
                val targetY = target1.second * 32f
                val posX = positionComponent.x
                val posY = positionComponent.y
                if (Vector2.dst(posX, posY, targetX, targetY) < 2) {
                    computerPathComponent.path!!.removeFirstOrNull()
                } else {
                    val nextXDir = targetX - posX
                    val nextYDir = targetY - posY
                    val vec = Vector2(nextXDir, nextYDir).nor()
                    positionComponent.x += vec.x * 100 * Gdx.graphics.deltaTime
                    positionComponent.y += vec.y * 100 * Gdx.graphics.deltaTime

                }


            }


        }
    }


}

