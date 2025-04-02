package cz.pacmanplus.game.core.systems.rendering

import com.artemis.Aspect
import com.artemis.BaseSystem
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.kotcrab.vis.ui.VisUI
import cz.pacmanplus.game.PlayerCamera
import cz.pacmanplus.game.core.components.control.PlayerInputComponent
import cz.pacmanplus.game.core.components.physics.*
import cz.pacmanplus.game.core.systems.findPlayer
import org.koin.java.KoinJavaComponent.getKoin

class PhysicsGuiSystem() : BaseSystem() {

    private val skin = VisUI.getSkin()
    val playersPosition = Label("", skin)
    val playersDirection = Label("", skin)
    val playersInput = Label("", skin)
    val pushPhysics = Label("", skin)

    init {

    }

    override fun initialize() {
        super.initialize()
        val stage = getKoin().get<Stage>()

        stage.addActor(playersPosition)
        stage.addActor(playersDirection)
        stage.addActor(playersInput)
        stage.addActor(pushPhysics)

        playersPosition.y += 64
        playersDirection.y += 48
        playersInput.y += 32
        pushPhysics.y += 80


    }

    override fun processSystem() {
        val stage = getKoin().get<Stage>()

        val player = world.findPlayer()
        player?.let {
            val positionComponent = it.getComponent(PositionComponent::class.java)
            val movementComponent = it.getComponent(MovementComponent::class.java)
            val playerInput = it.getComponent(PlayerInputComponent::class.java)
            val pushComponent = it.getComponent(PushComponent::class.java)
            pushPhysics.setText(pushComponent.toString())
            playersPosition.setText(positionComponent.toString())
            playersDirection.setText(movementComponent.toString())
            playersInput.setText(playerInput.toString())
        }


        stage.act()
        stage.draw()
    }


}
