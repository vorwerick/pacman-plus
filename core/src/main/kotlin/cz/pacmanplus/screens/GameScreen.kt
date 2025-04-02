package cz.pacmanplus.screens

import com.artemis.World
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import cz.pacmanplus.di.gameContext
import cz.pacmanplus.game.PlayerCamera
import cz.pacmanplus.game.core.entity.LevelCreator
import cz.pacmanplus.game.core.systems.rendering.PhysicsCircleRenderingSystem
import cz.pacmanplus.game.core.systems.rendering.PhysicsRectangleRenderingSystem
import ktx.app.KtxScreen
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.LoggerFactory


class GameScreen : KtxScreen {

    val log = LoggerFactory.getLogger("GameScreen")

    var physicsMode = true
    var paused = false



    init {
        loadKoinModules(gameContext)

        val levelCreator: LevelCreator = getKoin().get()
        levelCreator.createLevelDebug(16, 32)




        log.debug("Screen initialized")



    }



    override fun render(delta: Float) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.F1)) {
            if(physicsMode){
                physicsMode = false
            } else {
                physicsMode = true
            }
            log.info("Physics mode was " + if(physicsMode) "enabled" else "disabled")
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if(paused){
                paused = false
            } else {
                paused = true
            }
            log.info("Game was " + if(paused) "paused" else "unpaused")
        }


        val world = getKoin().get<World>()
        world.process()

        world.getSystem(PhysicsCircleRenderingSystem::class.java).isEnabled = physicsMode
        world.getSystem(PhysicsRectangleRenderingSystem::class.java).isEnabled = physicsMode

    }

    override fun dispose() {
        getKoin().get<World>().dispose()
        unloadKoinModules(gameContext)
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
