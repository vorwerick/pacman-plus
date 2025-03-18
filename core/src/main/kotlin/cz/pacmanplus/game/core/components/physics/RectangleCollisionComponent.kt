package cz.pacmanplus.game.core.components.physics

import com.artemis.Component

class RectangleCollisionComponent : Component() {
    var width: Float = 0f
    var height: Float = 0f
    var colliding: Boolean = false
    var solid: Boolean = false
}
