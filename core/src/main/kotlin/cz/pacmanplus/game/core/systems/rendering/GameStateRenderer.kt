package cz.pacmanplus.game.core.systems.rendering

import com.artemis.BaseSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
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

    // Grid configuration
    private val gridColumns = 5
    private val gridSpacingX = 100f
    private val gridSpacingY = 100f
    private val startX = 100f
    private val startY = 400f

    // Selected level tracking
    private var selectedLevelX = 0
    private var selectedLevelY = 0
    private var selectedLevel = 0

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
            is State.EditLevel -> processEditLevel((state.state as State.EditLevel).levelId)
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

    private fun processEditLevel(levelId: Int) {

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

        // Clear stage before adding actors
        stageLevelSelection.clear()

        // Add title label
        val titleLabel = Label("Level Selection", skin)
        titleLabel.setPosition(Gdx.graphics.width / 2f - titleLabel.width / 2f, Gdx.graphics.height - 100f)
        stageLevelSelection.addActor(titleLabel)

        // Create level buttons in a grid
        levels.forEachIndexed { index, level ->
            if(!levelButtons.containsKey(index)){
                levelButtons[index] = createToolButton("Level ${index + 1}", index)
            }

            // Calculate grid position
            val row = index / gridColumns
            val col = index % gridColumns
            val x = startX + (col * gridSpacingX)
            val y = startY - (row * gridSpacingY)

            // Set button position
            levelButtons[index]?.setPosition(x, y)

            // Set button color based on selection
            if (index == selectedLevel) {
                levelButtons[index]?.color = Color.WHITE
            } else {
                levelButtons[index]?.color = Color.GRAY
            }

            stageLevelSelection.addActor(levelButtons[index])
        }

        // Handle arrow key navigation
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            if (selectedLevelY > 0) {
                selectedLevelY--
                updateSelectedLevel()
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            val maxRows = (levels.size - 1) / gridColumns
            if (selectedLevelY < maxRows) {
                selectedLevelY++
                updateSelectedLevel()
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            if (selectedLevelX > 0) {
                selectedLevelX--
                updateSelectedLevel()
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            if (selectedLevelX < gridColumns - 1 && selectedLevel < levels.size - 1) {
                selectedLevelX++
                updateSelectedLevel()
            }
        }

        // Handle selection with Enter key
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            world.getSystem(GameStateSystem::class.java).changeGameState(State.LoadingLevel(selectedLevel))
        }

        stageLevelSelection.act()
        stageLevelSelection.draw()
    }

    private fun updateSelectedLevel() {
        selectedLevel = selectedLevelY * gridColumns + selectedLevelX
        // Ensure selectedLevel is within bounds
        val levels = getKoin().get<LevelLibrary>().levels
        if (selectedLevel >= levels.size) {
            selectedLevel = levels.size - 1
            selectedLevelX = selectedLevel % gridColumns
            selectedLevelY = selectedLevel / gridColumns
        }
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
