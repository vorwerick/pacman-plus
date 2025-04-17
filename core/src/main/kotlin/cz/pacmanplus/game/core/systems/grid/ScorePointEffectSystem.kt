package cz.pacmanplus.game.core.systems.grid

import com.artemis.Aspect
import com.artemis.Entity
import com.artemis.systems.EntityProcessingSystem
import cz.pacmanplus.game.core.components.objects.FloorComponent
import cz.pacmanplus.game.core.components.physics.PositionComponent
import cz.pacmanplus.utils.IterableGrid

class ScorePointEffectSystem(val width: Int, val height: Int) :
    EntityProcessingSystem(Aspect.one(FloorComponent::class.java, PositionComponent::class.java)) {

    val floorGrid = IterableGrid(width, height)

    override fun process(e: Entity?) {
        e?.let { entity ->
            entity.getComponent(FloorComponent::class.java)?.let { floor ->
                entity.getComponent(PositionComponent::class.java)?.let { position ->
                    floorGrid.add((position.x / 32).toInt(), (position.y / 32).toInt(), value = entity.id)
                }
            }
        }
    }
}
