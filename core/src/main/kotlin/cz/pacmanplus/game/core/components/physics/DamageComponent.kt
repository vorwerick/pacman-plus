package cz.pacmanplus.game.core.components.physics

import com.artemis.Component

class DamageComponent : Component() {
    var damage = 1

    override fun toString(): String {
        return "damagew=$damage"
    }
}
