package cz.pacmanplus.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera

class PlayerCamera(val config: PlayerCameraConfiguration) {

    val height = 480f
    val width = height * (Gdx.graphics.width.toFloat() / Gdx.graphics.height)

    val camera = OrthographicCamera().apply {
        zoom = 1f
        setToOrtho(false, width, height)
    }
}
