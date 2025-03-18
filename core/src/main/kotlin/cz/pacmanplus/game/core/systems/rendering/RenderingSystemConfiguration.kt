package cz.pacmanplus.game.core.systems.rendering

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import cz.pacmanplus.game.PlayerCamera

open class RenderingSystemConfiguration(val shapeRenderer: ShapeRenderer, val spriteBatch: SpriteBatch) {
}

object DefaultRenderingSystemConfiguration : RenderingSystemConfiguration(ShapeRenderer(), SpriteBatch(), )
