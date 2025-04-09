package cz.pacmanplus.game.core.systems.physics.collisions

import com.artemis.Aspect
import com.artemis.Entity
import com.artemis.World
import com.artemis.systems.EntityProcessingSystem
import com.artemis.utils.IntBag
import com.badlogic.gdx.math.Circle
import cz.pacmanplus.game.GameState
import cz.pacmanplus.game.core.components.physics.CircleCollisionComponent
import cz.pacmanplus.game.core.components.physics.PositionComponent
import cz.pacmanplus.game.core.components.pickup.BuffComponent
import cz.pacmanplus.game.core.components.pickup.KeyComponent
import cz.pacmanplus.game.core.components.attributes.InventoryComponent
import cz.pacmanplus.game.core.components.pickup.ScoreComponent
import cz.pacmanplus.game.core.components.pickup.SlotItemComponent
import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.LoggerFactory

/// For entities who can pick
class PickupSystem :
    EntityProcessingSystem(
        Aspect.all(InventoryComponent::class.java, PositionComponent::class.java, CircleCollisionComponent::class.java)
    ) {
    val log = LoggerFactory.getLogger("PickupSystem")


    override fun process(e: Entity?) {

        e?.let { entity: Entity ->
            val positionComponent = entity.getComponent(PositionComponent::class.java)
            val circleCollisionComponent = entity.getComponent(CircleCollisionComponent::class.java)
            val circle = Circle(positionComponent.x, positionComponent.y, circleCollisionComponent.radius)


            val items = world.findPickupItems()

            (0 until items.size()).forEach { index ->
                val id = items.get(index)
                val itemEntity = world.getEntity(id)
                val position = itemEntity.getComponent(PositionComponent::class.java)

                if (circle.contains(position.x, position.y)) {
                    val destroy = consumeForDestroy(itemEntity, entity)
                    if (destroy) {
                        world.delete(id)
                    }
                }

            }
        }
    }

    private fun consumeForDestroy(item: Entity, picker: Entity): Boolean {

        item.getComponent(KeyComponent::class.java)?.let { key ->

            if (picker.getComponent(InventoryComponent::class.java).keyring[key.number]) {
                return false
            }

            picker.getComponent(InventoryComponent::class.java).keyring[key.number] = true
            return true
        }

        item.getComponent(ScoreComponent::class.java)?.let { score ->

            picker.getComponent(InventoryComponent::class.java).score += score.amount
            return true
        }

        item.getComponent(SlotItemComponent::class.java)?.let { slotItem ->

            if (picker.getComponent(InventoryComponent::class.java).slot != null) {
                return false
            }
            picker.getComponent(InventoryComponent::class.java).slot = slotItem.id
            return true
        }

        return false
    }
}

fun World.findPickupItems(): IntBag {
    return this.aspectSubscriptionManager.get(
        Aspect
            .one(KeyComponent::class.java, ScoreComponent::class.java, SlotItemComponent::class.java)
            .all(PositionComponent::class.java)
    ).entities ?: IntBag()
}
