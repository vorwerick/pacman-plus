package cz.pacmanplus.game

import cz.pacmanplus.editor.entities.Level
import cz.pacmanplus.editor.entities.Layer

class GameMap(val width: Int, val height: Int) {

    val paused = false
    val layers: MutableList<Layer> = mutableListOf()

    /**
     * Creates a GameMap from a Level object
     */
    constructor(level: Level) : this(level.width, level.height) {
        // Copy layers from the Level object
        level.layers.forEach { layer ->
            layers.add(layer)
        }
    }

    companion object {
        /**
         * Creates a GameMap directly from a Level object
         */
        fun fromLevel(level: Level): GameMap {
            return GameMap(level)
        }
    }
}
