package cz.pacmanplus.screens

import com.artemis.World
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
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
import cz.pacmanplus.showMenuScreen
import ktx.actors.centerPosition
import ktx.app.KtxScreen
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.LoggerFactory
import kotlin.math.abs


class IntroScreen : KtxScreen {

    val log = LoggerFactory.getLogger("Intro screen")
    lateinit var skin: Skin
    lateinit var fps: Label
    lateinit var corpName: Label
    lateinit var subCorpName: Label

    var corpNameTargetPos = Vector2()
    var subCorpNameTargetPos = Vector2()

    var elapsedTime = 0f  // Čas, který uplynul
    var sizeVal = 0.1f  // Počáteční hodnota
    val duration = 1.6f  // Délka animace v sekundách

    val stage = Stage(ScreenViewport())


    init {


        log.debug("Screen initialized")


        skin = VisUI.getSkin()
        fps = Label("Intro screen", skin)
        corpName = Label("Poopin' Dogs", skin)
        subCorpName = Label("", skin)
        corpName.setFontScale(sizeVal)
        subCorpName.setFontScale(1.5f)

        val button = TextButton("Click me", skin)

        fps.y += 32
        button.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                println("Tlačítko bylo stisknuto!")
            }
        })


        // Přidání tlačítka na stagea

        stage.addActor(corpName)
        stage.addActor(subCorpName)
        stage.act()
        stage.draw() //precalculate

        val cx = Gdx.graphics.width / 2
        val cy = Gdx.graphics.height / 2
        corpName.setPosition(cx - (corpName.glyphLayout.width / 2f), cy + 100f)
        subCorpName.setPosition(cx - (subCorpName.glyphLayout.width / 2f), cy + 40f)

        corpNameTargetPos = Vector2(corpName.x, corpName.y)
        subCorpNameTargetPos = Vector2(subCorpName.x, subCorpName.y)
        corpNameTargetPos


    }


    override fun render(delta: Float) {
        if(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            showMenuScreen()
        }
        elapsedTime += (delta)

        // Zajištění, že čas nebude větší než 1 (když animace skončí)
        val t = Math.min(elapsedTime / duration, 1.6f)
        if(t >= 1.6){

        }

        // Interpolace pomocí bounceIn (hodnota se mění podle času)
        sizeVal = Interpolation.bounceOut.apply(t) * 4f


        //sizeVal += delta
        val cx = Gdx.graphics.width / 2
        val cy = Gdx.graphics.height / 2
        fps.setText("${Gdx.graphics.framesPerSecond}")
        val x = Gdx.graphics.width * 0.5f
        fps.x = x - (fps.glyphLayout.width / 2)
        stage.act(delta)
        stage.draw()
        corpName.setPosition(cx - (corpName.glyphLayout.width / 2f), cy + 100f)
        subCorpName.setPosition(cx - (subCorpName.glyphLayout.width / 2f), cy + 40f)

        //sizeVal = sizeVal.coerceAtMost(3f)
        corpName.setFontScale(abs(sizeVal + 0.01f))
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
