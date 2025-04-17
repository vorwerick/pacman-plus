package cz.pacmanplus.game.core.plugins

import com.artemis.ArtemisPlugin
import com.artemis.WorldConfigurationBuilder
import cz.pacmanplus.game.core.systems.physics.collisions.*
import cz.pacmanplus.game.core.systems.physics.movement.MovementSystem
import cz.pacmanplus.game.core.systems.physics.movement.BoxMovementSystem
import cz.pacmanplus.game.core.systems.physics.movement.StoneMovementSystem
import cz.pacmanplus.game.core.systems.physics.movement.UnlockingSystem

class PhysicsPlugin : ArtemisPlugin {
    override fun setup(p0: WorldConfigurationBuilder?) {

        //movement
        p0?.with(MovementSystem())
        p0?.with(BoxMovementSystem())
        p0?.with(StoneMovementSystem())
        p0?.with(UnlockingSystem())

        //collisions
        p0?.with(DamageSystem())
        p0?.with(ButtonSystem())
        p0?.with(SwitchSystem())
        p0?.with(PickupSystem())
        p0?.with(ExplosionSystem())
        p0?.with(TeleportSystem())
        p0?.with(TurretSystem())
        p0?.with(GeneratorSystem())


    }
}
