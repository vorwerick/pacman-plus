package cz.pacmanplus.game.core.entity

import cz.pacmanplus.game.core.components.attributes.InventoryComponent
import cz.pacmanplus.game.core.components.attributes.PressureComponent
import cz.pacmanplus.game.core.components.control.ComputerComponent
import cz.pacmanplus.game.core.components.control.PlayerComponent
import cz.pacmanplus.game.core.components.control.InputComponent
import cz.pacmanplus.game.core.components.physics.*
import org.slf4j.LoggerFactory

class CharacterCreator {

    val log = LoggerFactory.getLogger("CharacterCreator")

    fun player(x: Float, y: Float) {
        newEntity("Player").apply {
            create(PlayerComponent::class.java)
            create(InputComponent::class.java)
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
            create(PassiveAbilitiesComponent::class.java).apply {
                unlockingValue = 0f
                pushForce = 0f
            }
            create(InventoryComponent::class.java).apply {
                slot = null
                score = 0
                keyring = Array(4) { true }
            }
            create(PressureComponent::class.java).apply {

            }
        }
    }

    fun enemyPatrol(x: Float, y: Float) {
        newEntity("Enemy patrol").apply {

            create(ComputerComponent::class.java)
            create(InputComponent::class.java)
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
            create(PressureComponent::class.java).apply {

            }
        }
    }
}
