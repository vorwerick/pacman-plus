package cz.pacmanplus.game.core.components.attributes

import com.artemis.Component

class TeleportComponent : Component() {
    var address: Int = 0
    var target: Int = 0
    var usingEntities: MutableList<Int> = mutableListOf()


}
