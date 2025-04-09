package cz.pacmanplus.game.core.components.physics

import com.artemis.Component

//todo refactor
sealed class HitPoint {
    data object None : HitPoint()
    data object Dead : HitPoint()
    class Alive(var value: Int) : HitPoint()
    data object Invulnerable : HitPoint()
}

class HitPointsComponent : Component() {
    var state: HitPoint = HitPoint.None
        get() {
            if (field == HitPoint.None) {
                throw NotImplementedError()
            }
            return field
        }

    override fun toString(): String {
        return "state=$state"
    }
}
