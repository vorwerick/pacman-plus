package cz.pacmanplus.game.core.components.physics

import com.artemis.Component

class UnlockableComponent : Component() {
    var availableKeys: Array<Boolean>? = null
    var keyType = 0

    override fun toString(): String {
        return "mass="
    }
}
