package cz.pacmanplus.screens

import com.artemis.World
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import cz.pacmanplus.game.GameState
import cz.pacmanplus.game.GraphicsMode
import cz.pacmanplus.game.PlayerCamera
import cz.pacmanplus.game.State
import cz.pacmanplus.game.core.systems.rendering.PhysicsCircleRenderingSystem
import cz.pacmanplus.game.core.systems.rendering.PhysicsRectangleRenderingSystem
import cz.pacmanplus.showMenuScreen
import ktx.app.KtxScreen
import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.LoggerFactory


class GameScreen : KtxScreen {

    val log = LoggerFactory.getLogger("GameScreen")




    override fun render(delta: Float) {

        val world = getKoin().get<World>()
        world.process()
    }





    override fun dispose() {
        getKoin().get<World>().dispose()
       // unloadKoinModules(gameContext)
        log.info("Game screen was disposed")
    }

    override fun show() {
        log.debug("Window shown")
    }


    override fun resize(w: Int, h: Int) {
        log.debug("Window resized {}", Vector2(w.toFloat(), h.toFloat()))
        val cam = getKoin().get<PlayerCamera>()
       // cam.camera.viewportWidth = w.toFloat()
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
