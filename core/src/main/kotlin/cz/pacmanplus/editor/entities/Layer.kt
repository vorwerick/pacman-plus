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
            return Layer("", 0, 0)
        }

    }
}
