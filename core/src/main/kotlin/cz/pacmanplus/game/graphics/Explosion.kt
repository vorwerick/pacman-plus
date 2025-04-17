package cz.pacmanplus.game.graphics


import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import cz.pacmanplus.assets.AssetPaths
import cz.pacmanplus.screens.Loader


class Explosion() : Drawable() {

    val texture = TextureRegion(AssetPaths.explosion().findTexture(), 32, 32)


    override fun draw(spriteBatch: SpriteBatch, x: Float, y: Float) {
        super.draw(spriteBatch, x, y)

        spriteBatch.draw(
            texture, x,
            y,
            32f, 32f
        )

    }

}


