package cz.pacmanplus.game

import com.badlogic.gdx.Gdx


open class PlayerCameraConfiguration(val width: Float, val height: Float) {

}

object DefaultCameraConfiguration : PlayerCameraConfiguration(Gdx.graphics.width*1f, Gdx.graphics.height*1f - 240f)
