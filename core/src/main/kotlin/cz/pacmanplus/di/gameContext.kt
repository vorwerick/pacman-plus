package cz.pacmanplus.di

import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import cz.pacmanplus.game.DefaultCameraConfiguration
import cz.pacmanplus.game.GameState
import cz.pacmanplus.game.PlayerCamera
import cz.pacmanplus.game.core.entity.*
import cz.pacmanplus.game.core.entity.creator.FloorObjectsCreator
import cz.pacmanplus.game.core.entity.creator.ItemObjectsCreator
import cz.pacmanplus.game.core.entity.creator.WallObjectsCreator
import cz.pacmanplus.game.core.plugins.PhysicsPlugin
import cz.pacmanplus.game.core.systems.*
import cz.pacmanplus.game.core.systems.grid.WallGridSystem
import cz.pacmanplus.game.core.systems.lifecycle.CountdownLifecycleSystem
import cz.pacmanplus.game.core.systems.lifecycle.PlayerLifecycleSystem
import cz.pacmanplus.game.core.systems.lifecycle.HitpointsLifecycleSystem
import cz.pacmanplus.game.core.systems.physics.movement.PathMovementSystem
import cz.pacmanplus.game.core.systems.rendering.*
import cz.pacmanplus.game.core.systems.update.*
import org.koin.dsl.module

val gameContext = module {
    single<Stage> {
        Stage(ScreenViewport())
    }
    single<GameState> {
        GameState()
    }
    single<PlayerCamera> {
        PlayerCamera(config = DefaultCameraConfiguration)
    }
    single<FloorObjects> { FloorObjectsCreator() }
    single<WallObjects> { WallObjectsCreator() }
    single<ItemObjects> { ItemObjectsCreator() }
    single<LevelCreator> { LevelCreator() }
    single<CharacterCreator> { CharacterCreator() }
    single<World> {
        World(
            WorldConfigurationBuilder().with(PhysicsPlugin())
                .with(PlayerInputSystem())
                .with(ClockSystem())
                .with(WallGridSystem(32,32))
                .with(CountdownLifecycleSystem())
                .with(PlayerSystem())
                .with(PlayerLifecycleSystem())
                .with(HitpointsLifecycleSystem())
                .with(GateSystem())
                .with(ComputerPatrolSystem())
                .with(PathMovementSystem())
                .with(FloorRenderingSystem(configuration = DefaultRenderingSystemConfiguration))
                .with(CharacterRenderingSystem(configuration = DefaultRenderingSystemConfiguration))
                .with(WallRenderingSystem(configuration = DefaultRenderingSystemConfiguration))
                .with(PhysicsCircleRenderingSystem(configuration = DefaultRenderingSystemConfiguration))
                .with(PhysicsRectangleRenderingSystem(configuration = DefaultRenderingSystemConfiguration))
                .with(GUIRenderingSystem(configuration = DefaultRenderingSystemConfiguration))
                .with(PhysicsGuiSystem())
                .with(EntityGuiSystem())
                .build()
        )
    }
}

