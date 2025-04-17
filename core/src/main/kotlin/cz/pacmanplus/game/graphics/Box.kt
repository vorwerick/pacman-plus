package cz.pacmanplus.game.graphics


import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import cz.pacmanplus.assets.AssetPaths


class Box() : Drawable() {

    val texture = TextureRegion.split(AssetPaths.box().findTexture(), 32, 32)[0][0]


    override fun draw(spriteBatch: SpriteBatch, x: Float, y: Float) {
        super.draw(spriteBatch, x, y)

        spriteBatch.draw(
            texture, x-4, y, 38f, 40f
        )

    }

}


