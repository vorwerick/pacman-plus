package cz.pacmanplus.game.graphics


import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import cz.pacmanplus.assets.AssetPaths
import cz.pacmanplus.screens.Loader


class Lever(val enabled: Boolean) : Drawable() {

    val leverOn = TextureRegion(AssetPaths.lever().findTexture(), 32, 32)
    val leverOff = TextureRegion(AssetPaths.lever().findTexture(), 32, 32).apply { flip(true, false) }


    override fun draw(spriteBatch: SpriteBatch, x: Float, y: Float) {
        super.draw(spriteBatch, x, y)


        if(enabled){
            spriteBatch.draw(
                leverOn, x-8,
                y-4,
                32f, 32f
            )
        } else {
            spriteBatch.draw(
                leverOff, x-8,
                y-4,
                32f, 32f
            )
        }


    }

}


