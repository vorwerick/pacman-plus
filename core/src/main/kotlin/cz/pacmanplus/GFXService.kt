package cz.pacmanplus

import com.badlogic.gdx.Gdx

class GFXService {

    var max: Int? = null
    var min: Int? = null

    fun getStatusInfo(): String {

        return "Monitor " + Gdx.graphics.monitor.name + " " + Gdx.graphics.displayMode.width + "x" + Gdx.graphics.displayMode.height + " | " + (if (Gdx.graphics.isFullscreen) "Fullscreen" else "Windowed ${Gdx.graphics.width}x${Gdx.graphics.height} | FPS ${Gdx.graphics.framesPerSecond}" + (if (max != null) " max-$max" else "") + (if (min != null) " min-$min" else ""))
    }

    fun process() {
        if (Gdx.graphics.framesPerSecond > (max ?: Integer.MIN_VALUE)) {
            max = Gdx.graphics.framesPerSecond
        }
        if (Gdx.graphics.framesPerSecond < (min ?: Integer.MAX_VALUE)) {
            min = Gdx.graphics.framesPerSecond
        }
    }
}
