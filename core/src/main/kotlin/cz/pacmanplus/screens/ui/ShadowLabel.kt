package cz.pacmanplus.screens.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.kotcrab.vis.ui.VisUI
import cz.pacmanplus.utils.Position

class ShadowLabel(var title: String, var titleColor: Color = Color.WHITE, val shadowColor: Color = Color.BLACK) {

    private lateinit var label: Label
   private lateinit var shadow: Label

    init {
        label = Label(title, VisUI.getSkin()).apply {
            color = titleColor
        }
        shadow = Label(title, VisUI.getSkin()).apply {
            color = shadowColor
        }
    }

    fun setPosition(position: Vector2) {
        label.setPosition(position.x, position.y)
        shadow.setPosition(position.x - 3, position.y - 3)
    }

    fun changeTitle(newTitle: String) {
        title = newTitle
    }

    fun addToStage(stage: Stage) {
        stage.addActor(shadow)
        stage.addActor(label)

    }

    fun changeColor(newColor: Color) {
        label.color = newColor
    }

    fun getTitle(): Label {

        return label
    }


}
