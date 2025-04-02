package cz.pacmanplus.game.core.components.physics

import com.artemis.Component

sealed class Item{
    data object Empty : Item()
    data object Score : Item()
    data object Life : Item()
    data object Sprint : Item()
}

class LootComponent : Component() {
    var loot: Item = Item.Empty

    override fun toString(): String {
        return "loot=$loot"
    }
}
