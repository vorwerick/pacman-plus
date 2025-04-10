package cz.pacmanplus.game.core.systems.physics.movement

import com.artemis.Aspect
import com.artemis.Entity
import com.artemis.systems.EntityProcessingSystem
import com.badlogic.gdx.math.Vector2
import cz.pacmanplus.game.core.components.physics.*
import cz.pacmanplus.game.core.entity.ItemObjects
import cz.pacmanplus.utils.delete
import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.LoggerFactory

// system for all movable boxes
class UnlockingSystem :
    EntityProcessingSystem(
        Aspect.all(
            UnlockableComponent::class.java,
            PositionComponent::class.java,
            RectangleCollisionComponent::class.java,
        )
    ) {
    val log = LoggerFactory.getLogger("UnlockingSystem")


    override fun process(e: Entity?) {


        val friction = 1f
        e?.let { entity: Entity ->

            val positionComponent = entity.getComponent(PositionComponent::class.java)
            entity.getComponent(UnlockableComponent::class.java)?.let { unlockableComponent ->
                unlockableComponent.availableKeys?.let { keys ->
                    if (keys[unlockableComponent.keyType]) {
                        //unlock
                        entity.getComponent(LootComponent::class.java)?.let { loot ->
                            val itemCreator = getKoin().get<ItemObjects>()
                            when (loot.loot) {
                                Item.Empty -> {}
                                Item.Life -> itemCreator.life(positionComponent.x, positionComponent.y)
                                Item.Score -> {}
                                Item.Sprint -> itemCreator.sprint(positionComponent.x, positionComponent.y)
                            }

                        }
                        delete(entity.id, "Object was unlocked and destroyed")
                    } else {
                        //fail
                    }
                    unlockableComponent.availableKeys = null
                }

            }


        }
    }


}

