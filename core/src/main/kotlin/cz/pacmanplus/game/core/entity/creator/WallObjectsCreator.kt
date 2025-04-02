package cz.pacmanplus.game.core.entity.creator

import cz.pacmanplus.game.core.components.attributes.ActivateComponent
import cz.pacmanplus.game.core.components.attributes.DelayComponent
import cz.pacmanplus.game.core.components.objects.WallObjectComponent
import cz.pacmanplus.game.core.components.physics.*
import cz.pacmanplus.game.core.entity.WallObjects
import cz.pacmanplus.game.core.entity.newEntity
import org.slf4j.LoggerFactory

class WallObjectsCreator : WallObjects {

    companion object {
        const val FLOOR_SIZE = 32
    }

    val log = LoggerFactory.getLogger("WallFactory")


    override fun bedrock(x: Float, y: Float) {
        newEntity("Bedrock").apply {

            create(WallObjectComponent::class.java)
            create(PositionComponent::class.java).apply {
                this.x = x
                this.y = y
            }
            create(RectangleCollisionComponent::class.java).apply {
                width = FLOOR_SIZE * 1f
                height = FLOOR_SIZE * 1f
                solid = true
            }
            create(HitPointsComponent::class.java).apply {
                state = HitPoint.Invulnerable
            }
        }
    }

    override fun wall(x: Float, y: Float, hitPoints: Int) {
        newEntity("Wall").apply {
            create(WallObjectComponent::class.java)
            create(PositionComponent::class.java).apply {
                this.x = x
                this.y = y
            }
            create(RectangleCollisionComponent::class.java).apply {
                width = FLOOR_SIZE * 1f
                height = FLOOR_SIZE * 1f
                solid = true
            }
            create(HitPointsComponent::class.java).apply {
                state = HitPoint.Alive(hitPoints)
            }
        }
    }

    override fun box(x: Float, y: Float, hitPoints: Int) {
        newEntity("Box").apply {
            create(WallObjectComponent::class.java)
            create(PushableComponent::class.java)
            create(MovementComponent::class.java)
            create(PositionComponent::class.java).apply {
                this.x = x
                this.y = y
            }
            create(RectangleCollisionComponent::class.java).apply {
                width = FLOOR_SIZE * 1f
                height = FLOOR_SIZE * 1f
                solid = true
            }
            create(HitPointsComponent::class.java).apply {
                state = HitPoint.Alive(hitPoints)
            }
        }
    }

    override fun chest(x: Float, y: Float, hitPoints: Int) {
        newEntity("Chest").apply {
            create(WallObjectComponent::class.java)
            create(PositionComponent::class.java).apply {
                this.x = x
                this.y = y
            }
            create(RectangleCollisionComponent::class.java).apply {
                width = FLOOR_SIZE * 1f
                height = FLOOR_SIZE * 1f
                solid = true
            }
            create(HitPointsComponent::class.java).apply {
                state = HitPoint.Alive(hitPoints)
            }
            create(LootComponent::class.java).apply {
                loot = Item.Life
            }
        }
    }

    override fun stone(x: Float, y: Float) {
        newEntity("Stone").apply {
            create(WallObjectComponent::class.java)
            create(PushableComponent::class.java)
            create(MovementComponent::class.java)
            create(PositionComponent::class.java).apply {
                this.x = x
                this.y = y
            }
            create(RectangleCollisionComponent::class.java).apply {
                width = FLOOR_SIZE * 1f
                height = FLOOR_SIZE * 1f
                solid = true
            }
            create(HitPointsComponent::class.java).apply {
                state = HitPoint.Invulnerable
            }
        }
    }

    override fun gate(x: Float, y: Float, enabled: Boolean) {
        newEntity("Gate").apply {
            create(WallObjectComponent::class.java)
            create(ActivateComponent::class.java).apply {
                activated = enabled
            }
            create(PositionComponent::class.java).apply {
                this.x = x
                this.y = y
            }
            create(RectangleCollisionComponent::class.java).apply {
                width = FLOOR_SIZE * 1f
                height = FLOOR_SIZE * 1f
                solid = true
            }
            create(HitPointsComponent::class.java).apply {
                state = HitPoint.Invulnerable
            }
        }
    }

    override fun door(x: Float, y: Float) {
        newEntity("Door").apply {
            create(WallObjectComponent::class.java)
            create(PositionComponent::class.java).apply {
                this.x = x
                this.y = y
            }
            create(RectangleCollisionComponent::class.java).apply {
                width = FLOOR_SIZE * 1f
                height = FLOOR_SIZE * 1f
                solid = true
            }
            create(HitPointsComponent::class.java).apply {
                state = HitPoint.Invulnerable
            }
        }
    }

    override fun bomb(x: Float, y: Float) {
        newEntity("Bomb").apply {
            create(WallObjectComponent::class.java)
            create(PushableComponent::class.java)
            create(MovementComponent::class.java)
            create(PositionComponent::class.java).apply {
                this.x = x - 14
                this.y = y - 14
            }
            create(RectangleCollisionComponent::class.java).apply {
                width = FLOOR_SIZE - 4 * 1f
                height = FLOOR_SIZE - 4 * 1f
                solid = false
            }
            create(DelayComponent::class.java).apply {
                delay = 3000f
            }
        }


    }
}
