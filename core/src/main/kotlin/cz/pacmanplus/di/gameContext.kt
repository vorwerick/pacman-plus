package cz.pacmanplus.di

import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import cz.pacmanplus.game.DefaultCameraConfiguration
import cz.pacmanplus.game.GameState
import cz.pacmanplus.game.LevelLibrary
import cz.pacmanplus.game.PlayerCamera
import cz.pacmanplus.game.core.entity.*
import cz.pacmanplus.game.core.entity.creator.FloorObjectsCreator
import cz.pacmanplus.game.core.entity.creator.ItemObjectsCreator
import cz.pacmanplus.game.core.entity.creator.WallObjectsCreator
import cz.pacmanplus.game.core.plugins.PhysicsPlugin
import cz.pacmanplus.game.core.plugins.RenderingPlugin
import cz.pacmanplus.game.core.systems.*
import cz.pacmanplus.game.core.systems.grid.WallGridSystem
import cz.pacmanplus.game.core.systems.lifecycle.CountdownLifecycleSystem
import cz.pacmanplus.game.core.systems.lifecycle.PlayerLifecycleSystem
import cz.pacmanplus.game.core.systems.lifecycle.LifecycleSystem
import cz.pacmanplus.game.core.systems.physics.movement.PathMovementSystem
import cz.pacmanplus.game.core.systems.physics.movement.ProjectileMovementSystem
import cz.pacmanplus.game.core.systems.rendering.*
import cz.pacmanplus.game.core.systems.update.*
import cz.pacmanplus.screens.AssetLibrary
import cz.pacmanplus.screens.PlayerState
import org.koin.dsl.module

val gameContext = module {
    single<Stage> {
        Stage(ScreenViewport())
    }
    single<LevelLibrary> {
        LevelLibrary()
    }
    single<GameState> {
        GameState()
    }
    single<PlayerCamera> {
        PlayerCamera(config = DefaultCameraConfiguration)
    }
    single<AssetLibrary<Texture>> {
        AssetLibrary<Texture>()
    }
    single<AssetLibrary<PlayerState>> {
        AssetLibrary<PlayerState>()
    }
    single<FloorObjects> { FloorObjectsCreator() }
    single<WallObjects> { WallObjectsCreator() }
    single<ItemObjects> { ItemObjectsCreator() }
    single<EntityFactory> { EntityFactory() }
    single<CharacterCreator> { CharacterCreator() }
    single<World> {

        World(
            WorldConfigurationBuilder()
                .with(GameStateSystem())
                .with(LevelEditorSystem())
                .with(PhysicsPlugin())

                .with(PlayerInputSystem())
                .with(TimerSystem())
                .with(EffectSystem())
                .with(WallGridSystem(get<GameState>()))
                .with(CountdownLifecycleSystem())
                .with(PlayerSystem())
                .with(PlayerLifecycleSystem())
                .with(LifecycleSystem())
                .with(GateSystem())
                .with(ComputerPatrolSystem())
                .with(ProjectileMovementSystem())
                .with(PathMovementSystem())

                .with(PhysicsCircleRenderingSystem(get(), get()))
                .with(PhysicsRectangleRenderingSystem(get(), get()))


                .with(PhysicsGuiSystem())
                .with(EntityGuiSystem())
                .with(RenderingPlugin())
                .with(LevelEditorRenderer(get(), get()))



                .build()
        )
    }
}

