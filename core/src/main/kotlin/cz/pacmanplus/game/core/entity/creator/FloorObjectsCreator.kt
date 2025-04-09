package cz.pacmanplus.game.core.entity.creator

import com.badlogic.gdx.math.Vector2
import cz.pacmanplus.game.core.components.attributes.*
import cz.pacmanplus.game.core.components.objects.FloorComponent
import cz.pacmanplus.game.core.components.physics.*
import cz.pacmanplus.game.core.entity.FloorObjects
import cz.pacmanplus.game.core.entity.newEntity
import org.slf4j.LoggerFactory

class FloorObjectsCreator : FloorObjects {

    companion object {
        const val FLOOR_SIZE = 32
    }

    val log = LoggerFactory.getLogger("FloorObjectsCreator")


    override fun floor(x: Float, y: Float) {
        newEntity("Floor").apply {

            create(FloorComponent::class.java)
            create(PositionComponent::class.java).apply {
                this.x = x
                this.y = x
            }
            create(RectangleCollisionComponent::class.java).apply {
                width = FLOOR_SIZE * 1f
                height = FLOOR_SIZE * 1f
                solid = false
            }
        }
    }

    override fun switch(x: Float, y: Float, group: Int) {
        newEntity("Switch").apply {

            create(FloorComponent::class.java)
            create(PositionComponent::class.java).apply {
                this.x = x + 12
                this.y = y + 12
            }
            create(RectangleCollisionComponent::class.java).apply {
                width = (FLOOR_SIZE / 3) * 1f
                height = (FLOOR_SIZE / 3) * 1f
                solid = false
            }
            create(SwitchableComponent::class.java).apply {
                enabled = false
                groupId = group
            }
        }
    }

    override fun trigger(x: Float, y: Float, group: Int) {
        newEntity("Trigger").apply {

            create(FloorComponent::class.java)
            create(PositionComponent::class.java).apply {
                this.x = x + 12
                this.y = y + 12
            }
            create(RectangleCollisionComponent::class.java).apply {
                width = (FLOOR_SIZE / 3) * 1f
                height = (FLOOR_SIZE / 3) * 1f
                solid = false
            }
            create(ButtonComponent::class.java).apply {
                triggered = false
                groupId = group
            }
        }
    }

    override fun enemySpawner(x: Float, y: Float) {
        newEntity("EnemySpawner").apply {

            create(FloorComponent::class.java)
            create(PositionComponent::class.java).apply {
                this.x = x + 12
                this.y = y + 12
            }

        }
    }

    override fun enemyGate(x: Float, y: Float) {
        newEntity("EnemyGate").apply {

            create(FloorComponent::class.java)
            create(PositionComponent::class.java).apply {
                this.x = x
                this.y = x
            }
        }
    }

    override fun teleport(x: Float, y: Float, addressId: Int, targetId: Int) {
        newEntity("Teleport").apply {

            create(FloorComponent::class.java)
            create(PositionComponent::class.java).apply {
                this.x = x + 16
                this.y = y + 16
            }
            create(RectangleCollisionComponent::class.java).apply {
                width = (FLOOR_SIZE / 3) * 1f
                height = (FLOOR_SIZE / 3) * 1f
                solid = false
            }
            create(TeleportComponent::class.java).apply {
                address = addressId
                target = targetId
            }
        }
    }

    override fun lava(x: Float, y: Float) {
        newEntity("Lava").apply {

            create(FloorComponent::class.java)
            create(PositionComponent::class.java).apply {
                this.x = x
                this.y = x
            }
        }

    }

    override fun explosion(x: Float, y: Float) {
        newEntity("Explosion").apply {
            create(FloorComponent::class.java)
            create(PositionComponent::class.java).apply {
                this.x = x -16
                this.y = y - 16
            }
            create(RectangleCollisionComponent::class.java).apply {
                width = (FLOOR_SIZE )* 1f
                height = (FLOOR_SIZE )*1f
            }
            create(DamageComponent::class.java).apply {
                persistent = true
            }
            create(LifespanComponent::class.java).apply {
                frames = 30
            }
        }
    }

    override fun trapdoor(x: Float, y: Float) {
        newEntity("Trapdoor").apply {

            create(FloorComponent::class.java)
            create(PositionComponent::class.java).apply {
                this.x = x
                this.y = x
            }
        }
    }

    override fun ventilator(x: Float, y: Float, direction: Vector2, force: Int) {
        newEntity("Ventilator").apply {

            create(FloorComponent::class.java)
            create(PositionComponent::class.java).apply {
                this.x = x
                this.y = x
            }
        }
    }

    override fun void(x: Float, y: Float) {
        newEntity("Void").apply {

            create(FloorComponent::class.java)
            create(PositionComponent::class.java).apply {
                this.x = x
                this.y = x
            }
        }
    }

    override fun start(x: Float, y: Float) {
        newEntity("PlayerStart").apply {

            create(FloorComponent::class.java)
            create(PositionComponent::class.java).apply {
                this.x = x
                this.y = x
            }
        }
    }

    override fun finish(x: Float, y: Float) {
        newEntity("PlayerFinish").apply {

            create(FloorComponent::class.java)
            create(PositionComponent::class.java).apply {
                this.x = x
                this.y = x
            }
        }
    }

    override fun boxSpawner(x: Float, y: Float) {
        newEntity("BoxSpawner").apply {

            create(FloorComponent::class.java)
            create(PositionComponent::class.java).apply {
                this.x = x
                this.y = x
            }
        }
    }
}
