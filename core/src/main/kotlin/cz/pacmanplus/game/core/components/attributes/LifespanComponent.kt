package cz.pacmanplus.game.core.components.attributes

import com.artemis.Component

class LifespanComponent : Component() {
    var frames: Int = 0

    fun spent() {
        frames -= 1
        frames = frames.coerceAtLeast(0)
    }

    fun isFinished() = frames <= 0


}
