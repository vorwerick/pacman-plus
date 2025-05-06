package cz.pacmanplus.game

import cz.pacmanplus.editor.entities.Level

class LevelLibrary(val levels: MutableList<Level> = mutableListOf<Level>()) {
    fun get(currentLevel: Int): Level {
        return levels[currentLevel]
    }

    init {
        levels.add(Level(8, 8, 32, 0, 0))
        levels.add(Level(8, 8, 32, 0, 0))
    }

}
