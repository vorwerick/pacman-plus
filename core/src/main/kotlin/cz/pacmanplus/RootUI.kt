package cz.pacmanplus

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.kotcrab.vis.ui.VisUI
import cz.pacmanplus.di.accessories
import cz.pacmanplus.di.screenFactory
import cz.pacmanplus.screens.EditorScreen
import cz.pacmanplus.screens.GameScreen
import cz.pacmanplus.screens.IntroScreen
import cz.pacmanplus.screens.LoadingScreen
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

    companion object {
        lateinit var font: BitmapFont
    }

    override fun create() {
        VisUI.load()
        KtxAsync.initiate()
        val generator = FreeTypeFontGenerator(Gdx.files.internal("ui/public-pixel.ttf"));
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        font = generator.generateFont(parameter);


        log.debug("Created and ready")

        skin = VisUI.getSkin()
        fps = Label("", skin)
        fps.style.font = RootUI.font

        fps.y += 12
        stage.addActor(fps)
        stage.act()
        stage.draw() //precalculate

        showIntroScreen()
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
      //  fps.setText(GFXService().getStatusInfo())
        stage.act(Gdx.graphics.deltaTime)
        stage.draw()
    }

    override fun dispose() {
        super.dispose()
        stopKoin()
        log.debug("Disposed")
    }

    fun disposeCurrentScreen() {
        currentScreen?.dispose()
    }
}


inline fun <reified T : KtxScreen> T.showMenuScreen() {
    getKoin().get<RootUI>().disposeCurrentScreen()
    getKoin().get<RootUI>().addScreen(getKoin().get<MenuScreen>())
    getKoin().get<RootUI>().setScreen(MenuScreen::class.java)
}

fun KtxGame<KtxScreen>.showIntroScreen() {
    getKoin().get<RootUI>().disposeCurrentScreen()
    getKoin().get<RootUI>().addScreen(getKoin().get<IntroScreen>())
    getKoin().get<RootUI>().setScreen(IntroScreen::class.java)
}

inline fun <reified T : KtxScreen> T.showLoadingScreen() {
    getKoin().get<RootUI>().disposeCurrentScreen()
    getKoin().get<RootUI>().addScreen(getKoin().get<LoadingScreen>())
    getKoin().get<RootUI>().setScreen(LoadingScreen::class.java)
}

inline fun <reified T : KtxScreen> T.showGameScreen() {
    getKoin().get<RootUI>().disposeCurrentScreen()
    getKoin().get<RootUI>().addScreen(getKoin().get<GameScreen>())
    getKoin().get<RootUI>().setScreen(GameScreen::class.java)
}

inline fun <reified T : KtxScreen> T.showEditorScreen() {
    // Load the editor context module to make Editor available
    org.koin.core.context.loadKoinModules(cz.pacmanplus.di.editorContext)
    getKoin().get<RootUI>().addScreen(getKoin().get<EditorScreen>())
    getKoin().get<RootUI>().setScreen(EditorScreen::class.java)
}
