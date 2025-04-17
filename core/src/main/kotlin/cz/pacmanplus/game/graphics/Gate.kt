package cz.pacmanplus.game.graphics


import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import cz.pacmanplus.assets.AssetPaths
import cz.pacmanplus.screens.Loader


class Gate(val opened: Boolean) : Drawable() {

    val textureClosed = TextureRegion(AssetPaths.gate().findTexture(), 0, 0, 32, 32)
    val textureOpened = TextureRegion(AssetPaths.gate().findTexture(), 32, 0, 32, 32)


    override fun draw(spriteBatch: SpriteBatch, x: Float, y: Float) {
        super.draw(spriteBatch, x, y)

        if (opened) {
            spriteBatch.draw(
                textureOpened, x,
                y,
                32f, 38f
            )
        } else {
            spriteBatch.draw(
                textureClosed, x,
                y,
                32f, 38f
            )
        }


    }

}


