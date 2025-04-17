package cz.pacmanplus.game.core.components.graphics

import com.artemis.Component
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import cz.pacmanplus.game.graphics.Drawable
import kotlin.random.Random

class DrawableEffectComponent() : Component() {

    private var index = 0
    private var drawables = arrayOf<Drawable>()
    private var nextEffect = randomTime()

    private fun randomTime(): Float {
        return Random.nextInt(10000, 30000) * 1f
    }

    fun tick(delta: Float) {
        nextEffect -= delta * 1000
        if (nextEffect <= 0f) {
            nextEffect = randomTime()
            changeState()
        }
    }

    // iterates index in texture regions
    fun changeState(state: Int? = null) {

        if (state != null) {
            if (state < 0 || state >= drawables.size) {
                throw IndexOutOfBoundsException("State must be between 0 and ${drawables.size - 1}")
            }
            index = state
        } else {
            index++
            if (index >= drawables.size) index = 0
        }
    }

    fun addDrawableEffect(drawable: Drawable) {
        drawables = Array(drawables.size + 1) { a ->
            if (a < drawables.size) {
                drawables[a]
            } else {
                drawable
            }
        }
    }

    fun draw(spriteBatch: SpriteBatch, x: Float, y: Float) {
        drawables[index].draw(spriteBatch, x, y)
    }
}
