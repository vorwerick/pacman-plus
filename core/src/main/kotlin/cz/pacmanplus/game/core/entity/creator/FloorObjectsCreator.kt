package cz.pacmanplus.game.core.entity.creator

import com.badlogic.gdx.math.Vector2
import cz.pacmanplus.game.core.components.attributes.*
import cz.pacmanplus.game.core.components.objects.FloorObjectComponent
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

            create(FloorObjectComponent::class.java)
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

    override fun switch(x: Float, y: Float, affectedIds: List<Int>) {
        newEntity("Switch").apply {

            create(FloorObjectComponent::class.java)
            create(PositionComponent::class.java).apply {
                this.x = x
                this.y = x
            }
            create(RectangleCollisionComponent::class.java).apply {
                width = (FLOOR_SIZE / 3) * 1f
                height = (FLOOR_SIZE / 3) * 1f
                solid = false
            }
            create(EnabledComponent::class.java).apply {
                enabled = false
            }
            create(AffectionComponent::class.java).apply {
                this.affectedGroup = affectedIds
            }
        }
    }

    override fun trigger(x: Float, y: Float, affectedIds: List<Int>) {
        newEntity("Trigger").apply {

            create(FloorObjectComponent::class.java)
            create(PositionComponent::class.java).apply {
                this.x = x + 12
                this.y = y + 12
            }
            create(RectangleCollisionComponent::class.java).apply {
                width = (FLOOR_SIZE / 3) * 1f
                height = (FLOOR_SIZE / 3) * 1f
                solid = false
            }
            create(TriggerableComponent::class.java).apply {
                triggered = false
            }
            create(AffectionComponent::class.java).apply {
                this.affectedGroup = affectedIds
            }
        }
    }

    override fun enemySpawner(x: Float, y: Float) {
        newEntity("EnemySpawner").apply {

            create(FloorObjectComponent::class.java)
            create(PositionComponent::class.java).apply {
                this.x = x
                this.y = x
            }
        }
    }

    override fun enemyGate(x: Float, y: Float) {
        newEntity("EnemyGate").apply {

            create(FloorObjectComponent::class.java)
            create(PositionComponent::class.java).apply {
                this.x = x
                this.y = x
            }
        }
    }

    override fun teleport(x: Float, y: Float, targetId: Int) {
        newEntity("Teleport").apply {

            create(FloorObjectComponent::class.java)
            create(PositionComponent::class.java).apply {
                this.x = x
                this.y = x
            }
        }
    }

    override fun lava(x: Float, y: Float) {
        newEntity("Lava").apply {

            create(FloorObjectComponent::class.java)
            create(PositionComponent::class.java).apply {
                this.x = x
                this.y = x
            }
        }

    }

    override fun explosion(x: Float, y: Float) {
        newEntity("Explosion").apply {
            create(FloorObjectComponent::class.java)
            create(PositionComponent::class.java).apply {
                this.x = x
                this.y = y
            }
            create(RectangleCollisionComponent::class.java).apply {
                width = (FLOOR_SIZE / 2) * 1f + 8
                height = (FLOOR_SIZE / 2) * 1f  + 8
            }
            create(LifespanComponent::class.java).apply {
                frames = 30
            }
        }
    }

    override fun trapdoor(x: Float, y: Float) {
        newEntity("Trapdoor").apply {

            create(FloorObjectComponent::class.java)
            create(PositionComponent::class.java).apply {
                this.x = x
                this.y = x
            }
        }
    }

    override fun ventilator(x: Float, y: Float, direction: Vector2, force: Int) {
        newEntity("Ventilator").apply {

            create(FloorObjectComponent::class.java)
            create(PositionComponent::class.java).apply {
                this.x = x
                this.y = x
            }
        }
    }

    override fun void(x: Float, y: Float) {
        newEntity("Void").apply {

            create(FloorObjectComponent::class.java)
            create(PositionComponent::class.java).apply {
                this.x = x
                this.y = x
            }
        }
    }

    override fun start(x: Float, y: Float) {
        newEntity("PlayerStart").apply {

            create(FloorObjectComponent::class.java)
            create(PositionComponent::class.java).apply {
                this.x = x
                this.y = x
            }
        }
    }

    override fun finish(x: Float, y: Float) {
        newEntity("PlayerFinish").apply {

            create(FloorObjectComponent::class.java)
            create(PositionComponent::class.java).apply {
                this.x = x
                this.y = x
            }
        }
    }

    override fun boxSpawner(x: Float, y: Float) {
        newEntity("BoxSpawner").apply {

            create(FloorObjectComponent::class.java)
            create(PositionComponent::class.java).apply {
                this.x = x
                this.y = x
            }
        }
    }
}
