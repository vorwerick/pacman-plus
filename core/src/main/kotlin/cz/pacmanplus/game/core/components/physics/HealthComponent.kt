package cz.pacmanplus.game.core.components.physics

import com.artemis.Component



class HealthComponent : Component() {
    var lives = 0
    var bleeding = false
    var invulnerability = false
    var invulnerabilityTimer = 0f

    override fun toString(): String {
        return "bleeding=$bleeding"
    }
}
