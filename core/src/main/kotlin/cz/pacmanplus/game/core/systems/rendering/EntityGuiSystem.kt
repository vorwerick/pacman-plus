package cz.pacmanplus.game.core.systems.rendering

import com.artemis.Aspect
import com.artemis.BaseSystem
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.kotcrab.vis.ui.VisUI
import cz.pacmanplus.game.PlayerCamera
import cz.pacmanplus.game.core.components.control.PlayerInputComponent
import cz.pacmanplus.game.core.components.physics.*
import cz.pacmanplus.game.core.components.pickup.PickupComponent
import cz.pacmanplus.game.core.systems.findPlayer
import org.koin.java.KoinJavaComponent.getKoin

class EntityGuiSystem() : BaseSystem() {

    private val skin = VisUI.getSkin()
    val entitiesState = Label("", skin)
    val entityInfo = Label("", skin)

    var frameTime = 0f

    init {

    }

    override fun initialize() {
        super.initialize()
        val stage = getKoin().get<Stage>()

        stage.addActor(entitiesState)
        stage.addActor(entityInfo)

        entitiesState.y += 128
        entityInfo.y += 96


    }

    override fun processSystem() {
        val stage = getKoin().get<Stage>()
        if((System.currentTimeMillis()*1000 ).toInt() % 5== 0){
            frameTime = Gdx.graphics.deltaTime
        }

        val entities = world.aspectSubscriptionManager.get(Aspect.all())
        entityInfo.setText("FRAME_TIME: ${(frameTime*1000).toInt()}ms | ENTITY_COUNT: ${entities.entities.size()}/${entities.entities.capacity}")

        val walls = world.aspectSubscriptionManager.get(Aspect.all(RectangleCollisionComponent::class.java))
        val pickups = world.aspectSubscriptionManager.get(Aspect.all(PickupComponent::class.java))
        entitiesState.setText("WALLS_&_BOXES: ${walls.entities.size()} | COLLECTABLES: ${pickups.entities.size()}")


        stage.act()
        stage.draw()
    }


}
