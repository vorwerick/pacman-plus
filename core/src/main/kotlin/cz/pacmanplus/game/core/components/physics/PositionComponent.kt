package cz.pacmanplus.game.core.components.physics

import com.artemis.Component

class PositionComponent : Component() {
    var x = 0f
    var y = 0f

    override fun toString(): String {
        return "POSITION x=$x, y=$y"
    }
}
