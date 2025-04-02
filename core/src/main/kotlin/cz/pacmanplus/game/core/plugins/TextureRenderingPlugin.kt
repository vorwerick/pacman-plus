package cz.pacmanplus.game.core.plugins

import com.artemis.ArtemisPlugin
import com.artemis.WorldConfigurationBuilder
import cz.pacmanplus.game.core.systems.physics.MovementPhysicsSystem
import cz.pacmanplus.game.core.systems.physics.PushPhysicsSystem
import cz.pacmanplus.game.core.systems.physics.RollingPhysicsSystem

class TextureRenderingPlugin : ArtemisPlugin {
    override fun setup(p0: WorldConfigurationBuilder?) {
       // p0?.with(MovementPhysicsSystem())
      //  p0?.with(PushPhysicsSystem())
       // p0?.with(RollingPhysicsSystem())
    }
}
