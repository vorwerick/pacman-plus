package cz.pacmanplus.game.core.systems

import com.artemis.BaseSystem
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import cz.pacmanplus.game.core.components.physics.PositionComponent

class LevelEditorRenderer(val spriteBatch: SpriteBatch, val shapeRenderer: ShapeRenderer) : BaseSystem() {


    override fun processSystem() {

        val levelEditorSystem = world.getSystem(LevelEditorSystem::class.java)

        shapeRenderer.begin()
        shapeRenderer.setAutoShapeType(true)

        shapeRenderer.color = Color.DARK_GRAY
        for (x in 0 until 32) {
            for (y in 0 until 32) {
                shapeRenderer.rect(x * 32f, y * 32f , 32f, 32f)
            }
        }



        shapeRenderer.color = Color.WHITE

        if (levelEditorSystem.selected != null) {
            val entity = world.getEntity(levelEditorSystem.selected!!)
            entity.getComponent<PositionComponent>(PositionComponent::class.java)?.let { position ->
                shapeRenderer.rect(position.x , position.y , 32f, 32f)

            }

        }

        shapeRenderer.color = Color.GRAY
        shapeRenderer.rect(levelEditorSystem.mouseTile.x * 32f, levelEditorSystem.mouseTile.y * 32f ,32f, 32f)

        shapeRenderer.end()


    }
}
