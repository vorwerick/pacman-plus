package cz.pacmanplus.game.core.components.graphics

import com.artemis.Component
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import cz.pacmanplus.game.graphics.Drawable

class DrawableStateComponent() : Component() {

    private var index = 0
    private var drawables = arrayOf<Drawable>()

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

    fun addDrawableState(drawable: Drawable) {
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
