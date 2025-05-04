package cz.pacmanplus.editor

import cz.pacmanplus.editor.entities.Object
import cz.pacmanplus.utils.IterableGrid

class EditorData{

    var cameraX = 0f
    var cameraY = 0f
    var gridWidth = 0
    var gridHeight = 0
    var cellSize = 0
    var offsetX = 0
    var offsetY = 0
    var selectedObjectId = null

    val layers = mutableListOf<IterableGrid>()




}