package cz.pacmanplus.game.core.entity

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import cz.pacmanplus.game.core.components.attributes.InventoryComponent
import cz.pacmanplus.game.core.components.attributes.PressureComponent
import cz.pacmanplus.game.core.components.control.CharacterComponent
import cz.pacmanplus.game.core.components.control.ComputerPathComponent
import cz.pacmanplus.game.core.components.control.PlayerComponent
import cz.pacmanplus.game.core.components.control.InputComponent
import cz.pacmanplus.game.core.components.control.computer.FindPlayerComponent
import cz.pacmanplus.game.core.components.graphics.DrawableStateComponent
import cz.pacmanplus.game.core.components.physics.*
import cz.pacmanplus.game.graphics.EnemyMummy
import cz.pacmanplus.game.graphics.Player
import org.slf4j.LoggerFactory

class CharacterCreator {

    val log = LoggerFactory.getLogger("CharacterCreator")

    fun player(x: Float, y: Float) {
        newEntity("Player").apply {
            create(CharacterComponent::class.java)
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
            create(DrawableStateComponent::class.java).apply {
                addDrawableState(Player(0))
                addDrawableState(Player(1))
                addDrawableState(Player(2))
                addDrawableState(Player(3))
            }
        }
    }

    fun enemyPatrol(x: Float, y: Float) {
        newEntity("Enemy patrol").apply {
            create(CharacterComponent::class.java)
            create(ComputerPathComponent::class.java)
            create(FindPlayerComponent::class.java)
            // create(InputComponent::class.java)
            create(PositionComponent::class.java).apply {
                val currentXTile = ((x + 16) / 32).toInt()
                val currentYTile = ((y + 16) / 32).toInt()
                this.x = currentXTile * 32f
                this.y = currentYTile * 32f
            }
            create(HealthComponent::class.java).apply {
                lives = 1
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
            create(DrawableStateComponent::class.java).apply {
                addDrawableState(EnemyMummy(0))
                addDrawableState(EnemyMummy(1))
                addDrawableState(EnemyMummy(2))
                addDrawableState(EnemyMummy(3))
            }
        }
    }
}
