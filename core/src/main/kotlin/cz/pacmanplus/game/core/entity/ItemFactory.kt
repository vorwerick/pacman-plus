package cz.pacmanplus.game.core.entity

import com.artemis.World
import cz.pacmanplus.game.core.components.common.NameComponent
import cz.pacmanplus.game.core.components.control.PlayerInputComponent
import cz.pacmanplus.game.core.components.physics.*
import cz.pacmanplus.game.core.components.pickup.PickupComponent
import ktx.artemis.entity
import org.koin.mp.KoinPlatform.getKoin
import org.slf4j.LoggerFactory

object ItemFactory {

    val log = LoggerFactory.getLogger("ObstacleFactory")

    fun createCoin(x: Float, y: Float): Int {
        val world: World = getKoin().get<World>()
        val entity = world.createEntity()
        val edit = entity.edit()


        val nameComponent = edit.create(NameComponent::class.java)
        nameComponent.name = "Coin"

        val positionComponent = edit.create(PositionComponent::class.java)
        positionComponent.x = x
        positionComponent.y = y

        val rectangleCollisionComponent = edit.create(CircleCollisionComponent::class.java)
        rectangleCollisionComponent.radius = 8f

        val pickupComponent = edit.create(PickupComponent::class.java)

        log.info("Wall entity created id: ${entity.id} named: $nameComponent on position: $positionComponent")
        return entity.id
    }
}
