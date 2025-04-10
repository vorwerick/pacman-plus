package cz.pacmanplus.game.core.systems.grid

import com.artemis.Aspect
import com.artemis.BaseSystem
import com.artemis.World
import com.artemis.utils.IntBag
import cz.pacmanplus.game.core.components.objects.WallComponent
import cz.pacmanplus.game.core.components.physics.PositionComponent
import cz.pacmanplus.utils.IterableGrid

class WallGridSystem(val width: Int, val height: Int) :BaseSystem() {

    val wallGrid = IterableGrid(width, height)

    fun sync() {

    }

    override fun processSystem() {
        val areas = world.findAllWallObjects()
        wallGrid.clear()
        wallGrid.forEachGrid {x, y, value ->
            (0 until areas.size()).forEach { index ->
                val id = areas.get(index)
                val e = world.getEntity(id)
                e.getComponent(WallComponent::class.java)?.let { wall ->
                    e.getComponent(PositionComponent::class.java)?.let { position ->
                        val entityX = ((position.x + 16) / 32).toInt()
                        val entityY = ((position.y + 16) / 32).toInt()
                        if (entityX == x && entityY == y) {
                            wallGrid.add(x, y, value = id)
                        }
                    }
                }
            }
        }
    }
}


fun World.findAllWallObjects(): IntBag {
    return this.aspectSubscriptionManager.get(
        Aspect
            .all(WallComponent::class.java, PositionComponent::class.java)
    ).entities ?: IntBag()
}
