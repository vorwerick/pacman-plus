package cz.pacmanplus.game.graphics


import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import cz.pacmanplus.assets.AssetPaths
import cz.pacmanplus.screens.Loader


class Button(val enabled: Boolean) : Drawable() {

    val textureOn = TextureRegion(AssetPaths.button().findTexture(), 0, 0, 32, 32)
    val textureOff = TextureRegion(AssetPaths.button().findTexture(), 32, 0, 32, 32)


    override fun draw(spriteBatch: SpriteBatch, x: Float, y: Float) {
        super.draw(spriteBatch, x, y)

        var texture = textureOff
        if (enabled) {
            texture = textureOn
        }
        spriteBatch.draw(
            texture, x,
            y,
            64f, 64f
        )

    }

}


