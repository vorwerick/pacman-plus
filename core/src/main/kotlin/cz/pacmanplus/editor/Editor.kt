package cz.pacmanplus.editor

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import cz.pacmanplus.editor.entities.Layer
import cz.pacmanplus.editor.entities.Level
import cz.pacmanplus.editor.entities.Object
import org.slf4j.LoggerFactory

class Editor: EditorEvents {

    private val log = LoggerFactory.getLogger("Editor")
    private val data: EditorData = EditorData()
    private var currentLevel: Level? = null
    private var currentLayerId: Int = 0
    private var selectedObjectId: Int? = null
    private var selectedObject: Object? = null
    private val catalogue: MutableMap<Int, Object> = mutableMapOf()

    init {
        // Initialize with a default level
        new(32, 32, 32, 0, 0)

        // Initialize catalogue with some sample objects
        initializeCatalogue()
    }

    private fun initializeCatalogue() {
        // Add some sample objects to the catalogue
        // These would typically be loaded from a configuration file
        catalogue[1] = Object(1, 0, 0, 1) // Wall
        catalogue[2] = Object(2, 0, 0, 2) // Floor
        catalogue[3] = Object(3, 0, 0, 3) // Item
        catalogue[4] = Object(4, 0, 0, 4) // Character
    }

    override fun moveCamera(x: Int, y: Int) {
        data.cameraX += x
        data.cameraY += y
        log.debug("Camera moved to (${data.cameraX}, ${data.cameraY})")
    }

    override fun resetCamera() {
        data.cameraX = 0f
        data.cameraY = 0f
        log.debug("Camera reset to (0, 0)")
    }

    override fun new(width: Int, height: Int, cellSize: Int, offsetX: Int, offsetY: Int) {
        currentLevel = Level(width, height, cellSize, offsetX, offsetY)
        // Create a default layer
        createLayer()
        log.debug("New level created with dimensions ${width}x${height}")
    }

    override fun createLayer() {
        val level = currentLevel ?: return
        val layerName = "Layer ${level.layers.size + 1}"
        val layer = Layer(layerName, level.width, level.height)
        level.layers.add(layer)
        currentLayerId = level.layers.size - 1
        log.debug("Created new layer: $layerName")
    }

    override fun removeLayer() {
        val level = currentLevel ?: return
        if (level.layers.size > 1 && currentLayerId >= 0 && currentLayerId < level.layers.size) {
            val removedLayer = level.layers.removeAt(currentLayerId)
            if (currentLayerId >= level.layers.size) {
                currentLayerId = level.layers.size - 1
            }
            log.debug("Removed layer: ${removedLayer.name}")
        } else {
            log.debug("Cannot remove the only layer")
        }
    }

    override fun changeLayerVisibility() {
        // This would typically toggle a visibility flag in the layer
        // For now, we'll just log it
        log.debug("Layer visibility changed for layer $currentLayerId")
    }

    override fun getLayerId(): Int {
        log.debug("Current layer ID: $currentLayerId")
        return currentLayerId
    }

    override fun setCurrentLayerId(layerId: Int) {
        val level = currentLevel ?: return
        if (layerId >= 0 && layerId < level.layers.size) {
            currentLayerId = layerId
            log.debug("Set current layer ID to $currentLayerId")
        } else {
            log.debug("Invalid layer ID: $layerId")
        }
    }

    override fun selectObject(x: Int, y: Int): Boolean {
        val level = currentLevel ?: return false
        if (currentLayerId >= 0 && currentLayerId < level.layers.size) {
            val layer = level.layers[currentLayerId]
            if (x >= 0 && x < level.width && y >= 0 && y < level.height) {
                selectedObject = layer.grid[x][y]
                if (selectedObject != null) {
                    log.debug("Selected object ${selectedObject?.id} at position ($x, $y)")
                    return true
                }
            }
        }
        selectedObject = null
        log.debug("No object selected at position ($x, $y)")
        return false
    }

