package cz.pacmanplus.utils

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion


class SequenceAnimation(name: String, val frames: List<TextureRegion>, var index: Int = 0, var speed: Int) {

    companion object {
        fun create(texture: Texture, width: Int, height: Int, speed: Int): SequenceAnimation {
            val regions = TextureRegion.split(texture, width, height)
            return SequenceAnimation("name", regions.flatten(), speed = speed)
        }
    }

    var stateTime = 0f

    fun animate(onFinish: (() -> Unit)? = null) {
        stateTime += 1
        if (stateTime >= 1000 / speed) {
            stateTime = 0f
            index++
            if (index >= frames.size) {
                onFinish?.invoke()
                index = 0
            }
        }
    }

    fun currentFrame(): TextureRegion {
        return frames[index]
    }

    var offsetX: Float = 0f
    var offsetY: Float = 0f
    var scaleX: Float = 1f
    var scaleY: Float = 1f

}
