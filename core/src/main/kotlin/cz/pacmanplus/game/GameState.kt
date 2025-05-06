package cz.pacmanplus.game

import cz.pacmanplus.assets.LevelTheme
import cz.pacmanplus.editor.entities.Level
import org.koin.java.KoinJavaComponent.getKoin

sealed class State {
    data object SelectLevel : State()

    data class LoadingLevel(val levelId: Int) : State()

    data class EditLevel(val levelId: Int) : State()
    data class Ready(val levelId: Int) : State()
    sealed class InGame : State() {
        data object Prologue : InGame()
        data object Running : InGame()
        data object Paused : InGame()
        data object Epilogue : InGame()
    }

    data object Finished : State()
}

sealed class GraphicsMode {
    data object Normal : GraphicsMode()
    data object Debug : GraphicsMode()
}

class GameState {

    var graphicsMode: GraphicsMode = GraphicsMode.Debug
    var state: State = State.SelectLevel


    var currentLevel: Int? = null
    var levelRecords: MutableList<Level>? = null
    var mapLevel: MapLevel? = null

    fun currentMap(){
        return
    }


}

fun changeGameState(newState: State) {
    getKoin().get<GameState>().state = newState
}

class MapLevel(val tileWidth: Int, val tileHeight: Int, val levelTheme: LevelTheme) {

}
