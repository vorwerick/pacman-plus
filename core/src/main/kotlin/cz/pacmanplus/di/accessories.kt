package cz.pacmanplus.di

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.AssetLoader
import cz.pacmanplus.Navigator
import org.koin.dsl.module

val accessories = module {
    single<AssetManager> {
        AssetManager()
    }
}

