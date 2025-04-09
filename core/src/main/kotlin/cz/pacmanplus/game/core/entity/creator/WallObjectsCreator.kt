package cz.pacmanplus.game.core.entity.creator

import cz.pacmanplus.game.core.components.attributes.ActivateComponent
import cz.pacmanplus.game.core.components.attributes.DelayComponent
import cz.pacmanplus.game.core.components.attributes.ExplosionComponent
import cz.pacmanplus.game.core.components.attributes.PressureComponent
import cz.pacmanplus.game.core.components.objects.WallComponent
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

            create(WallComponent::class.java)
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
            create(WallComponent::class.java)
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
            create(WallComponent::class.java)
            create(PushableComponent::class.java)
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
            create(PressureComponent::class.java).apply {

            }
        }
    }

    override fun chest(x: Float, y: Float, hitPoints: Int, keyType: Int) {
        newEntity("Chest").apply {
            create(WallComponent::class.java)
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
            create(UnlockableComponent::class.java).apply {
                this.keyType = keyType
            }
        }
    }

    override fun stone(x: Float, y: Float) {
        newEntity("Stone").apply {
            create(WallComponent::class.java)
            create(PushableComponent::class.java)
            create(MovementComponent::class.java)
            create(PressureComponent::class.java)
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

    override fun gate(x: Float, y: Float,group: Int) {
        newEntity("Gate").apply {
            create(WallComponent::class.java)
            create(ActivateComponent::class.java).apply {
                this.group = group
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

    override fun door(x: Float, y: Float, keyType: Int) {
        newEntity("Door").apply {
            create(WallComponent::class.java)
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
            create(UnlockableComponent::class.java).apply {
                this.keyType = keyType
            }
        }
    }

    override fun bomb(x: Float, y: Float) {
        newEntity("Bomb").apply {
            create(WallComponent::class.java)
            //create(PushableComponent::class.java)
            create(PositionComponent::class.java).apply {
                this.x = x
                this.y = y
            }
            create(CircleCollisionComponent::class.java).apply {
                radius = 16f
            }
            create(DelayComponent::class.java).apply {
                delay = 3000f
            }
            create(ExplosionComponent::class.java).apply {

            }
        }


    }
}
