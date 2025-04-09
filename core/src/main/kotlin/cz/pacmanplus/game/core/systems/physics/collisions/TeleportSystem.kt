package cz.pacmanplus.game.core.systems.physics.collisions

import com.artemis.Aspect
import com.artemis.Entity
import com.artemis.World
import com.artemis.systems.EntityProcessingSystem
import com.artemis.utils.IntBag
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Vector2
import cz.pacmanplus.game.GameState
import cz.pacmanplus.game.core.components.attributes.TeleportComponent
import cz.pacmanplus.game.core.components.physics.*
import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.LoggerFactory

/// For entities who can use teleport
class TeleportSystem : EntityProcessingSystem(
    Aspect.all(
        CircleCollisionComponent::class.java, PositionComponent::class.java, MovementComponent::class.java,
    )
) {
    val log = LoggerFactory.getLogger("ButtonSystem")


    override fun process(e: Entity?) {

        e?.let { teleportUser: Entity ->
            val positionComponent = teleportUser.getComponent(PositionComponent::class.java)
            val teleports = world.findTeleports()

            (0 until teleports.size()).forEach { index ->

                val id = teleports.get(index)
                val teleport = world.getEntity(id)
                val teleportPosition = teleport.getComponent(PositionComponent::class.java)

                teleportUser.getComponent(CircleCollisionComponent::class.java)?.let { circle ->
                    val colliderCircle = Circle(positionComponent.x, positionComponent.y, circle.radius)
                    if (colliderCircle.contains(Vector2(teleportPosition.x, teleportPosition.y))) {
                        val destination = enterTeleport(teleport, teleportUser, teleports)
                        if (destination != null) {
                            transport(destination, teleportUser)
                        }
                    } else {
                        leaveTeleport(teleport, teleportUser)
                    }
                }


            }
        }
    }

    private fun transport(destination: Entity, teleportUser: Entity) {
        teleportUser.getComponent(PositionComponent::class.java)?.let { userPosition ->
            destination.getComponent(PositionComponent::class.java)?.let { teleportPosition ->
                userPosition.x = teleportPosition.x
                userPosition.y = teleportPosition.y
            }
        }
        destination.getComponent(TeleportComponent::class.java)?.usingEntities?.add(teleportUser.id)
    }

    private fun findDestination(teleports: IntBag, targetAddress: Int): Entity? {
        (0 until teleports.size()).forEach { index ->
            val id = teleports.get(index)
            val t = world.getEntity(id)
            t.getComponent(TeleportComponent::class.java)?.let { teleport ->
                if (targetAddress == teleport.address) {
                    return t
                }
            }
        }
        return null
    }

    private fun leaveTeleport(teleport: Entity, user: Entity) {
        teleport.getComponent(TeleportComponent::class.java)?.let { teleportComponent ->
            if (teleportComponent.usingEntities.contains(user.id)) {
                teleportComponent.usingEntities.remove(user.id)
            }
        }
    }

    private fun enterTeleport(teleport: Entity, user: Entity, teleports: IntBag): Entity? {
        teleport.getComponent(TeleportComponent::class.java)?.let { teleportComponent ->
            if (!teleportComponent.usingEntities.contains(user.id)) {
                teleportComponent.usingEntities.add(user.id)
                val targetAddress = teleportComponent.target
                val destinationTeleport = findDestination(teleports, targetAddress)
                return destinationTeleport
            }
        }
        return null
    }


}

fun World.findTeleports(): IntBag {
    return this.aspectSubscriptionManager.get(
        Aspect.all(TeleportComponent::class.java, PositionComponent::class.java)
    ).entities ?: IntBag()
}
