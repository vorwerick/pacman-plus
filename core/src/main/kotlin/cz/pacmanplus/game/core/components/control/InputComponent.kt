package cz.pacmanplus.game.core.components.control

import com.artemis.Component
import com.badlogic.gdx.math.Vector2

class InputComponent : Component() {
    var dir: Vector2 = Vector2()
    var horizontal : Boolean = false
    var vertical : Boolean = false
    var lastDirBeforeBoth = 0

    var useBomb = false


    override fun toString(): String {
        return "USE BOMB: $useBomb | INPUT_DIR: $dir "
    }
}
