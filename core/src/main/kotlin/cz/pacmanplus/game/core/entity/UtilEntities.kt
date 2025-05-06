package cz.pacmanplus.game.core.entity

import cz.pacmanplus.game.core.components.physics.PositionComponent

object UtilEntities {


    fun cameraMan() = newEntity("CameraMan", PositionComponent().apply { x = 0f; y = 0f })

}
