package cz.pacmanplus.game.graphics


import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import cz.pacmanplus.assets.AssetPaths
import cz.pacmanplus.utils.SequenceAnimation
import org.koin.java.KoinJavaComponent.getKoin


class Bomb : Drawable() {

    lateinit var animationBody : SequenceAnimation
    lateinit var animationFuse : SequenceAnimation

    init {
         animationBody = SequenceAnimation.create(AssetPaths.bombBody().findTexture(), 32, 32, 30)
         animationFuse = SequenceAnimation.create(AssetPaths.bombFuse().findTexture(), 32, 32, 15)

    }


    override fun draw(spriteBatch: SpriteBatch, x: Float, y: Float) {
        super.draw(spriteBatch, x, y)

        val body = animationBody.currentFrame()
        spriteBatch.draw(
            body, x + animationBody.offsetX - 24,
            y + animationBody.offsetY-4,
            80f, 80f
        )
        animationBody.animate()

        val fuse = animationFuse.currentFrame()
        spriteBatch.draw(
            fuse, x + animationFuse.offsetX-24,
            y + animationFuse.offsetY-4,
            80f, 80f
        )
        animationFuse.animate()
    }

}


