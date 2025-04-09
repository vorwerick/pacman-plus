package cz.pacmanplus.game.core.components.physics

import com.artemis.Component

//todo refactor
sealed class HitPoint {
    data object None : cz.pacmanplus.game.core.components.physics.HitPoint()
    data object Dead : cz.pacmanplus.game.core.components.physics.HitPoint()
    class Alive(var value: Int) : cz.pacmanplus.game.core.components.physics.HitPoint()
    data object Invulnerable : cz.pacmanplus.game.core.components.physics.HitPoint()
}

class HitPointsComponent : Component() {
    var state: cz.pacmanplus.game.core.components.physics.HitPoint =
        cz.pacmanplus.game.core.components.physics.HitPoint.None
        get() {
            if (field == cz.pacmanplus.game.core.components.physics.HitPoint.None) {
                throw NotImplementedError()
            }
            return field
        }

    override fun toString(): String {
        return "state=$state"
    }
}
