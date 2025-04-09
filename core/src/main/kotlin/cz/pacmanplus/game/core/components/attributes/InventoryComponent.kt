package cz.pacmanplus.game.core.components.attributes

import com.artemis.Component

class InventoryComponent : Component() {

    var score: Int = 0
    var slot: Int? = null
    var keyring: Array<Boolean> = Array(4) { false }
}
