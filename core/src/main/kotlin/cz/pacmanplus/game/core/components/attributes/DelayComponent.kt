package cz.pacmanplus.game.core.components.attributes

import com.artemis.Component

class DelayComponent : Component() {
    var delay: Float = 0f
    var timer: Float = 0f

    fun tick(delta: Float) {
        timer += delta * 1000
        timer = timer.coerceAtMost(delay)
    }

    fun isFinished() = timer >= delay

    fun reset() {
        timer = 0f
    }
}
