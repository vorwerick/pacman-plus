package cz.pacmanplus.game.core.components.physics

import com.artemis.Component

class PassiveAbilitiesComponent  : Component() {
    var pushForce: Float = 0f
    var unlockingValue: Float = 0f

    override fun toString(): String {
        return "pushForce=$pushForce | unlockingValue=$unlockingValue"
    }
}
