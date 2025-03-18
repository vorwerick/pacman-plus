package cz.pacmanplus.game

import com.badlogic.gdx.graphics.OrthographicCamera

open class PlayerCameraConfiguration(val width: Float, val height: Float) {

}

object DefaultCameraConfiguration : PlayerCameraConfiguration(400f, 400f)
