package cz.pacmanplus.screens

import com.artemis.World
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import cz.pacmanplus.di.gameContext
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
import org.koin.core.context.loadKoinModules
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

    init {
        //loadKoinModules(gameContext)

      //  val levelCreator: LevelCreator = getKoin().get()
        //levelCreator.createLevelEmpty(32, 32)

        log.debug("Editor Screen initialized")
    }

    override fun render(delta: Float) {
        handleGfxModeInput()
        handlePauseUnpauseInput()
        handleEditorInput()

        worldUpdate()
    }

    private fun handleEditorInput() {
        // Handle category switching
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            currentCategory = 0
            log.debug("Selected Wall category")
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            currentCategory = 1
            log.debug("Selected Item category")
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            currentCategory = 2
            log.debug("Selected Floor category")
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
            currentCategory = 3
            log.debug("Selected Character category")
        }

        // Handle entity type cycling
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            when (currentCategory) {
                0 -> {
                    currentWallType = (currentWallType + 1) % wallTypes.size
                    log.debug("Selected Wall type: ${wallTypes[currentWallType]}")
                }
                1 -> {
                    currentItemType = (currentItemType + 1) % itemTypes.size
                    log.debug("Selected Item type: ${itemTypes[currentItemType]}")
                }
                2 -> {
                    currentFloorType = (currentFloorType + 1) % floorTypes.size
                    log.debug("Selected Floor type: ${floorTypes[currentFloorType]}")
                }
                3 -> {
                    currentCharacterType = (currentCharacterType + 1) % characterTypes.size
                    log.debug("Selected Character type: ${characterTypes[currentCharacterType]}")
                }
            }
        }

        // Handle mouse clicks for entity placement
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) || Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            val camera = getKoin().get<PlayerCamera>().camera
            val mousePos = Vector3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f)
            camera.unproject(mousePos)

            // Calculate grid position
            val gridX = ((mousePos.x + 16) / 32).toInt() * 32f
            val gridY = ((mousePos.y + 16) / 32).toInt() * 32f

            // Place entity based on current category and type
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                placeEntity(gridX, gridY)
            } else if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
                // Cycle to next category on right click
                currentCategory = (currentCategory + 1) % 4
                log.debug("Right click: switched to category ${
                    when(currentCategory) {
                        0 -> "Wall"
                        1 -> "Item"
                        2 -> "Floor"
                        3 -> "Character"
                        else -> "Unknown"
                    }
                }")
            }
        }
    }

    private fun placeEntity(x: Float, y: Float) {
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
        getKoin().get<World>().dispose()
        unloadKoinModules(gameContext)
    }

    override fun show() {
        log.debug("Editor Screen shown")
    }

    override fun resize(w: Int, h: Int) {
        log.debug("Window resized {}", Vector2(w.toFloat(), h.toFloat()))
        val cam = getKoin().get<PlayerCamera>()
    }

    override fun pause() {
        log.debug("Window paused")
    }

    override fun resume() {
        log.debug("Window resumed")
    }

    override fun hide() {
        log.debug("Window hidden")
    }
}
