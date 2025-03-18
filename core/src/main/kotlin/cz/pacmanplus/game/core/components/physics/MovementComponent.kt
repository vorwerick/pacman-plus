package cz.pacmanplus.game.core.components.physics

import com.artemis.Component
import com.badlogic.gdx.math.Vector2

class MovementComponent : Component() {

    var lastDir: Vector2 = Vector2.Zero
    var newDir: Vector2 = Vector2.Zero
    var direction: Float = 0f
    var targetXTile: Int = 0
    var targetYTile: Int = 0
    var xTile: Int = 0
    var yTile: Int = 0
    var maxSpeed: Float = 10f

    override fun toString(): String {
        return "MovementComponent"
    }
}
