package cz.pacmanplus.game

import com.badlogic.gdx.graphics.OrthographicCamera

class PlayerCamera(val config: PlayerCameraConfiguration) {

    val camera = OrthographicCamera(config.width, config.height)
}
