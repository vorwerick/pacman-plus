package cz.pacmanplus.game.core.entity

import com.artemis.World
import cz.pacmanplus.game.core.components.control.PlayerInputComponent
import cz.pacmanplus.game.core.components.physics.*
import org.koin.mp.KoinPlatform.getKoin
import org.slf4j.LoggerFactory

class CharacterCreator {

    val log = LoggerFactory.getLogger("CharacterCreator")

    fun player(x: Float, y: Float) {
        newEntity("Player").apply {

            create(PlayerInputComponent::class.java)
            create(PositionComponent::class.java).apply {
                val currentXTile = ((x + 16) / 32).toInt()
                val currentYTile = ((y + 16) / 32).toInt()
                this.x = currentXTile * 32f
                this.y = currentYTile * 32f
            }
            create(HealthComponent::class.java).apply {
                lives = 3
                bleeding = false
                invulnerability = true
            }
            create(CircleCollisionComponent::class.java).apply {
                radius = 15f
            }
            create(MovementComponent::class.java).apply {
                maxSpeed = 30f
            }
            create(PushComponent::class.java).apply {
                pushAmount = 0f
                pushForce = 40f
            }


        }

    }
}
