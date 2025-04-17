package cz.pacmanplus.game.graphics


import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import cz.pacmanplus.assets.AssetPaths
import cz.pacmanplus.screens.Loader
import cz.pacmanplus.utils.SequenceAnimation


class ScorePointAnimation(val onAnimationEnds: () -> Unit) : Drawable() {

    val animation = SequenceAnimation.create(AssetPaths.flareEffect().findTexture(), 16,16, 30)


    override fun draw(spriteBatch: SpriteBatch, x: Float, y: Float) {
        super.draw(spriteBatch, x, y)

        spriteBatch.color = Color.YELLOW
        spriteBatch.draw(
            animation.currentFrame(), x-12,
            y-12,
            24f, 24f
        )
        spriteBatch.color = Color.WHITE
        animation.animate{
            onAnimationEnds()
        }

    }

}


