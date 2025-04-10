package cz.pacmanplus.game.core.components.control

import com.artemis.Component

class ComputerPathComponent : Component() {

    var path: MutableList<Pair<Int, Int>>? = null


    override fun toString(): String {
        return "AICOMPONENT"
    }
}
