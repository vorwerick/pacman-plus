package cz.pacmanplus.game.core.systems.rendering

import com.artemis.BaseSystem
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.kotcrab.vis.ui.VisUI
import cz.pacmanplus.game.core.components.control.InputComponent
import cz.pacmanplus.game.core.components.physics.*
import cz.pacmanplus.game.core.systems.findPlayer
import cz.pacmanplus.game.core.systems.grid.WallGridSystem
import org.koin.java.KoinJavaComponent.getKoin

class PhysicsGuiSystem() : BaseSystem() {

    private val skin = VisUI.getSkin()
    val playersPosition = Label("", skin)
    val playersDirection = Label("", skin)
    val playersInput = Label("", skin)
    val pushPhysics = Label("", skin)
    val map = Label("", skin)

    init {

    }

    override fun initialize() {
        super.initialize()
        val stage = getKoin().get<Stage>()

        stage.addActor(playersPosition)
        stage.addActor(playersDirection)
        stage.addActor(playersInput)
        stage.addActor(pushPhysics)
        stage.addActor(map)

        playersPosition.y += 64
        playersDirection.y += 48
        playersInput.y += 32
        pushPhysics.y += 80

        map.y += 400


    }

    override fun processSystem() {
        val stage = getKoin().get<Stage>()

        val player = world.findPlayer()
        player?.let {
            val positionComponent = it.getComponent(PositionComponent::class.java)
            val movementComponent = it.getComponent(MovementComponent::class.java)
            val playerInput = it.getComponent(InputComponent::class.java)
            val passiveAbilitiesComponent = it.getComponent(PassiveAbilitiesComponent::class.java)
            pushPhysics.setText(passiveAbilitiesComponent.toString())
            playersPosition.setText(positionComponent.toString())
            playersDirection.setText(movementComponent.toString())
            playersInput.setText(playerInput.toString())

            val grid = world.getSystem(WallGridSystem::class.java).wallGrid
            val sb = StringBuilder()
            var yy = 0
            grid.forEachGrid { x, y, value ->
                if(x > yy){
                    yy ++
                    sb.append("\n")
                }
                if (value != null) {
                    sb.append("X")
                } else {
                    sb.append("0")
                }

            }
            map.setText(sb.toString())
        }


        stage.act()
        stage.draw()
    }


}
