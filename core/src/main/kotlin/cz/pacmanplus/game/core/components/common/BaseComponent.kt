package cz.pacmanplus.game.core.components.common

import com.artemis.Component

class BaseComponent() : Component() {

    var name: String = ""
    var alive: Boolean = true

    override fun toString(): String {
        return "[$name] Is alive: $alive"
    }
}