    override fun removeSelectedObject() {
        val selectedObj = selectedObject ?: return
        val level = currentLevel ?: return
        if (currentLayerId >= 0 && currentLayerId < level.layers.size) {
            val layer = level.layers[currentLayerId]
            // Find the object in the grid
            for (x in 0 until level.width) {
                for (y in 0 until level.height) {
                    val obj = layer.grid[x][y]
                    if (obj != null && obj.id == selectedObj.id && obj.x == selectedObj.x && obj.y == selectedObj.y) {
                        layer.grid[x][y] = null
                        selectedObject = null
                        log.debug("Removed selected object ${selectedObj.id} from position (${selectedObj.x}, ${selectedObj.y})")
                        return
                    }
                }
            }
        }
    }

    override fun addObject(layerId: Int, objectId: Int, x: Int, y: Int) {
        val level = currentLevel ?: return
        if (layerId >= 0 && layerId < level.layers.size) {
            val layer = level.layers[layerId]
            if (x >= 0 && x < level.width && y >= 0 && y < level.height) {
                val obj = Object(objectId, x, y, catalogue[objectId]?.objectType ?: 0)
                layer.grid[x][y] = obj
                log.debug("Added object $objectId at position ($x, $y) on layer $layerId")
            } else {
                log.debug("Position ($x, $y) is out of bounds")
            }
        } else {
            log.debug("Layer $layerId does not exist")
        }
    }

    override fun getObject(): Object {
        val selectedId = selectedObjectId ?: return Object(0, 0, 0, 0)
        return catalogue[selectedId] ?: Object(0, 0, 0, 0)
    }

    override fun removeObject(layerId: Int, objectId: Int) {
        val level = currentLevel ?: return
        if (layerId >= 0 && layerId < level.layers.size) {
            val layer = level.layers[layerId]
            // Find the object with the given ID
            for (x in 0 until level.width) {
                for (y in 0 until level.height) {
                    val obj = layer.grid[x][y]
                    if (obj != null && obj.id == objectId) {
                        layer.grid[x][y] = null
                        log.debug("Removed object $objectId from position ($x, $y) on layer $layerId")
                        return
                    }
                }
            }
            log.debug("Object $objectId not found on layer $layerId")
        } else {
            log.debug("Layer $layerId does not exist")
        }
    }

    override fun moveObject(layerId: Int, objectId: Int, newX: Int, newY: Int) {
        val level = currentLevel ?: return
        if (layerId >= 0 && layerId < level.layers.size) {
            val layer = level.layers[layerId]
            if (newX >= 0 && newX < level.width && newY >= 0 && newY < level.height) {
                // Find the object with the given ID
                for (x in 0 until level.width) {
                    for (y in 0 until level.height) {
                        val obj = layer.grid[x][y]
                        if (obj != null && obj.id == objectId) {
                            // Remove from old position
                            layer.grid[x][y] = null
                            // Add to new position
                            layer.grid[newX][newY] = obj.copy(x = newX, y = newY)
                            log.debug("Moved object $objectId from ($x, $y) to ($newX, $newY) on layer $layerId")
                            return
                        }
                    }
                }
                log.debug("Object $objectId not found on layer $layerId")
            } else {
                log.debug("Position ($newX, $newY) is out of bounds")
            }
        } else {
            log.debug("Layer $layerId does not exist")
        }
    }

    override fun load(filename: String) {
        try {
            val file = Gdx.files.local(filename)
            if (file.exists()) {
                val bytes = file.readBytes()
                val level = Level.serialize(bytes)
                if (level != null) {
                    currentLevel = level
                    currentLayerId = 0
                    log.debug("Loaded level from $filename")
                } else {
                    log.error("Failed to parse level from $filename")
                }
            } else {
                log.error("File $filename does not exist")
            }
        } catch (e: Exception) {
            log.error("Error loading level from $filename", e)
        }
    }

    override fun save(filename: String) {
        val level = currentLevel ?: return
        try {
            val file = Gdx.files.local(filename)
            val bytes = level.deserialize()
            file.writeBytes(bytes, false)
            log.debug("Saved level to $filename")
        } catch (e: Exception) {
            log.error("Error saving level to $filename", e)
        }
    }

    override fun exit() {
        // This would typically handle any cleanup before exiting
        log.debug("Editor exiting")
    }

    override fun getCatalogue(): Map<Int, Object> {
        return catalogue
    }

    override fun getLevel(): Level {
        return currentLevel ?: Level(0, 0, 0, 0, 0)
    }

    override fun fromCatalogue(objectId: Int) {
        selectedObjectId = objectId
        log.debug("Selected object $objectId from catalogue")
    }
}
