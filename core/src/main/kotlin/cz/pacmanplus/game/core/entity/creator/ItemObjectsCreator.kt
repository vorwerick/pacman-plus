package cz.pacmanplus.game.core.entity.creator

import cz.pacmanplus.game.core.components.physics.CircleCollisionComponent
import cz.pacmanplus.game.core.components.physics.PositionComponent
import cz.pacmanplus.game.core.components.pickup.ScoreComponent
import cz.pacmanplus.game.core.entity.ItemObjects
import cz.pacmanplus.game.core.entity.newEntity
import org.slf4j.LoggerFactory

class ItemObjectsCreator : ItemObjects {

    companion object {
        const val FLOOR_SIZE = 32
    }

    val log = LoggerFactory.getLogger("ItemObjectsCreator")


    override fun life(x: Float, y: Float) {
        newEntity("Life").apply {
            create(PositionComponent::class.java).apply {
                this.x = x
                this.y = y
            }
            create(ScoreComponent::class.java).apply {
                amount = 10
            }
        }
    }

    override fun shield(x: Float, y: Float) {
        newEntity("Shield").apply {
            create(PositionComponent::class.java).apply {
                this.x = x
                this.y = y
            }
            create(ScoreComponent::class.java).apply {
                amount = 10
            }
        }
    }

    override fun score(x: Float, y: Float) {
        newEntity("ScorePoint").apply {
            create(PositionComponent::class.java).apply {
                this.x = x
                this.y = y
            }
            create(ScoreComponent::class.java).apply {
                amount = 10
            }
        }
    }

    override fun key(x: Float, y: Float) {
        TODO("Not yet implemented")
    }

    override fun dash(x: Float, y: Float) {
        TODO("Not yet implemented")
    }

    override fun sprint(x: Float, y: Float) {
        TODO("Not yet implemented")
    }
}
