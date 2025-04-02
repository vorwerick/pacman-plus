package cz.pacmanplus.game.core.components.physics

import com.artemis.Component

class PushComponent  : Component() {
    var pushAmount: Float = 0f
    var pushForce: Float = 0f

    override fun toString(): String {
        return "amount=$pushAmount"
    }
}
