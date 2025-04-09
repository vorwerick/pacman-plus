package cz.pacmanplus.game.core.components.physics

import com.artemis.Component

enum class DamageLifespan {
    Destroy, Pierce
}

class DamageComponent : Component() {
    var persistent = true // if true then stay alive after dealing first damage

    override fun toString(): String {
        return "amount=ALWAYS_1_GIVES_LIFE_NOT_HP"
    }
}
