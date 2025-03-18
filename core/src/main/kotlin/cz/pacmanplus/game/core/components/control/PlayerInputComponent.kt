package cz.pacmanplus.game.core.components.control

import com.artemis.Component
import com.badlogic.gdx.math.Vector2

class PlayerInputComponent : Component() {
    var dir: Vector2 = Vector2()
    var horizontal : Boolean = false
    var vertical : Boolean = false
    var lastDirBeforeBoth = 0
}
