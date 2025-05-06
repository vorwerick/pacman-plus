package cz.pacmanplus.screens

import com.artemis.World
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.kotcrab.vis.ui.VisUI
import cz.pacmanplus.RootUI
import cz.pacmanplus.di.gameContext
import cz.pacmanplus.game.PlayerCamera
import cz.pacmanplus.screens.ui.ShadowLabel
import cz.pacmanplus.showEditorScreen
import cz.pacmanplus.showGameScreen
import cz.pacmanplus.utils.centerStage
import ktx.app.KtxScreen
import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.LoggerFactory
import kotlin.system.exitProcess


class MenuScreen : KtxScreen {

    val log = LoggerFactory.getLogger("MenuScreen")
    lateinit var skin: Skin


    val stage = Stage(ScreenViewport())

    val menuItems = listOf<ShadowLabel>(
        ShadowLabel("CAMPAIGN", titleColor = Color.GRAY),
        ShadowLabel("DEBUG MODE", titleColor = Color.GRAY),
        ShadowLabel("SETTINGS", titleColor = Color.GRAY),
        ShadowLabel("CREDITS", titleColor = Color.GRAY),
        ShadowLabel("QUIT", titleColor = Color.GRAY)
    )

    val bottomItems = listOf<ShadowLabel>(
        ShadowLabel("(J)OIN DISCORD", titleColor = Color(0.447f, 0.537f, 0.855f, 1f))
    )

    val background = Image(Texture("ui/egypt.png")).apply {
        scaleX = 0.65f
        scaleY = 0.7f
    }
    var menuIndex = 0

    init {


        log.debug("Screen initialized")

        skin = VisUI.getSkin()



        stage.addActor(background)

        menuItems.forEach {
            it.addToStage(stage)
        }
        bottomItems.forEach {
            it.addToStage(stage)
        }


    }


    override fun render(delta: Float) {



        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (menuIndex == 0) {
                showGameScreen()
            }
            if (menuIndex == 1) {
                showGameScreen()
            }
            if (menuIndex == 4) {
                exitProcess(0)
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            menuIndex += 1
            if(menuIndex >= menuItems.size) {
                menuIndex = 0
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            menuIndex -= 1
            if (menuIndex < 0) {
                menuIndex = menuItems.size - 1
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.J)) {
            Gdx.net.openURI("https://discord.gg/kaHXpK4E")
        }

        menuItems.forEachIndexed { index, shadowLabel ->
            val center = stage.centerStage(shadowLabel.getTitle().glyphLayout)
            shadowLabel.setPosition(Vector2(center.x, center.y - (index * 48)))
            if (index == menuIndex) {
                shadowLabel.changeColor(Color.WHITE)
            } else {
                shadowLabel.changeColor(Color.GRAY)
            }
        }

        bottomItems.forEachIndexed { index, shadowLabel ->
            shadowLabel.setPosition(Vector2(32f, 32f))

        }

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
