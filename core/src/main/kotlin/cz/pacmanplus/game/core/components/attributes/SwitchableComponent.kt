package cz.pacmanplus.game.core.components.attributes

import com.artemis.Component

class SwitchableComponent : Component() {
    var enabled: Boolean = false
    var usingEntities: MutableList<Int> = mutableListOf()
    var groupId: Int = 0


}
