package cz.pacmanplus.game.core.entity

import com.artemis.World
import com.badlogic.gdx.math.Vector2
import cz.pacmanplus.game.core.components.common.NameComponent
import cz.pacmanplus.game.core.components.control.ForcePushingComponent
import cz.pacmanplus.game.core.components.control.PlayerInputComponent
import cz.pacmanplus.game.core.components.physics.*
import org.koin.mp.KoinPlatform.getKoin
import org.slf4j.LoggerFactory

object CharacterFactory {

    val log = LoggerFactory.getLogger("CharacterFactory")

    fun createPlayer(x: Float, y: Float): Int {
        val world: World = getKoin().get<World>()
        val entity = world.createEntity()
        val edit = entity.edit()

        val playerInputComponent = edit.create(PlayerInputComponent::class.java)

        val nameComponent = edit.create(NameComponent::class.java)
        nameComponent.name = "Pacman"

        val positionComponent = edit.create(PositionComponent::class.java)
        val currentXTile = ((x + 16) / 32).toInt()
        val currentYTile = ((y + 16) / 32).toInt()
        positionComponent.x = currentXTile * 32f
        positionComponent.y = currentYTile * 32f

        val healthComponent = edit.create(HealthComponent::class.java)
        healthComponent.lives = 3
        healthComponent.bleeding = false
        healthComponent.invulnerability = true

        val circleCollisionComponent = edit.create(CircleCollisionComponent::class.java)
        circleCollisionComponent.radius = 15f

        val directionComponent = edit.create(DirectionComponent::class.java)
        directionComponent.x = 0f
        directionComponent.y = 0f

        val movementComponent = edit.create(MovementComponent::class.java)
        movementComponent.maxSpeed = 30f





        log.info("Player entity created id: ${entity.id} named: $nameComponent on position: $positionComponent")
        return entity.id
    }
}
