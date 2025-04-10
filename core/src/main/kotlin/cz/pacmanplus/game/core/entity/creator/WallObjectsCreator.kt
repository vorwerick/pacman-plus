package cz.pacmanplus.game.core.entity.creator

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasSprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import cz.pacmanplus.game.core.components.attributes.ActivateComponent
import cz.pacmanplus.game.core.components.attributes.DelayComponent
import cz.pacmanplus.game.core.components.attributes.ExplosionComponent
import cz.pacmanplus.game.core.components.attributes.PressureComponent
import cz.pacmanplus.game.core.components.graphics.TexturesComponent
import cz.pacmanplus.game.core.components.objects.WallComponent
import cz.pacmanplus.game.core.components.physics.*
import cz.pacmanplus.game.core.entity.WallObjects
import cz.pacmanplus.game.core.entity.newEntity
import org.slf4j.LoggerFactory
import java.awt.TextComponent

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


            create(TexturesComponent::class.java).apply {
                val texture = Texture("egypt/Walls_Overlap.png")
                val frames = TextureRegion.split(texture, 32, 38)
                textures = frames.flatten()
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
            create(TexturesComponent::class.java).apply {
                val texture = Texture("temp/wall.png")
                val frames = TextureRegion.split(texture, 32, 32)
                textures = frames.flatten()
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
            create(TexturesComponent::class.java).apply {
                val texture = Texture("temp/box.png")
                val frames = TextureRegion.split(texture, 32, 32)
                textures = frames.flatten()
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
            create(TexturesComponent::class.java).apply {
                val texture = Texture("temp/chest.png")
                val frames = TextureRegion.split(texture, 32, 32)
                textures = frames.flatten()
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
            create(HitPointsComponent::class.java).apply {
                state = HitPoint.Invulnerable
            }

            create(TexturesComponent::class.java).apply {
                val texture = Texture("temp/stone.png")
                val frames = TextureRegion.split(texture, 32, 32)
                textures = frames.flatten()
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
            create(TexturesComponent::class.java).apply {
                val texture = Texture("temp/door.png")
                val frames = TextureRegion.split(texture, 32, 32)
                textures = frames.flatten()
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

            create(TexturesComponent::class.java).apply {
                val texture = Texture("temp/bomb.png")
                val frames = TextureRegion.split(texture, 32, 32)
                textures = frames.flatten()
            }
        }


    }
}
