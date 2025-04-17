package cz.pacmanplus.game.graphics


import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import cz.pacmanplus.assets.AssetPaths
import cz.pacmanplus.screens.Loader


class ScorePoint() : Drawable() {

    val texture = TextureRegion(AssetPaths.flareEffect().findTexture(), 0, 0, 16, 16)


    override fun draw(spriteBatch: SpriteBatch, x: Float, y: Float) {
        super.draw(spriteBatch, x, y)

        spriteBatch.color = Color.YELLOW
        spriteBatch.draw(
            texture, x-12,
            y-12,
            24f, 24f
        )
        spriteBatch.color = Color.WHITE

    }

}


