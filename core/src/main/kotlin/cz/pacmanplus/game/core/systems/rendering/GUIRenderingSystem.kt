package cz.pacmanplus.game.core.systems.rendering

import com.artemis.Aspect
import com.artemis.BaseSystem
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import cz.pacmanplus.assets.AssetPaths
import cz.pacmanplus.game.PlayerCamera
import cz.pacmanplus.game.core.components.physics.DamageComponent
import cz.pacmanplus.game.core.components.physics.PositionComponent
import cz.pacmanplus.game.core.components.physics.PushableComponent
import cz.pacmanplus.game.core.components.physics.RectangleCollisionComponent
import cz.pacmanplus.game.graphics.findTexture
import org.koin.java.KoinJavaComponent.getKoin

class GUIRenderingSystem(val spriteBatch: SpriteBatch, val shapeRenderer: ShapeRenderer) : BaseSystem() {


    override fun processSystem() {
        val cam: PlayerCamera = getKoin().get<PlayerCamera>()

        shapeRenderer.projectionMatrix = cam.camera.combined
        spriteBatch.projectionMatrix = cam.camera.combined
        val cameraFrameTexture = AssetPaths.guiCameraFrame().findTexture()
        val width = cam.camera.viewportWidth
        val height = cam.camera.viewportHeight

        val panelWidth = 192f
        shapeRenderer.begin()
        shapeRenderer.color = Color.DARK_GRAY
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.rect(
            cam.camera.position.x + width - panelWidth - (width / 2),
            cam.camera.position.y - (height / 2),
            panelWidth,
            height
        )
        shapeRenderer.color = Color.WHITE
        shapeRenderer.end()

        spriteBatch.begin()
        spriteBatch.draw(
            cameraFrameTexture,
            cam.camera.position.x - (width / 2),
            cam.camera.position.y - (height / 2),
            width,
            height
        )
        spriteBatch.end()

    }


}
