package cz.pacmanplus.screens

import com.artemis.World
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import cz.pacmanplus.di.gameContext
import cz.pacmanplus.game.GameState
import cz.pacmanplus.game.GraphicsMode
import cz.pacmanplus.game.PlayerCamera
import cz.pacmanplus.game.State
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
        levelCreator.createLevelEmpty(16, 32)

        log.debug("Screen initialized")
    }


    override fun render(delta: Float) {
        handleGfxModeInput()
        handlePauseUnpauseInput()
        handleInitialGame()
        handlePreparedGame()

        worldUpdate()
    }

    private fun handleInitialGame() {
        val gameState = getKoin().get<GameState>()
        if (gameState.state == State.Initial) {
            gameState.state = State.Loading
            //load
            gameState.state = State.Prepared
            startLoadingGame()
        }
    }


    private fun handlePreparedGame() {
        val gameState = getKoin().get<GameState>()
        if (gameState.state == State.Prepared) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                gameState.state = State.Running
                runGame()
            }

        }
    }

    private fun startLoadingGame() {

    }

    private fun runGame() {

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


    private fun worldUpdate() {
        val state = getKoin().get<GameState>()
        if(state.state == State.Running) {
            val world = getKoin().get<World>()

            world.process()

            world.getSystem(PhysicsCircleRenderingSystem::class.java).isEnabled = physicsMode
            world.getSystem(PhysicsRectangleRenderingSystem::class.java).isEnabled = physicsMode
        }

    }

    private fun handlePauseUnpauseInput() {
        val gameState = getKoin().get<GameState>()
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (gameState.state is State.Running) {
                gameState.state = State.Paused
            } else if (gameState.state is State.Paused) {
                gameState.state = State.Running
            }
            log.info("Physics mode was " + if (physicsMode) "enabled" else "disabled")
        }
    }

    private fun handleGfxModeInput() {
        val gameState = getKoin().get<GameState>()
        if (Gdx.input.isKeyJustPressed(Input.Keys.F1)) {
            if (gameState.graphicsMode is GraphicsMode.Normal) {
                gameState.graphicsMode = GraphicsMode.Debug
            } else {
                gameState.graphicsMode = GraphicsMode.Normal
            }
            log.info("Physics mode was " + if (physicsMode) "enabled" else "disabled")
        }
    }
}
