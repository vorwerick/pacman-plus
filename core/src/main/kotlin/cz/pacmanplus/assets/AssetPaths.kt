package cz.pacmanplus.assets

import com.badlogic.gdx.assets.AssetManager

enum class LevelTheme {
    Egypt, Rome, Japan
}

object AssetPaths {


    private const val MISCELLANEOUS = "miscellaneous"
    private const val CHARACTERS = "characters"
    private const val UI = "ui"

    private const val GROUND_TEXTURE = "Ground.png"
    private const val WALL_TEXTURE = "Walls_Overlap.png"
    private const val BOMB_BODY_TEXTURE = "Bomb_Body.png"
    private const val BOMB_FUSE_TEXTURE = "Bomb_Fuse.png"
    private const val CRATE = "Crate.png"
    private const val LEVER = "Lever.png"
    private const val EXPLOSION = "Explosion.png"
    private const val GATE = "GateHorizontal.png"
    private const val KEY = "Key.png"
    private const val PRESSURE_PLATE = "PressurePlate.png"
    private const val PLAYER = "character_solo_x2.png"
    private const val MUMMY = "foes_solo_x2.png"
    private const val CAMERA_FRAME = "Frame.png"
    private const val FLARE_EFFECT = "FlareEffect.png"

    fun floor(theme: LevelTheme): String {
        return "${theme.name.lowercase()}/$GROUND_TEXTURE"
    }

    fun wall(theme: LevelTheme): String {
        return "${theme.name.lowercase()}/$WALL_TEXTURE"
    }

    fun bombBody(): String {
        return "${MISCELLANEOUS}/$BOMB_BODY_TEXTURE"
    }

    fun bombFuse(): String {
        return "${MISCELLANEOUS}/$BOMB_FUSE_TEXTURE"
    }

    fun chest(): String {
        return "${MISCELLANEOUS}/$CRATE"
    }

    fun lever(): String {
        return "${MISCELLANEOUS}/${LEVER}"
    }

    fun explosion(): String {
        return "${MISCELLANEOUS}/$EXPLOSION"
    }

    fun gate(): String {
        return "${MISCELLANEOUS}/$GATE"
    }

    fun key(): String {
        return "${MISCELLANEOUS}/$KEY"
    }

    fun button(): String {
        return "${MISCELLANEOUS}/$PRESSURE_PLATE"
    }

    fun player(): String {
        return "${CHARACTERS}/$PLAYER"
    }

    fun enemyMummy(): String {
        return "${CHARACTERS}/$MUMMY"
    }

    fun box(): String {
        return "${MISCELLANEOUS}/$CRATE"
    }

    fun guiCameraFrame(): String {
        return "${UI}/$CAMERA_FRAME"

    }

    fun flareEffect(): String {
        return  "${MISCELLANEOUS}/$FLARE_EFFECT"
    }


}

fun AssetManager.getAll(): List<String> {
    return listOf(
        AssetPaths.guiCameraFrame(),
        AssetPaths.floor(LevelTheme.Egypt),
        AssetPaths.wall(LevelTheme.Egypt),
        AssetPaths.bombBody(),
        AssetPaths.bombFuse(),
        AssetPaths.chest(),
        AssetPaths.lever(),
        AssetPaths.explosion(),
        AssetPaths.gate(),
        AssetPaths.key(),
        AssetPaths.button(),
        AssetPaths.player(),
        AssetPaths.enemyMummy(),
        AssetPaths.flareEffect(),
    )

}
