package cz.pacmanplus.game.graphics


import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import cz.pacmanplus.assets.AssetPaths
import cz.pacmanplus.assets.LevelTheme
import cz.pacmanplus.screens.Loader


class Wall(hitpoints: Int) : Drawable() {

    private val tileWidth = 32
    private val tileHeight = 38

    val texture = TextureRegion.split(AssetPaths.wall(LevelTheme.Egypt).findTexture(), tileWidth, tileHeight)[1][0]


    override fun draw(spriteBatch: SpriteBatch, x: Float, y: Float) {
        super.draw(spriteBatch, x, y)

        spriteBatch.draw(
            texture, x, y, tileWidth * 1f, tileHeight * 1f
        )


    }

}


