package cz.pacmanplus.game.core.components.physics

import com.artemis.Component

sealed class LifecycleState {
    data object None : LifecycleState()
    class Alive(var lives: Int) : LifecycleState()
    data object Invulnerable : LifecycleState()
}

class LifecycleComponent : Component() {
    private var state: LifecycleState =
        LifecycleState.None
        get() {
            if (field == LifecycleState.None) {
                throw NotImplementedError()
            }
            return field
        }

    fun isDead(): Boolean = (state is LifecycleState.Alive) && (state as LifecycleState.Alive).lives <= 0
    fun isInvulnerable(): Boolean = state is LifecycleState.Invulnerable
    fun getHitpoints(): Int {
        if (state is LifecycleState.Alive) {
            return (state as LifecycleState.Alive).lives
        }
        return -1
    }

    fun increaseHitpoints() {
        if (state is LifecycleState.Alive) {
            (state as LifecycleState.Alive).lives += 1
        }
    }

    fun decreaseHitpoints() {
        if (state is LifecycleState.Alive) {
            (state as LifecycleState.Alive).lives -= 1
        }
    }

    fun setAlive(hitpoints: Int) {
        state = LifecycleState.Alive(hitpoints)
    }

    fun setInvulnerable() {
        state = LifecycleState.Invulnerable
    }

    fun setDead() {
        state = LifecycleState.Alive(0)
    }


    override fun toString(): String {
        return "state=$state"
    }

    fun isAlive(): Boolean {
        return !isDead()
    }
}
