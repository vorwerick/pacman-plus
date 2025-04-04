package cz.pacmanplus.screens

import com.artemis.World
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.kotcrab.vis.ui.VisUI
import cz.pacmanplus.di.gameContext
import cz.pacmanplus.game.PlayerCamera
import ktx.app.KtxScreen
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.LoggerFactory


class MenuScreen : KtxScreen {

    val log = LoggerFactory.getLogger("MenuScreen")
    lateinit var skin: Skin
    lateinit var fps: Label

    val stage = Stage(ScreenViewport())


    init {




        log.debug("Screen initialized")

        skin = VisUI.getSkin()
        fps = Label("Intro screen", skin)

        val button = TextButton("Click me", skin)

        fps.y += 32
        button.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                println("Tlačítko bylo stisknuto!")
            }
        })


        // Přidání tlačítka na stagea

        stage.addActor(button)
        stage.addActor(fps)

    }


    override fun render(delta: Float) {

        fps.setText("${Gdx.graphics.framesPerSecond}")

        stage.act(delta)
        stage.draw()
    }

    override fun dispose() {
        stage.dispose()
    }

    override fun show() {
        log.debug("Window shown")
    }


    override fun resize(w: Int, h: Int) {
        log.debug("Window resized {}", Vector2(w.toFloat(), h.toFloat()))
        val cam = getKoin().get<PlayerCamera>()
        cam.camera.viewportWidth = w.toFloat()
        cam.camera.viewportHeight = h.toFloat()
    }

    override fun pause() {
        log.debug("Window paused")
    }

    override fun resume() {
        log.debug("Window resumed")
    }

    override fun hide() {
        log.debug("Window hidden")
    }
}
