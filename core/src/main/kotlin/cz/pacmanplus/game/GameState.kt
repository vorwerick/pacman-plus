package cz.pacmanplus.game

import cz.pacmanplus.assets.LevelTheme

sealed class State {
    data object Initial : State()
    data object Loading : State()
    data object Prepared : State()
    data object Running : State()
    data object Paused : State()
    data object Finished : State()
}

sealed class GraphicsMode {
    data object Normal : GraphicsMode()
    data object Debug : GraphicsMode()
}

class GameState {

    var graphicsMode: GraphicsMode = GraphicsMode.Debug
    var state: State = State.Initial



    var map: MapLevel = MapLevel(32, 32, LevelTheme.Egypt)
}

class MapLevel(val tileWidth: Int, val tileHeight: Int, val levelTheme: LevelTheme) {

}
