package cz.pacmanplus.game.core.plugins

import com.artemis.ArtemisPlugin
import com.artemis.WorldConfigurationBuilder
import cz.pacmanplus.game.GameState
import cz.pacmanplus.game.core.systems.physics.movement.BoxMovementSystem
import cz.pacmanplus.game.core.systems.physics.movement.MovementSystem
import cz.pacmanplus.game.core.systems.physics.movement.StoneMovementSystem
import cz.pacmanplus.game.core.systems.physics.movement.UnlockingSystem
import cz.pacmanplus.game.core.systems.rendering.*
import org.koin.java.KoinJavaComponent.getKoin

class RenderingPlugin : ArtemisPlugin {
    override fun setup(p0: WorldConfigurationBuilder?) {
        p0?.with(
            BackgroundRenderingSystem(
                configuration = DefaultRenderingSystemConfiguration,
                gameState = getKoin().get<GameState>()
            )
        )
        p0?.with(FloorRenderingSystem(configuration = DefaultRenderingSystemConfiguration))
        //  p0?.with(CharacterRenderingSystem(configuration = DefaultRenderingSystemConfiguration))
        p0?.with(WallRenderingSystem(configuration = DefaultRenderingSystemConfiguration))

        p0?.with(GUIRenderingSystem(configuration = DefaultRenderingSystemConfiguration))
    }
}
