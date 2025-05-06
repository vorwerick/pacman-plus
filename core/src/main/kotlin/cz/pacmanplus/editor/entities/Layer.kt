package cz.pacmanplus.editor.entities

class Layer(val name: String, width: Int, height: Int) {

    val grid: Array<Array<Object?>> = Array(width) { Array(height) { null } }

    fun deserialize(): String {
        val sb = StringBuilder()
        for (x in grid.indices) {
            for (y in grid[x].indices) {
                sb.append(grid[x][y]?.toString() ?: "null")
                if (y < grid[x].size - 1) sb.append(",")
            }
            if (x < grid.size - 1) sb.append("\n")
        }
        return sb.toString()
    }

    companion object {
        fun serialize(source: String): Layer {
            val rows = source.split("\n")
            val width = rows.size
            val height = if (width > 0) rows[0].split(",").size else 0
            val layer = Layer("Deserialized", width, height)

            rows.forEachIndexed { x, row ->
                val cells = row.split(",")
                cells.forEachIndexed { y, cell ->
                    layer.grid[x][y] = if (cell == "null") null else cell as Object
                }
            }
            return layer
        }

    }
}
