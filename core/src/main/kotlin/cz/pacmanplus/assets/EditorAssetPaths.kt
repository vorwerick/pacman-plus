package cz.pacmanplus.assets

import com.badlogic.gdx.assets.AssetManager
import cz.pacmanplus.editor.Editor

object EditorAssetPaths {


    private const val EDITOR = "editor"
    private const val ICONS = "icons.png"



    fun icons(): String {
        return "${EDITOR}/$ICONS"
    }

}


