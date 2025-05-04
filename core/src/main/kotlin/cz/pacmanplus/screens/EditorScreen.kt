package cz.pacmanplus.screens

import com.artemis.World
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.kotcrab.vis.ui.VisUI
import java.nio.ByteBuffer
import cz.pacmanplus.assets.EditorAssetPaths
import cz.pacmanplus.di.gameContext
import cz.pacmanplus.editor.Editor
import cz.pacmanplus.game.GameState
import cz.pacmanplus.game.GraphicsMode
import cz.pacmanplus.game.PlayerCamera
import cz.pacmanplus.game.State
import cz.pacmanplus.game.core.entity.CharacterCreator
import cz.pacmanplus.game.core.entity.FloorObjects
import cz.pacmanplus.game.core.entity.ItemObjects
import cz.pacmanplus.game.core.entity.WallObjects
import cz.pacmanplus.game.core.systems.rendering.PhysicsCircleRenderingSystem
import cz.pacmanplus.game.core.systems.rendering.PhysicsRectangleRenderingSystem
import ktx.app.KtxScreen
import org.koin.core.context.unloadKoinModules
import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.LoggerFactory

class EditorScreen : KtxScreen {

    val log = LoggerFactory.getLogger("EditorScreen")

    var physicsMode = true
    var paused = false

    // Entity creators
    private val wallCreator: WallObjects = getKoin().get<WallObjects>()
    private val itemCreator: ItemObjects = getKoin().get<ItemObjects>()
    private val floorObjects: FloorObjects = getKoin().get<FloorObjects>()
    private val characterCreator: CharacterCreator = getKoin().get<CharacterCreator>()

    // Current entity type to place
    private var currentWallType = 0
    private var currentItemType = 0
    private var currentFloorType = 0
    private var currentCharacterType = 0

    // Available entity types
    private val wallTypes = listOf(
        "Bedrock", "Wall", "Box", "Turret", "Generator",
        "Chest", "Stone", "Gate", "Door", "Bomb"
    )

    private val itemTypes = listOf(
        "Life", "Shield", "Score", "Key", "Dash", "Sprint"
    )

    private val floorTypes = listOf(
        "Floor", "Switch", "Trigger", "EnemySpawner", "EnemyGate",
        "Teleport", "Lava", "Explosion", "Ray", "Projectile",
        "Trapdoor", "Ventilator", "Void", "Start", "Finish", "BoxSpawner"
    )

    private val characterTypes = listOf(
        "Player", "EnemyPatrol"
    )

    // Current category (0 = Wall, 1 = Item, 2 = Floor, 3 = Character)
    private var currentCategory = 0

    // UI components
    private val stage = Stage(ScreenViewport())
    private lateinit var skin: Skin
    private val batch = SpriteBatch()
    private val font = BitmapFont()
    private val editor: Editor = getKoin().get()

    // UI panels
    private lateinit var toolPanel: Table
    private lateinit var layerPanel: Table
    private lateinit var propertiesPanel: Table
    private lateinit var statusBar: Table

    // UI textures
    private lateinit var iconsTexture: Texture

    init {
        // Initialize UI skin
        skin = VisUI.getSkin()
       // skin.add("default-font", font)

        // Create a white pixel texture for the buttons
        val pixmap = Pixmap(1, 1, Pixmap.Format.RGBA8888)
        pixmap.setColor(Color.WHITE)
        pixmap.fill()
        val pixelTexture = Texture(pixmap)
        pixmap.dispose()

        val buttonStyle = TextButton.TextButtonStyle()
        buttonStyle.font = font
        buttonStyle.up = TextureRegionDrawable(TextureRegion(pixelTexture)).tint(Color.DARK_GRAY)
        buttonStyle.down = TextureRegionDrawable(TextureRegion(pixelTexture)).tint(Color.GRAY)
        buttonStyle.checked = TextureRegionDrawable(TextureRegion(pixelTexture)).tint(Color.LIGHT_GRAY)
        buttonStyle.over = TextureRegionDrawable(TextureRegion(pixelTexture)).tint(Color.LIGHT_GRAY)
        skin.add("default", buttonStyle)

        // Load editor icons
        iconsTexture = Texture(Gdx.files.internal(EditorAssetPaths.icons()))

        // Set up UI
        setupUI()

        // Set up input processor to handle both UI and game input
        val inputMultiplexer = InputMultiplexer()
        inputMultiplexer.addProcessor(stage)  // UI input takes priority
        inputMultiplexer.addProcessor(createGameInputProcessor())  // Game input as fallback
        Gdx.input.inputProcessor = inputMultiplexer

        // Initialize editor
        val level = editor.getLevel()

        log.debug("Editor Screen initialized")
    }

