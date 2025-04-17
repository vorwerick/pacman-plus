package cz.pacmanplus.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.maps.Map

/** expansive*/

class AssetLibrary<T> {

    enum class State {
        NotLoaded, Loading, Loaded
    }

    val items = mutableMapOf<String, T>()
    val status = State.NotLoaded


    suspend fun load(vararg aPackages: AssetPack) {
        aPackages.forEach { pack ->
            pack.load()
        }
    }
}


interface AssetPack {
    fun load()
}

class PlayerState {

}

class Loader {
    val ui = AssetLibrary<Map>()
    val maps = AssetLibrary<Map>()
    val playerState = AssetLibrary<PlayerState>()
    val gameTextures = AssetLibrary<Map>()

    companion object {
        fun get() {

        }

        fun findTexture(s: String): Texture {
            return Texture(Gdx.files.internal(s))
        }

    }

}


object Tiles {
    val wall = null
    val floor = null
    val chest = null
    val box = null
}

class TextureAssetPack : AssetPack {

    val textures = mutableListOf<Texture>()
    override fun load() {

        TODO("Not yet implemented")
    }
}

class MapAssetPack : AssetPack {
    override fun load() {
        TODO("Not yet implemented")
    }

}
