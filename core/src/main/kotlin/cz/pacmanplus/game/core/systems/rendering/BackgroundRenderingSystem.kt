package cz.pacmanplus.game.core.systems.rendering

import com.artemis.Aspect
import com.artemis.BaseSystem
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import cz.pacmanplus.assets.AssetPaths
import cz.pacmanplus.game.GameState
import cz.pacmanplus.game.core.components.graphics.DrawableStateComponent
import cz.pacmanplus.game.core.components.objects.FloorComponent
import cz.pacmanplus.game.core.components.physics.CircleCollisionComponent
import cz.pacmanplus.game.core.components.physics.PositionComponent
import cz.pacmanplus.game.core.systems.physics.collisions.entities
import cz.pacmanplus.game.graphics.findTexture

class BackgroundRenderingSystem(
    val spriteBatch: SpriteBatch,
    val shapeRenderer: ShapeRenderer,
    val gameState: GameState
) :
    BaseSystem() {


    override fun processSystem() {

        val texture = TextureRegion.split(AssetPaths.floor(gameState.map.levelTheme).findTexture(), 32, 32)[0][0]

        spriteBatch.begin()

        val greyscale = 0.4f
        val alpha = 1f
        spriteBatch.setColor(greyscale, greyscale, greyscale, alpha)
        for (x in 0 until gameState.map.tileWidth) {
            for (y in 0 until gameState.map.tileHeight) {

                spriteBatch.draw(texture, (32f * x) + 16, (32f * y) + 16)
            }
        }
        spriteBatch.color = Color.WHITE
        spriteBatch.end()
    }
}
