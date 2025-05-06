package cz.pacmanplus.game.core.systems.rendering

import com.artemis.BaseSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.kotcrab.vis.ui.VisUI
import cz.pacmanplus.assets.AssetPaths
import cz.pacmanplus.game.GameState
import cz.pacmanplus.game.LevelLibrary
import cz.pacmanplus.game.PlayerCamera
import cz.pacmanplus.game.State
import cz.pacmanplus.game.core.systems.GameStateSystem
import cz.pacmanplus.game.graphics.findTexture
import org.koin.java.KoinJavaComponent.getKoin

class GameStateRenderer(val spriteBatch: SpriteBatch, val shapeRenderer: ShapeRenderer, val camera: Camera) :
    BaseSystem() {

    lateinit var skin: Skin
    lateinit var stageReadyState: Stage
    lateinit var stageLevelSelection : Stage

    lateinit var stageLoadingLevel: Stage
    lateinit var stageFinished : Stage

    lateinit var stageEditor : Stage

    val levelButtons: MutableMap<Int, TextButton> = mutableMapOf()


    init {
        skin = VisUI.getSkin()
        stageReadyState = Stage(ScreenViewport())
        stageFinished = Stage(ScreenViewport())
        stageLoadingLevel= Stage(ScreenViewport())
        stageLevelSelection = Stage(ScreenViewport())
        stageEditor = Stage(ScreenViewport())

    }


    override fun processSystem() {
        val state = getKoin().get<GameState>()




        when (state.state) {
            State.Finished -> processFinishedGui()
            is State.Ready -> processReadyGui((state.state as State.Ready).levelId)
            State.SelectLevel -> processSelectLevelGui()
            is State.LoadingLevel -> processLoadingLevelGui((state.state as State.LoadingLevel).levelId)
            else -> {}
        }
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

    private fun processLoadingLevelGui(loadingLevel: Int) {
        Gdx.input.inputProcessor = stageLoadingLevel
        //generate text level is loading
        val label = Label("Level is loading...", skin)
        label.setPosition(stageLoadingLevel.width / 2 - label.width / 2, stageLoadingLevel.height / 2 - label.height / 2)
        stageLoadingLevel.addActor(label)

        stageLoadingLevel.act()
        stageLoadingLevel.draw()
    }

    private fun processFinishedGui() {

    }

    private fun processReadyGui(levelId: Int) {
        Gdx.input.inputProcessor = stageLevelSelection
        Label("Press any key to start", skin).let { label ->
            stageReadyState.addActor(label)
        }
        stageReadyState.act()
        stageReadyState.draw()

    }

    private fun processSelectLevelGui() {
        Gdx.input.inputProcessor = stageLevelSelection

        val levels = getKoin().get<LevelLibrary>().levels

        levels.forEachIndexed { index, level ->
            if(!levelButtons.containsKey(index)){
                levelButtons[index] = createToolButton("Level ${index + 1}", index).apply {
                    setPosition(192f * index, 192f)
                }
            }
        }

        levelButtons.forEach {
            stageLevelSelection.addActor(it.value)
        }
        stageLevelSelection.act()
        stageLevelSelection.draw()

    }

    private fun createToolButton(text: String, categoryId: Int): TextButton {
        val button = TextButton(text, skin)
        button.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                world.getSystem(GameStateSystem::class.java).changeGameState(State.EditLevel(categoryId))
                println("Clicked on $text")
            }
        })

        return button
    }


}
