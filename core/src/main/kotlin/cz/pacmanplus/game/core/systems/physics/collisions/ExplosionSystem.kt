package cz.pacmanplus.game.core.systems.physics.collisions

import com.artemis.Aspect
import com.artemis.Entity
import com.artemis.EntitySubscription
import com.artemis.World
import com.artemis.systems.EntityProcessingSystem
import com.badlogic.gdx.Gdx
import cz.pacmanplus.game.GameState
import cz.pacmanplus.game.core.components.attributes.DelayComponent
import cz.pacmanplus.game.core.components.attributes.ExplosionComponent
import cz.pacmanplus.game.core.components.physics.PositionComponent
import cz.pacmanplus.game.core.components.physics.RectangleCollisionComponent
import cz.pacmanplus.game.core.entity.FloorObjects
import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.LoggerFactory

//find all bombs and let it explodes
class ExplosionSystem :
    EntityProcessingSystem(
        Aspect.all(
            DelayComponent::class.java, PositionComponent::class.java, ExplosionComponent::class.java,
        )
    ) {
    val log = LoggerFactory.getLogger("BombSystem")


    override fun process(e: Entity?) {
        if (e == null) return


        e.let { entity: Entity ->
            val delta = Gdx.graphics.deltaTime
            val delayComponent = entity.getComponent(DelayComponent::class.java)
            if (!delayComponent.isFinished()) {
                return
            }

            val positionComponent = entity.getComponent(PositionComponent::class.java)
            val destroyables =
                world.aspectSubscriptionManager.get(Aspect.all(RectangleCollisionComponent::class.java)).entities(world)
            val floorObjects: FloorObjects = getKoin().get()
            val bombRadius = 3
            if (delayComponent.isFinished()) {
                val directions = listOf(
                    1 to 0,  // doprava
                    -1 to 0, // doleva
                    0 to 1,  // nahoru
                    0 to -1  // dolů
                )

                for ((dx, dy) in directions) {
                    var isObstacle = false
                    for (i in 1..bombRadius) {
                        val tileX = ((positionComponent.x / 32)).toInt() + (i * dx)
                        val tileY = ((positionComponent.y / 32)).toInt() + (i * dy)



                        destroyables.forEach { obstacle ->
                            val obstaclePos = obstacle.getComponent(PositionComponent::class.java)
                            val obstacleTileX = ((obstaclePos.x / 32)+16).toInt()
                            val obstacleTileY =( (obstaclePos.y / 32)+16).toInt()

                            if(obstacleTileX == tileX && obstacleTileY == tileY){
                                isObstacle = true
                            }
                        }


                        if(isObstacle){
                            break
                        } else {
                            floorObjects.explosion(tileX * 32f, tileY * 32f)
                        }
                    }
                }



                world.delete(e.id)
            }

        }
    }


}

fun EntitySubscription.entities(world: World): List<Entity> {
    val list = mutableListOf<Entity>()
    for (i in 0 until this.entities.size()) {
        list.add(world.getEntity(entities[i]))
    }

    return list
}

