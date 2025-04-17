package cz.pacmanplus.game.core.plugins

import com.artemis.ArtemisPlugin
import com.artemis.WorldConfigurationBuilder
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import cz.pacmanplus.game.GameState
import cz.pacmanplus.game.core.systems.physics.movement.BoxMovementSystem
import cz.pacmanplus.game.core.systems.physics.movement.MovementSystem
import cz.pacmanplus.game.core.systems.physics.movement.StoneMovementSystem
import cz.pacmanplus.game.core.systems.physics.movement.UnlockingSystem
import cz.pacmanplus.game.core.systems.rendering.*
import org.koin.java.KoinJavaComponent.getKoin

class RenderingPlugin : ArtemisPlugin {
    override fun setup(p0: WorldConfigurationBuilder?) {
        val shapeRenderer = getKoin().get<ShapeRenderer>()
        val spriteBatch = getKoin().get<SpriteBatch>()
        p0?.with(
            BackgroundRenderingSystem(
                spriteBatch, shapeRenderer,
                gameState = getKoin().get<GameState>()
            )
        )
        p0?.with(FloorRenderingSystem(spriteBatch, shapeRenderer))
        //  p0?.with(CharacterRenderingSystem(configuration = DefaultRenderingSystemConfiguration))
        p0?.with(WallRenderingSystem(spriteBatch, shapeRenderer))

        p0?.with(GUIRenderingSystem(spriteBatch, shapeRenderer))
    }
}
