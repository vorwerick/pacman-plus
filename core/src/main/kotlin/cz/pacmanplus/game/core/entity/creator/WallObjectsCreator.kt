package cz.pacmanplus.game.core.entity.creator

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import cz.pacmanplus.game.core.components.attributes.*
import cz.pacmanplus.game.core.components.graphics.DrawableStateComponent
import cz.pacmanplus.game.core.components.objects.WallComponent
import cz.pacmanplus.game.core.components.physics.*
import cz.pacmanplus.game.core.entity.WallObjects
import cz.pacmanplus.game.core.entity.newEntity
import cz.pacmanplus.game.graphics.*
import cz.pacmanplus.screens.Loader
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
            create(LifecycleComponent::class.java).apply {
                setInvulnerable()
            }

            create(DrawableStateComponent::class.java).apply {
                addDrawableState(Wall(3))
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
            create(LifecycleComponent::class.java).apply {
                setAlive(hitPoints)
            }
            create(DrawableStateComponent::class.java).apply {
                addDrawableState(Wall(3))
                addDrawableState(Wall(2))
                addDrawableState(Wall(1))
                addDrawableState(Wall(0))
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
            create(LifecycleComponent::class.java).apply {
                setAlive(hitPoints)
            }
            create(PressureComponent::class.java).apply {

            }
            create(DrawableStateComponent::class.java).apply {
                addDrawableState(Box())
            }
        }
    }

    override fun turret(x: Float, y: Float, hitPoints: Int, direction: Vector2) {
        newEntity("Turret").apply {
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
            create(LifecycleComponent::class.java).apply {
                setAlive(hitPoints)
            }
            create(PressureComponent::class.java).apply {

            }
            create(SpeedComponent::class.java).apply {
                this.speed = 150
            }
            create(DirectionComponent::class.java).apply {
                this.direction = direction
            }
            create(TimeComponent::class.java).apply {
                setTimer(seconds = 2)
            }
            create(DrawableStateComponent::class.java).apply {
                addDrawableState(Wall(3))
                addDrawableState(Wall(2))
                addDrawableState(Wall(1))
                addDrawableState(Wall(0))
            }
        }
    }

    override fun generator(x: Float, y: Float, hitPoints: Int) {
        newEntity("Generator").apply {
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
            create(LifecycleComponent::class.java).apply {
                setAlive(hitPoints)
            }
            create(GeneratorComponent::class.java).apply {

            }
            create(DrawableStateComponent::class.java).apply {
                addDrawableState(Wall(3))
                addDrawableState(Wall(2))
                addDrawableState(Wall(1))
                addDrawableState(Wall(0))
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
            create(LifecycleComponent::class.java).apply {
                setAlive(hitPoints)
            }
            create(LootComponent::class.java).apply {
                loot = Item.Life
            }
            create(UnlockableComponent::class.java).apply {
                this.keyType = keyType
            }
            create(DrawableStateComponent::class.java).apply {
                addDrawableState(Chest(locked = true))
                addDrawableState(Chest(locked = false))
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
                this.x = x + 8
                this.y = y + 8
            }
            create(RectangleCollisionComponent::class.java).apply {
                width = FLOOR_SIZE * 1f / 2
                height = FLOOR_SIZE * 1f / 2
                solid = true
            }
            create(LifecycleComponent::class.java).apply {
                setInvulnerable()
            }

            create(DrawableStateComponent::class.java).apply {
                addDrawableState(Wall(3))
                addDrawableState(Wall(2))
                addDrawableState(Wall(1))
                addDrawableState(Wall(0))
            }
        }

    }

    override fun gate(x: Float, y: Float, group: Int) {
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
            create(LifecycleComponent::class.java).apply {
                setInvulnerable()
            }
            create(DrawableStateComponent::class.java).apply {
                addDrawableState(Gate(opened = false))
                addDrawableState(Gate(opened = true))
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
            create(LifecycleComponent::class.java).apply {
                setInvulnerable()
            }
            create(UnlockableComponent::class.java).apply {
                this.keyType = keyType
            }
            create(DrawableStateComponent::class.java).apply {
                addDrawableState(Gate(opened = false))
                addDrawableState(Gate(opened = true))
            }
        }
    }

    override fun bomb(x: Float, y: Float) {
        newEntity("Bomb").apply {
            create(WallComponent::class.java)
            create(PushableComponent::class.java)
            create(PositionComponent::class.java).apply {
                this.x = x - 16
                this.y = y - 16
            }
            create(RectangleCollisionComponent::class.java).apply {
                width = FLOOR_SIZE * 1f
                height = FLOOR_SIZE * 1f
                solid = true
            }
            create(TimeComponent::class.java).apply {
                setTimer(seconds = 3)
            }
            create(ExplosionComponent::class.java).apply {

            }
            create(LifecycleComponent::class.java).apply {
                setInvulnerable()
            }

            create(DrawableStateComponent::class.java).apply {
                addDrawableState(Bomb())
            }
        }


    }
}
