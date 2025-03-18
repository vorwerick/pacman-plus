package cz.pacmanplus.di

import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import cz.pacmanplus.game.DefaultCameraConfiguration
import cz.pacmanplus.game.GameState
import cz.pacmanplus.game.PlayerCamera
import cz.pacmanplus.game.core.systems.*
import cz.pacmanplus.game.core.systems.physics.MovementSystem
import cz.pacmanplus.game.core.systems.rendering.DefaultRenderingSystemConfiguration
import cz.pacmanplus.game.core.systems.rendering.PhysicsCircleRenderingSystem
import cz.pacmanplus.game.core.systems.rendering.PhysicsRectangleRenderingSystem
import org.koin.dsl.module

val gameContext = module {
    single<GameState> {
        GameState()
    }
    single<PlayerCamera> {
        PlayerCamera(config = DefaultCameraConfiguration)
    }
    single<World> {
        World(
            WorldConfigurationBuilder()
                .with(PlayerSystem())
                .with(PlayerInputSystem())
                //.with(Movement16TileSystem())
                .with(MovementSystem())
                .with(DamageSystem())
                .with(PickupSystem())
                .with(PhysicsCircleRenderingSystem(configuration = DefaultRenderingSystemConfiguration))
                .with(PhysicsRectangleRenderingSystem(configuration = DefaultRenderingSystemConfiguration))
                .build()
        )
    }
}