    private fun setupUI() {
        // Main container
        val root = Table()
        root.setFillParent(true)
        stage.addActor(root)

        // Tool panel (left side)
        toolPanel = Table()
       // toolPanel.background = skin.newDrawable("white", Color(0.2f, 0.2f, 0.2f, 0.8f))
        toolPanel.pad(5f)

        // Add category buttons
        val categoryTable = Table()
        categoryTable.add(Label("Categories", skin)).colspan(2).pad(5f).row()

        val wallButton = createToolButton("Wall", 0)
        val itemButton = createToolButton("Item", 1)
        val floorButton = createToolButton("Floor", 2)
        val characterButton = createToolButton("Character", 3)

        categoryTable.add(wallButton).pad(2f)
        categoryTable.add(itemButton).pad(2f).row()
        categoryTable.add(floorButton).pad(2f)
        categoryTable.add(characterButton).pad(2f)

        toolPanel.add(categoryTable).pad(5f).row()

        // Add file operation buttons
        val fileTable = Table()
        fileTable.add(Label("File", skin)).colspan(2).pad(5f).row()

        val newButton = TextButton("New", skin)
        newButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                editor.new(32, 32, 32, 0, 0)
                updateLayerPanel()
            }
        })

        val saveButton = TextButton("Save", skin)
        saveButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                editor.save("level.dat")
            }
        })

        val loadButton = TextButton("Load", skin)
        loadButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                editor.load("level.dat")
                updateLayerPanel()
            }
        })

        fileTable.add(newButton).pad(2f)
        fileTable.add(saveButton).pad(2f).row()
        fileTable.add(loadButton).pad(2f).colspan(2)

        toolPanel.add(fileTable).pad(5f).row()

        // Layer panel (right side)
        layerPanel = Table()
        layerPanel.background = skin.newDrawable("white", Color(0.2f, 0.2f, 0.2f, 0.8f))
        layerPanel.pad(5f)

        // Add layer buttons
        val layerControlTable = Table()
        layerControlTable.add(Label("Layers", skin)).colspan(3).pad(5f).row()

        val addLayerButton = TextButton("+", skin)
        addLayerButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                editor.createLayer()
                updateLayerPanel()
            }
        })

        val removeLayerButton = TextButton("-", skin)
        removeLayerButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                editor.removeLayer()
                updateLayerPanel()
            }
        })

        val visibilityButton = TextButton("👁", skin)
        visibilityButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                editor.changeLayerVisibility()
            }
        })

        layerControlTable.add(addLayerButton).pad(2f)
        layerControlTable.add(removeLayerButton).pad(2f)
        layerControlTable.add(visibilityButton).pad(2f)

        layerPanel.add(layerControlTable).pad(5f).row()

        // Properties panel (bottom)
        propertiesPanel = Table()
        propertiesPanel.background = skin.newDrawable("white", Color(0.2f, 0.2f, 0.2f, 0.8f))
        propertiesPanel.pad(5f)
        propertiesPanel.add(Label("Properties", skin)).pad(5f).row()

        // Status bar
        statusBar = Table()
        statusBar.background = skin.newDrawable("white", Color(0.1f, 0.1f, 0.1f, 0.9f))
        statusBar.pad(2f)
        statusBar.add(Label("Ready", skin)).expandX().left()

        // Add all panels to root
        root.add(toolPanel).width(150f).fillY().expandY().pad(5f)
        root.add().expand().fill()
        root.add(layerPanel).width(150f).fillY().expandY().pad(5f).row()
        root.add(propertiesPanel).height(100f).fillX().expandX().colspan(3).pad(5f).row()
        root.add(statusBar).height(20f).fillX().expandX().colspan(3).pad(0f)

        // Update layer panel with initial layers
        updateLayerPanel()
    }

    private fun createToolButton(text: String, categoryId: Int): TextButton {
        val button = TextButton(text, skin)
        button.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                currentCategory = categoryId
                updateStatusBar("Selected category: $text")
            }
        })
        return button
    }

    private fun updateLayerPanel() {
        // Clear existing layer buttons
        val layerButtonsTable = Table()
        layerButtonsTable.pad(5f)

        // Add layer buttons based on current layers
        val level = editor.getLevel()
        for (i in level.layers.indices) {
            val layer = level.layers[i]
            val layerButton = TextButton("Layer ${i+1}: ${layer.name}", skin)
            val layerId = i  // Capture the current index
            layerButton.addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    // Select this layer
                    editor.setCurrentLayerId(layerId)
                    updateStatusBar("Selected layer: ${layer.name}")
                }
            })
            layerButtonsTable.add(layerButton).fillX().expandX().pad(2f).row()
        }

        // Replace the existing layer buttons
        layerPanel.clear()

        // Add layer control buttons
        val layerControlTable = Table()
        layerControlTable.add(Label("Layers", skin)).colspan(3).pad(5f).row()

        val addLayerButton = TextButton("+", skin)
        addLayerButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                editor.createLayer()
                updateLayerPanel()
            }
        })

        val removeLayerButton = TextButton("-", skin)
        removeLayerButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                editor.removeLayer()
                updateLayerPanel()
            }
        })

        val visibilityButton = TextButton("👁", skin)
        visibilityButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                editor.changeLayerVisibility()
            }
        })

        layerControlTable.add(addLayerButton).pad(2f)
        layerControlTable.add(removeLayerButton).pad(2f)
        layerControlTable.add(visibilityButton).pad(2f)

        layerPanel.add(layerControlTable).pad(5f).row()
        layerPanel.add(layerButtonsTable).expand().fill().pad(5f)
    }

    private fun updateStatusBar(message: String) {
        statusBar.clear()
        statusBar.add(Label(message, skin)).expandX().left()
    }

    private fun createGameInputProcessor(): InputAdapter {
        return object : InputAdapter() {
            override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
                // Convert screen coordinates to world coordinates
                val camera = getKoin().get<PlayerCamera>().camera
                val mousePos = Vector3(screenX.toFloat(), screenY.toFloat(), 0f)
                camera.unproject(mousePos)

                // Calculate grid position
                val gridX = ((mousePos.x + 16) / 32).toInt()
                val gridY = ((mousePos.y + 16) / 32).toInt()
                val gridXFloat = gridX * 32f
                val gridYFloat = gridY * 32f

                // Place entity based on current category and type
                if (button == Input.Buttons.LEFT) {
                    // Check if Shift is pressed for object selection
                    if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
                        // Try to select an object at this position
                        val selected = editor.selectObject(gridX, gridY)
                        if (selected) {
                            updateStatusBar("Selected object at ($gridX, $gridY)")
                        } else {
                            updateStatusBar("No object at ($gridX, $gridY)")
                        }
                    } else {
                        // Place a new entity
                        placeEntity(gridXFloat, gridYFloat)
                    }
                    return true
                } else if (button == Input.Buttons.RIGHT) {
                    // Cycle to next category on right click
                    currentCategory = (currentCategory + 1) % 4
                    updateStatusBar("Right click: switched to category ${
                        when(currentCategory) {
                            0 -> "Wall"
                            1 -> "Item"
                            2 -> "Floor"
                            3 -> "Character"
                            else -> "Unknown"
                        }
                    }")
                    return true
                }
                return false
            }

            override fun keyDown(keycode: Int): Boolean {
                // Handle category switching
                when (keycode) {
                    Input.Keys.NUM_1 -> {
                        currentCategory = 0
                        updateStatusBar("Selected Wall category")
                        return true
                    }
                    Input.Keys.NUM_2 -> {
                        currentCategory = 1
                        updateStatusBar("Selected Item category")
                        return true
                    }
                    Input.Keys.NUM_3 -> {
                        currentCategory = 2
                        updateStatusBar("Selected Floor category")
                        return true
                    }
                    Input.Keys.NUM_4 -> {
                        currentCategory = 3
                        updateStatusBar("Selected Character category")
                        return true
                    }
                    Input.Keys.E -> {
                        // Handle entity type cycling
                        when (currentCategory) {
                            0 -> {
                                currentWallType = (currentWallType + 1) % wallTypes.size
                                updateStatusBar("Selected Wall type: ${wallTypes[currentWallType]}")
                            }
                            1 -> {
                                currentItemType = (currentItemType + 1) % itemTypes.size
                                updateStatusBar("Selected Item type: ${itemTypes[currentItemType]}")
                            }
                            2 -> {
                                currentFloorType = (currentFloorType + 1) % floorTypes.size
                                updateStatusBar("Selected Floor type: ${floorTypes[currentFloorType]}")
                            }
                            3 -> {
                                currentCharacterType = (currentCharacterType + 1) % characterTypes.size
                                updateStatusBar("Selected Character type: ${characterTypes[currentCharacterType]}")
                            }
                        }
                        return true
                    }
                    Input.Keys.D -> {
                        // Remove selected object
                        editor.removeSelectedObject()
                        updateStatusBar("Removed selected object")
                        return true
                    }
                }
                return false
            }
        }
    }

    override fun render(delta: Float) {
        // Handle system input (not related to editor actions)
        handleGfxModeInput()
        handlePauseUnpauseInput()

        // Update world
        worldUpdate()

        // Update and draw UI
        stage.act(delta)
        stage.draw()

        // Display status message about layer opacity
        updateStatusBar("Selected layer (ID: ${editor.getLayerId()}) shown with 100% opacity, others at 50%")
    }

    // This method is kept for reference but no longer used directly
    // Input handling is now done through the InputMultiplexer and createGameInputProcessor
    private fun handleEditorInput() {
        // This method is no longer used directly
        // Input handling is now done through the InputMultiplexer and createGameInputProcessor
    }

    private fun placeEntity(x: Float, y: Float) {
        // Calculate grid coordinates
        val gridX = (x / 32).toInt()
        val gridY = (y / 32).toInt()

        // Get object ID based on current category and type
        val objectId = when (currentCategory) {
            0 -> currentWallType + 1  // Wall objects start at ID 1
            1 -> currentItemType + 101  // Item objects start at ID 101
            2 -> currentFloorType + 201  // Floor objects start at ID 201
            3 -> currentCharacterType + 301  // Character objects start at ID 301
            else -> 0
        }

        // Add object to the editor using the current layer ID
        val currentLayerId = editor.getLayerId()
        editor.addObject(currentLayerId, objectId, gridX, gridY)
        updateStatusBar("Added object $objectId at ($gridX, $gridY) on layer $currentLayerId")

        // Also place the entity in the world for visualization
        when (currentCategory) {
            0 -> placeWall(x, y)
            1 -> placeItem(x, y)
            2 -> placeFloor(x, y)
            3 -> placeCharacter(x, y)
        }
    }

    private fun placeWall(x: Float, y: Float) {
        when (currentWallType) {
            0 -> wallCreator.bedrock(x, y)
            1 -> wallCreator.wall(x, y, 3)
            2 -> wallCreator.box(x, y, 3)
            3 -> wallCreator.turret(x, y, 300, Vector2(0f, 1f))
            4 -> wallCreator.generator(x, y, 3)
            5 -> wallCreator.chest(x, y, 3, 1)
            6 -> wallCreator.stone(x, y)
            7 -> wallCreator.gate(x, y, 1)
            8 -> wallCreator.door(x, y, 1)
            9 -> wallCreator.bomb(x, y)
        }
        log.debug("Placed wall: ${wallTypes[currentWallType]} at $x, $y")
    }

    private fun placeItem(x: Float, y: Float) {
        when (currentItemType) {
            0 -> itemCreator.life(x, y)
            1 -> itemCreator.shield(x, y)
            2 -> itemCreator.score(x, y)
            3 -> itemCreator.key(x, y)
            4 -> itemCreator.dash(x, y)
            5 -> itemCreator.sprint(x, y)
        }
        log.debug("Placed item: ${itemTypes[currentItemType]} at $x, $y")
    }

    private fun placeFloor(x: Float, y: Float) {
        when (currentFloorType) {
            0 -> floorObjects.floor(x, y)
            1 -> floorObjects.switch(x, y, 1)
            2 -> floorObjects.trigger(x, y, 1)
            3 -> floorObjects.enemySpawner(x, y)
            4 -> floorObjects.enemyGate(x, y)
            5 -> floorObjects.teleport(x, y, 1, 2)
            6 -> floorObjects.lava(x, y)
            7 -> floorObjects.explosion(x, y)
            8 -> floorObjects.ray(x, y)
            9 -> floorObjects.projectile(x, y, 100, Vector2(1f, 0f))
            10 -> floorObjects.trapdoor(x, y)
            11 -> floorObjects.ventilator(x, y, Vector2(1f, 0f), 100)
            12 -> floorObjects.void(x, y)
            13 -> floorObjects.start(x, y)
            14 -> floorObjects.finish(x, y)
            15 -> floorObjects.boxSpawner(x, y)
        }
        log.debug("Placed floor: ${floorTypes[currentFloorType]} at $x, $y")
    }

    private fun placeCharacter(x: Float, y: Float) {
        when (currentCharacterType) {
            0 -> characterCreator.player(x, y)
            1 -> characterCreator.enemyPatrol(x, y)
        }
        log.debug("Placed character: ${characterTypes[currentCharacterType]} at $x, $y")
    }

    private fun handleGfxModeInput() {
        val gameState = getKoin().get<GameState>()
        if (Gdx.input.isKeyJustPressed(Input.Keys.F1)) {
            if (gameState.graphicsMode is GraphicsMode.Normal) {
                gameState.graphicsMode = GraphicsMode.Debug
            } else {
                gameState.graphicsMode = GraphicsMode.Normal
            }
            log.info("Graphics mode changed to ${gameState.graphicsMode}")
        }
    }

    private fun handlePauseUnpauseInput() {
        val gameState = getKoin().get<GameState>()
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (gameState.state is State.Running) {
                gameState.state = State.Paused
            } else if (gameState.state is State.Paused) {
                gameState.state = State.Running
            }
            log.info("Game state changed to ${gameState.state}")
        }
    }

    private fun worldUpdate() {
        val state = getKoin().get<GameState>()
        val world = getKoin().get<World>()

        world.process()

        world.getSystem(PhysicsCircleRenderingSystem::class.java).isEnabled = physicsMode
        world.getSystem(PhysicsRectangleRenderingSystem::class.java).isEnabled = physicsMode
    }

    override fun dispose() {
        // Dispose of UI resources
        stage.dispose()
        skin.dispose()
        batch.dispose()
        font.dispose()
        iconsTexture.dispose()

        // Dispose of world resources
        getKoin().get<World>().dispose()
        unloadKoinModules(gameContext)
    }

    override fun show() {
        // Set up input processor to handle both UI and game input
        val inputMultiplexer = InputMultiplexer()
        inputMultiplexer.addProcessor(stage)  // UI input takes priority
        inputMultiplexer.addProcessor(createGameInputProcessor())  // Game input as fallback
        Gdx.input.inputProcessor = inputMultiplexer

        log.debug("Editor.kt Screen shown")
    }

    override fun resize(w: Int, h: Int) {
        log.debug("Window resized {}", Vector2(w.toFloat(), h.toFloat()))

        // Resize the UI viewport
        stage.viewport.update(w, h, true)

        // Resize the game camera
        val cam = getKoin().get<PlayerCamera>()
    }

    override fun pause() {
        log.debug("Window paused")
    }

    override fun resume() {
        log.debug("Window resumed")
    }

    override fun hide() {
        Gdx.input.inputProcessor = null
        log.debug("Window hidden")
    }
}
