package cz.pacmanplus.game.core.systems.rendering

import com.artemis.Aspect
import com.artemis.BaseSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.kotcrab.vis.ui.VisUI
import cz.pacmanplus.game.core.components.physics.*
import cz.pacmanplus.game.core.components.attributes.InventoryComponent
import cz.pacmanplus.game.core.systems.findPlayer
import cz.pacmanplus.game.core.systems.physics.collisions.findPickupItems
import org.koin.java.KoinJavaComponent.getKoin

class EntityGuiSystem() : BaseSystem() {

    private val skin = VisUI.getSkin()
    val entitiesState = Label("", skin)
    val entityInfo = Label("", skin)
    val entityInventory = Label("", skin)

    var frameTime = 0f

    init {

    }

    override fun initialize() {
        super.initialize()
        val stage = getKoin().get<Stage>()

        stage.addActor(entitiesState)
        stage.addActor(entityInfo)
        stage.addActor(entityInventory)

        entitiesState.y += 128
        entityInfo.y += 96
        entityInventory.y += 128+32


    }

    override fun processSystem() {
        val stage = getKoin().get<Stage>()
        if((System.currentTimeMillis()*1000 ).toInt() % 5== 0){
            frameTime = Gdx.graphics.deltaTime
        }

        val entities = world.aspectSubscriptionManager.get(Aspect.all())
    //    entityInfo.setText("FRAME_TIME: ${(frameTime*1000).toInt()}ms | ENTITY_COUNT: ${entities.entities.size()}/${entities.entities.capacity}")

        val walls = world.aspectSubscriptionManager.get(Aspect.all(RectangleCollisionComponent::class.java))
        val pickups = world.findPickupItems()
   //     entitiesState.setText("WALLS_&_BOXES: ${walls.entities.size()} | COLLECTABLES: ${pickups.size()}")

        val player = world.findPlayer()
        player?.let { p ->
            p.getComponent(InventoryComponent::class.java)?.let { inventory ->
            //    entitiesState.setText("SCORE: ${inventory.score} | SLOT: ${inventory.slot} | KEYS: ${inventory.keyring.joinToString(" ")}")

            }
        }


        stage.act()
        stage.draw()
    }


}
