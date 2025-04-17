package cz.pacmanplus.game.graphics


import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import cz.pacmanplus.assets.AssetPaths
import cz.pacmanplus.screens.Loader
import cz.pacmanplus.utils.SequenceAnimation


class Player(val direction: Int) : Drawable() {

    val left = TextureRegion(AssetPaths.player().findTexture(), 64, 64)
    val right = TextureRegion(AssetPaths.player().findTexture(), 64, 64)
    val up = TextureRegion(AssetPaths.player().findTexture(), 64, 64)
    val down = TextureRegion(AssetPaths.player().findTexture(), 64, 64)

    var current: TextureRegion? = null


    override fun draw(spriteBatch: SpriteBatch, x: Float, y: Float) {
        super.draw(spriteBatch, x, y)

        when (direction) {
            0 -> current = left
            1 -> current = right
            2 -> current = up
            3 -> current = down
        }


        spriteBatch.draw(
            current, x - 24-4,
            y-16-4,
            64f, 64f
        )

    }

}


