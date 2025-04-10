package cz.pacmanplus.game.core.systems.physics.collisions

import com.artemis.Aspect
import com.artemis.Entity
import com.artemis.World
import com.artemis.systems.EntityProcessingSystem
import com.artemis.utils.IntBag
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Rectangle
import cz.pacmanplus.game.GameState
import cz.pacmanplus.game.core.components.pickup.KeyComponent
import cz.pacmanplus.game.core.components.attributes.InventoryComponent
import cz.pacmanplus.game.core.components.physics.*
import cz.pacmanplus.game.core.components.pickup.ScoreComponent
import cz.pacmanplus.game.core.components.pickup.SlotItemComponent
import cz.pacmanplus.game.core.systems.lifecycle.HitpointsLifecycleSystem
import cz.pacmanplus.utils.delete
import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.LoggerFactory

/// For entities who can pick
class DamageSystem :
    EntityProcessingSystem(
        Aspect.one(CircleCollisionComponent::class.java, RectangleCollisionComponent::class.java)
            .all(PositionComponent::class.java, HitPointsComponent::class.java)
    ) {
    val log = LoggerFactory.getLogger("DamageSystem")


    override fun process(e: Entity?) {

        e?.let { entity: Entity ->
            val positionComponent = entity.getComponent(PositionComponent::class.java)

            val areas = world.findDamageAreas()

            (0 until areas.size()).forEach { index ->
                val id = areas.get(index)
                val damageArea = world.getEntity(id)
                val areaPosition = damageArea.getComponent(PositionComponent::class.java)
                val areaRect = damageArea.getComponent(RectangleCollisionComponent::class.java)
                val areaCollider = Rectangle(areaPosition.x, areaPosition.y, areaRect.width, areaRect.height)

                entity.getComponent(CircleCollisionComponent::class.java)?.let { circle ->
                    val colliderCircle = Circle(positionComponent.x, positionComponent.y, circle.radius)
                    if (Intersector.overlaps(colliderCircle, areaCollider)) {
                        val destroy = dealDamageForDestroy(damageArea, entity)
                        if (destroy) {
                            delete(id, "Damage area overlaps the object")
                        }
                    }
                }

                entity.getComponent(RectangleCollisionComponent::class.java)?.let { rectangle ->
                    val colliderRect =
                        Rectangle(positionComponent.x, positionComponent.y, rectangle.width, rectangle.height)
                    if (Intersector.overlaps(colliderRect, areaCollider)) {
                        val destroy = dealDamageForDestroy(damageArea, entity)
                        if (destroy) {
                            delete(id, "Damage area overlaps the object")
                        }
                    }
                }
            }
        }
    }

    private fun dealDamageForDestroy(damageArea: Entity, damageGiver: Entity): Boolean {
        damageGiver.getComponent(HitPointsComponent::class.java)?.let { hitPoints ->
            if (hitPoints.state is HitPoint.Alive) {
                val aliveState = hitPoints.state as HitPoint.Alive
                if (aliveState.value <= 0) {
                    return false
                }
                damageArea.getComponent(DamageComponent::class.java)?.let { damage ->
                    hitPoints.state = HitPoint.Alive(aliveState.value - 1) // make damage
                    if (!damage.persistent) {
                        return true
                    }
                    return false
                }
            }
        }
        return false
    }
}

fun World.findDamageAreas(): IntBag {
    return this.aspectSubscriptionManager.get(
        Aspect
            .all(DamageComponent::class.java, RectangleCollisionComponent::class.java, PositionComponent::class.java)
    ).entities ?: IntBag()
}
