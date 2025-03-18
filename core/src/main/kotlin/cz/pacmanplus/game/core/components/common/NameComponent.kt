package cz.pacmanplus.game.core.components.common

import com.artemis.Component

class NameComponent() : Component() {

    var name: String = ""

    override fun toString(): String {
        return "[$name]"
    }
}
