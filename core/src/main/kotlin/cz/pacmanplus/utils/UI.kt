package cz.pacmanplus.utils

import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import ktx.actors.stage

fun Stage.centerStage(glyphLayout: GlyphLayout): Vector2 {
    return Vector2(width / 2f - glyphLayout.width / 2f, height / 2f - glyphLayout.height / 2f)
}
