package cz.pacmanplus.di

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.AssetLoader
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import cz.pacmanplus.Navigator
import org.koin.dsl.module

val accessories = module {
    single<AssetManager> {
        AssetManager()
    }
    single<ShapeRenderer> {
        ShapeRenderer().apply { setAutoShapeType(true) }
    }
    single<SpriteBatch> {
        SpriteBatch()
    }
}

