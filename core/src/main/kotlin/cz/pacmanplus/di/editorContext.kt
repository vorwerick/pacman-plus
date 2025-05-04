package cz.pacmanplus.di

import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import cz.pacmanplus.editor.Editor
import cz.pacmanplus.game.DefaultCameraConfiguration
import cz.pacmanplus.game.GameState
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
import org.koin.java.KoinJavaComponent

val editorContext = module {
    single<Stage> {
        Stage(ScreenViewport())
    }
    single<AssetLibrary<Texture>> {
        AssetLibrary<Texture>()
    }

    single<Editor> { Editor() }
}

