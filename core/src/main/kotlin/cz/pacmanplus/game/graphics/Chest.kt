package cz.pacmanplus.game.graphics


import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import cz.pacmanplus.assets.AssetPaths


class Chest(locked: Boolean) : Drawable() {

    val chest = TextureRegion.split(AssetPaths.chest().findTexture(), 32, 32)[0][if (locked) 0 else 1]


    override fun draw(spriteBatch: SpriteBatch, x: Float, y: Float) {
        super.draw(spriteBatch, x, y)

        spriteBatch.draw(
            chest, x, y, 64f, 64f
        )

    }

}


