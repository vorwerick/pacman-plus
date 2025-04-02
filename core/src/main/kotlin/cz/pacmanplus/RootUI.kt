package cz.pacmanplus

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.kotcrab.vis.ui.VisUI
import cz.pacmanplus.di.accessories
import cz.pacmanplus.di.screenFactory
import cz.pacmanplus.screens.GameScreen
import cz.pacmanplus.screens.IntroScreen
import cz.pacmanplus.screens.MenuScreen
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.async.KtxAsync
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.LoggerFactory
import java.awt.Menu

class RootUI : KtxGame<KtxScreen>() {

    val log = LoggerFactory.getLogger("RootUI")

    lateinit var skin: Skin
    lateinit var fps: Label
    val stage = Stage(ScreenViewport())



    override fun create() {
        VisUI.load()
        KtxAsync.initiate()


        addScreen(IntroScreen())
        addScreen(MenuScreen())
        addScreen(GameScreen())

        //setScreen<IntroScreen>()
        setScreen<GameScreen>()
        log.debug("Created and ready")

        skin = VisUI.getSkin()
        fps = Label("", skin)
        fps.y += 12
        stage.addActor(fps)
        stage.act()
        stage.draw() //precalculate
    }

    override fun resume() {
        super.resume()
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
    }

    override fun pause() {
        super.pause()
    }


    override fun render() {
        super.render()
        fps.setText(GFXService().getStatusInfo())
        stage.act(Gdx.graphics.deltaTime)
        stage.draw()
    }

    override fun dispose() {
        super.dispose()
        stopKoin()
        log.debug("Disposed")
    }
}


inline fun <reified T : KtxScreen> T.showMenuScreen() {
    getKoin().get<RootUI>().setScreen(MenuScreen::class.java)
}

inline fun <reified T : KtxScreen> T.showIntroScreen() {
    getKoin().get<RootUI>().setScreen(IntroScreen::class.java)
}

inline fun <reified T : KtxScreen> T.showGameScreen() {
    getKoin().get<RootUI>().setScreen(GameScreen::class.java)
}
