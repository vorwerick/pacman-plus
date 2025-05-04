package cz.pacmanplus.editor

import cz.pacmanplus.editor.entities.Level
import cz.pacmanplus.editor.entities.Object

interface EditorEvents {

    fun moveCamera(x: Int, y: Int)
    fun resetCamera()
    fun createLayer()
    fun removeLayer()
    fun changeLayerVisibility()
    fun getLayerId(): Int
    fun setCurrentLayerId(layerId: Int)
    fun addObject(layerId: Int, objectId: Int, x: Int, y: Int)
    fun getObject(): Object
    fun removeObject(layerId: Int, objectId: Int)
    fun removeSelectedObject()
    fun moveObject(layerId: Int, objectId: Int, newX: Int, newY: Int)
    fun new(width: Int, height: Int, cellSize: Int, offsetX: Int, offsetY: Int)
    fun load(filename: String)
    fun save(filename: String)
    fun exit()
    fun getCatalogue(): Map<Int, Object>

    fun getLevel(): Level
    fun fromCatalogue(objectId: Int)
    fun selectObject(x: Int, y: Int): Boolean

}
