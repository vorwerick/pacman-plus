package cz.pacmanplus.game.graphics


import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import org.koin.java.KoinJavaComponent.getKoin


abstract class Drawable() {

    var x = 0f
    var y = 0f
    var visible = true


    open fun draw(spriteBatch: SpriteBatch, x: Float, y: Float) {
        if (!visible) {
            return
        }
    }
}


fun String.findTexture(): Texture {
    return getKoin().get<AssetManager>().get<Texture>(this)
}
