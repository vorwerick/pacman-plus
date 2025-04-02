package cz.pacmanplus.game.core.systems.update

import com.artemis.Aspect
import com.artemis.Entity
import com.artemis.EntitySubscription
import com.artemis.World
import com.artemis.systems.EntityProcessingSystem
import com.badlogic.gdx.Gdx
import cz.pacmanplus.game.GameState
import cz.pacmanplus.game.core.components.attributes.DelayComponent
import cz.pacmanplus.game.core.components.physics.PositionComponent
import cz.pacmanplus.game.core.components.physics.RectangleCollisionComponent
import cz.pacmanplus.game.core.entity.FloorObjects
import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.LoggerFactory

class BombSystem :
    EntityProcessingSystem(
        Aspect.all(
            DelayComponent::class.java, PositionComponent::class.java,
        )
    ) {
    val log = LoggerFactory.getLogger("BombSystem")


    override fun process(e: Entity?) {
        if (e == null) return

        val gameState: GameState = getKoin().get()
        if (gameState.paused) {
            return
        }

        e.let { entity: Entity ->
            val delta = Gdx.graphics.deltaTime
            val delayComponent = entity.getComponent(DelayComponent::class.java)
            val positionComponent = entity.getComponent(PositionComponent::class.java)
            val destroyables = world.aspectSubscriptionManager.get(Aspect.all(RectangleCollisionComponent::class.java)).entities(world)
            val floorObjects: FloorObjects = getKoin().get()
            val bombRadius = 3
            if (delayComponent.isFinished()) {
                for (i in 1..bombRadius) {


                    floorObjects.explosion(
                        ((positionComponent.x / 32) + i) * 32f,
                        positionComponent.y
                    )
                    val any = destroyables.any {
                        val pos = it.getComponent(PositionComponent::class.java)
                        val tileX =    ((positionComponent.x / 32) + i)
                        val tileY = positionComponent.y / 32
                        return@any it.getComponent(PositionComponent::class.java).x == tileX && it.getComponent(PositionComponent::class.java).y == tileY
                    }
                    if(any){
                        break
                    }
                }

                for (i in -3..bombRadius) {
                    floorObjects.explosion(
                        positionComponent.x,
                        ((positionComponent.y / 32) + i) * 32f,
                    )
                    val any = destroyables.any {
                        val pos = it.getComponent(PositionComponent::class.java)
                        val tileX =    ((positionComponent.x / 32) + i)
                        val tileY = positionComponent.y / 32
                        return@any it.getComponent(PositionComponent::class.java).x == tileX && it.getComponent(PositionComponent::class.java).y == tileY
                    }
                    if(any){
                        break
                    }
                }

                for (i in -1 downTo -bombRadius) {
                    floorObjects.explosion(
                        ((positionComponent.x / 32) + i) * 32f,
                        positionComponent.y
                    )

                    val any = destroyables.any {
                        val pos = it.getComponent(PositionComponent::class.java)
                        val tileX =    ((positionComponent.x / 32) + i)
                        val tileY = positionComponent.y / 32
                        return@any it.getComponent(PositionComponent::class.java).x == tileX && it.getComponent(PositionComponent::class.java).y == tileY
                    }
                    if(any){
                        break
                    }
                }

                for (i in -1 downTo -bombRadius) {
                    floorObjects.explosion(
                        positionComponent.x,
                        ((positionComponent.y / 32) + i) * 32f,
                    )

                    val any = destroyables.any {
                        val pos = it.getComponent(PositionComponent::class.java)
                        val tileX =    ((positionComponent.x / 32) + i)
                        val tileY = positionComponent.y / 32
                        return@any it.getComponent(PositionComponent::class.java).x == tileX && it.getComponent(PositionComponent::class.java).y == tileY
                    }
                    if(any){
                        break
                    }
                }


                world.delete(e.id)
            }

        }
    }


}

fun EntitySubscription.entities(world: World): List<Entity> {
    val list = mutableListOf<Entity>()
    for(i in 0 until this.entities.size()){
        list.add(world.getEntity(entities[i]))
    }

    return list
}

