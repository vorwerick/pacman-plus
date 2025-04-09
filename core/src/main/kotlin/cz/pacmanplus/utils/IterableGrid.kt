package cz.pacmanplus.utils

class Position(val data: Int, val x: Int, val y: Int, val sizeX: Int, val sizeY: Int) {

}


class IterableGrid(val width: Int, val height: Int) {


    private var grid = Array<Array<Int?>>(width) { x ->
        Array<Int?>(height) { y ->
            null
        }
    }

    private val iterable = mutableMapOf<Int, Position>()

    fun add(x: Int, y: Int, sizeX: Int = 1, sizeY: Int = 1, value: Int): Int {
        (0 until sizeX).flatMap() { sx ->
            (0 until sizeY).map() { sy ->
                grid[x + sx][y + sy] = value
            }
        }

        iterable[value] = Position(value, x, y, sizeX, sizeY)
        return value
    }

    fun getById(id: Int): Int? {
        return iterable[id]?.data
    }

    fun getByPosition(x: Int, y: Int): Int? {
        return iterable.get(grid.getOrNull(x)?.getOrNull(y))?.data
    }

    fun getByPositionInRect(x: Int, y: Int, width: Int, height: Int): List<Int?> {
        return (0 until width - 1).flatMap() { sx ->
            (0 until height - 1).map() { sy ->
                iterable.get(grid.getOrNull(x)?.getOrNull(y))?.data
            }
        }.toList()
    }

    fun removeByPosition(x: Int, y: Int) {
        val id = grid.getOrNull(x)?.getOrNull(y)
        iterable.get(id)?.let {
            (0 until it.sizeX).flatMap() { sx ->
                (0 until it.sizeY).map() { sy ->
                    grid[x + sx][y + sy] = null
                }
            }
            iterable.remove(id)
        }
    }

    fun removeById(id: Int) {
        val position = iterable[id]!!
        removeByPosition(position.x, position.y)
    }


    fun forEachIterable(action: (x: Int, y: Int, value: Int) -> Unit) {
        iterable.forEach { (id, position) ->
            action(position.x, position.y, position.data)
        }
    }

    fun forEachGrid(action: (x: Int, y: Int, value: Int?) -> Unit) {
        for(x in 0 until width) {
            for(y in 0 until height) {
                action(x, y, grid[x][y])
            }
        }
    }

    fun clear() {
        grid = Array<Array<Int?>>(width) { x ->
            Array<Int?>(height) { y ->
                null
            }
        }
    }

}
