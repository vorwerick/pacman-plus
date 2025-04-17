package cz.pacmanplus.screens

import com.artemis.World
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.kotcrab.vis.ui.VisUI
import cz.pacmanplus.assets.AssetPaths
import cz.pacmanplus.assets.getAll
import cz.pacmanplus.showGameScreen
import cz.pacmanplus.showMenuScreen
import ktx.actors.centerPosition
import ktx.app.KtxScreen
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.LoggerFactory
import kotlin.math.abs
import kotlin.math.roundToInt


class LoadingScreen : KtxScreen {

    val log = LoggerFactory.getLogger("Loading screen")
    lateinit var skin: Skin
    lateinit var corpName: Label

    var corpNameTargetPos = Vector2()
    var subCorpNameTargetPos = Vector2()

    val assetManager = getKoin().get<AssetManager>()


    val stage = Stage(ScreenViewport())


    init {


        log.debug("Screen initialized")


        skin = VisUI.getSkin()
        corpName = Label("", skin)

        val button = TextButton("Click me", skin)

        button.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                println("Tlačítko bylo stisknuto!")
            }
        })


        // Přidání tlačítka na stagea

        stage.addActor(corpName)
        stage.act()
        stage.draw() //precalculate

        val cx = Gdx.graphics.width / 2
        val cy = Gdx.graphics.height / 2
        corpName.setPosition(cx - (corpName.glyphLayout.width / 2f), cy + 100f)

        corpNameTargetPos = Vector2(corpName.x, corpName.y)


        assetManager.getAll().forEach {
            assetManager.load(it, Texture::class.java)
        }
    }


    override fun render(delta: Float) {


        if (assetManager.update()) {
            // Hotovo
            if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
                showGameScreen()
            }
        } else {
            val progress = assetManager.progress // 0..1
            corpName.setText("Loading " + (assetManager.loadedAssets*1f) /(assetManager.getAll().size  - 1) * 100f)
            println()
        }

        //sizeVal += delta
        val cx = Gdx.graphics.width / 2
        val cy = Gdx.graphics.height / 2
        val x = Gdx.graphics.width * 0.5f
        stage.act(delta)
        stage.draw()
        corpName.setPosition(cx - (corpName.glyphLayout.width / 2f), cy + 100f)

        //sizeVal = sizeVal.coerceAtMost(3f)
    }

    override fun dispose() {
        stage.dispose()
    }

    override fun show() {
        log.debug("Window shown")
    }


    override fun resize(w: Int, h: Int) {
        log.debug("Window resized {}", Vector2(w.toFloat(), h.toFloat()))
        //val cam = getKoin().get<PlayerCamera>()
        //  cam.camera.viewportWidth = w.toFloat()
        // cam.camera.viewportHeight = h.toFloat()
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
