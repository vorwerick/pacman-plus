package cz.pacmanplus.game.core.components.attributes

import com.artemis.Component

class TimeComponent : Component() {
    private var value: Float = 0f
    private var timer: Float = 0f

    fun setTimer(seconds: Int = 0, milliseconds: Int = 0) {
        this.value = (seconds*1000f) + (milliseconds*1f)
        timer = 0f
    }

    fun tick(delta: Float) {
        timer += delta * 1000
        timer = timer.coerceAtMost(value)
    }

    fun isFinished() = timer >= value

    fun reset() {
        timer = 0f
    }
}
