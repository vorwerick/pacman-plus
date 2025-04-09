package cz.pacmanplus.game.core.systems

import com.artemis.Aspect
import com.artemis.BaseSystem
import com.artemis.Entity
import com.artemis.World
import com.badlogic.gdx.math.Vector3
import cz.pacmanplus.game.PlayerCamera
import cz.pacmanplus.game.core.components.control.InputComponent
import cz.pacmanplus.game.core.components.physics.MovementComponent
import cz.pacmanplus.game.core.components.physics.PositionComponent
import cz.pacmanplus.game.core.entity.WallObjects
import org.koin.java.KoinJavaComponent.getKoin

class PlayerSystem : BaseSystem() {

    var playerId: Int? = null

    override fun processSystem() {
        if (playerId == null) {
            world.findPlayer()?.let {
                playerId = it.id
            }
            return
        }

        playerId?.let {
            val player = world.getEntity(it)
            val position = player.getComponent(PositionComponent::class.java)
            val inputComponent = player.getComponent(InputComponent::class.java)
            val cam = getKoin().get<PlayerCamera>()

            cam.camera.position.set(Vector3(position.x, position.y, 0f))
            cam.camera.update()
        }

    }

    fun createBomb() {
        playerId?.let {
            world?.getEntity(it)?.let { player ->
                val position = player.getComponent(PositionComponent::class.java)
                val movementComponent = player.getComponent(MovementComponent::class.java)
                val wallObjects: WallObjects = getKoin().get()
                wallObjects.bomb(movementComponent.xTile * 32f, movementComponent.yTile * 32f)
            }
        }
    }


}

fun World.findPlayer(): Entity? {
    val entBag = this.aspectSubscriptionManager.get(Aspect.all(InputComponent::class.java))
    for (index in 0 until entBag.entities.size()) {
        val entityId = entBag.entities[index]
        return this.getEntity(entityId)

    }
    return null

}
