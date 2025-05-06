package cz.pacmanplus.game.core.systems

import com.artemis.BaseSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import cz.pacmanplus.game.GameState
import cz.pacmanplus.game.LevelLibrary
import cz.pacmanplus.game.State
import cz.pacmanplus.game.changeGameState
import cz.pacmanplus.game.core.entity.EntityFactory
import org.koin.java.KoinJavaComponent.getKoin

class GameStateSystem : BaseSystem() {

    fun changeGameState(state: State) {
        cz.pacmanplus.game.changeGameState(newState = state)
    }

    override fun processSystem() {
        val game = getKoin().get<GameState>()

        when (game.state) {
            State.SelectLevel -> {

            }

            is State.Ready -> processReadyState((game.state as State.Ready).levelId)

            is State.InGame -> processInGameState()
            State.Finished -> {

            }

            is State.LoadingLevel -> processLoadingLevelState((game.state as State.LoadingLevel).levelId)
            is State.EditLevel -> processEditLevel((game.state as State.EditLevel).levelId)
        }

    }

    private fun processEditLevel(levelId: Int) {

    }

    private fun processLoadingLevelState(levelId: Int) {
        val library = getKoin().get<LevelLibrary>()
        EntityFactory().createLevelDebug(32, 32)

        changeGameState(State.Ready(levelId))
    }

    private fun processInGameState() {
        val gameState = getKoin().get<GameState>()
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) { //on escape
            if (gameState.state is State.InGame.Running) {
                gameState.state = State.InGame.Paused
            } else if (gameState.state is State.InGame.Paused) {
                gameState.state = State.InGame.Running
            }
        }

    }

    private fun processReadyState(levelId: Int) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            changeGameState(State.InGame.Running)
        }
    }
}
