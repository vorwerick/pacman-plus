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
import cz.pacmanplus.showEditorScreen
import cz.pacmanplus.showGameScreen
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
        fps = Label("Menu screen", skin)

        val gameButton = TextButton("Start Game", skin)
        val editorButton = TextButton("Level Editor.kt", skin)

        fps.y += 32f
        gameButton.y = 100f
        editorButton.y = 150f

        gameButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                showGameScreen()
                log.debug("Navigating to Game Screen")
            }
        })

        editorButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                showEditorScreen()
                log.debug("Navigating to Editor.kt Screen")
            }
        })

        // Add actors to stage
        stage.addActor(gameButton)
        stage.addActor(editorButton)
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
        Gdx.input.inputProcessor = stage
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
        Gdx.input.inputProcessor = null
        log.debug("Window hidden")
    }
}
