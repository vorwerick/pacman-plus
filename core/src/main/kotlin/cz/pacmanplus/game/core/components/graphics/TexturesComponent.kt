package cz.pacmanplus.game.core.components.graphics

import com.artemis.Component
import com.badlogic.gdx.graphics.g2d.TextureRegion

class TexturesComponent: Component() {

    var textures: List<TextureRegion> = listOf()
    var index: Int = 0

}
