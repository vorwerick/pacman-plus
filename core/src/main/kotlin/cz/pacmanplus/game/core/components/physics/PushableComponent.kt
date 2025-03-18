package cz.pacmanplus.game.core.components.physics

import com.artemis.Component
import com.badlogic.gdx.math.Vector2

class PushableComponent : Component() {
    var mass = 0
    var pushPotential = 0f
    var pushDirection = Vector2.Zero

    override fun toString(): String {
        return "mass=$mass"
    }
}
