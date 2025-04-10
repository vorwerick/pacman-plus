package cz.pacmanplus.utils

object AStar {

    //astar method for finding path manhattan distance
    fun findPathNullable(startX: Int, startY: Int, endX: Int, endY: Int, map: Array<Array<Int?>>): List<Pair<Int, Int>> {
        val openList = mutableListOf<Pair<Int, Int>>()
        val closedList = mutableListOf<Pair<Int, Int>>()
        val path = mutableListOf<Pair<Int, Int>>()
        val gScore = hashMapOf<Pair<Int, Int>, Int>()
        val fScore = hashMapOf<Pair<Int, Int>, Int>()
        val cameFrom = hashMapOf<Pair<Int, Int>, Pair<Int, Int>>()
        val start = Pair(startX, startY)
        val end = Pair(endX, endY)
        openList.add(start)
        gScore[start] = 0
        fScore[start] = heuristicCostEstimate(start, end)

        while (openList.isNotEmpty()) {
            var current = Pair(0, 0)
            var currentF = Int.MAX_VALUE
            openList.forEach {
                if (fScore[it]!! < currentF) {
                    current = it
                    currentF = fScore[it]!!
                }
            }

            if (current == end) {
                path.add(current)
                while (cameFrom.containsKey(current)) {
                    current = cameFrom[current]!!
                    path.add(current)
                }
                path.reverse()
                return path
            }

            openList.remove(current)
            closedList.add(current)

            val neighbors = getNeighbors(current, map)
            neighbors.forEach {
                if (closedList.contains(it)) {
                    return@forEach
                }
                val tentativeGScore = gScore[current]!! + getWeight(it, map)
                if (!openList.contains(it) || tentativeGScore < gScore[it]!!) {
                    cameFrom[it] = current
                    gScore[it] = tentativeGScore
                    fScore[it] = gScore[it]!! + heuristicCostEstimate(it, end)

                    if (!openList.contains(it)) {
                        openList.add(it)
                    }
                }
            }
        }
        return path
    }

    //astar method for finding path manhattan distance


    private fun getWeight(position: Pair<Int, Int>, map: Array<Array<Int?>>): Int {
        val pos = map[position.first][position.second]
        if (pos != null) {
            return -1
        }

        return 0
    }

    //get neighbors of a tile in 8 directions
    private fun getNeighborsDiagonal(current: Pair<Int, Int>, map: Array<Array<Int?>>): MutableList<Pair<Int, Int>> {
        val neighbors = mutableListOf<Pair<Int, Int>>()
        val x = current.first
        val y = current.second
        if (x > 0) {
            if (map[x - 1][y] != null) {
                neighbors.add(Pair(x - 1, y))
            }
        }
        if (x < map.size - 1) {
            if (map[x + 1][y] != null) {
                neighbors.add(Pair(x + 1, y))
            }
        }
        if (y > 0) {
            if (map[x][y - 1] != null) {
                neighbors.add(Pair(x, y - 1))
            }
        }
        if (y < map.size - 1) {
            if (map[x][y + 1] != null) {
                neighbors.add(Pair(x, y + 1))
            }
        }

        return neighbors
    }

    //get neighbors of a tile
    private fun getNeighbors(current: Pair<Int, Int>, map: Array<Array<Int?>>): MutableList<Pair<Int, Int>> {
        val neighbors = mutableListOf<Pair<Int, Int>>()
        val x = current.first
        val y = current.second
        if (x > 0) {
            if (map[x - 1][y] == null) {
                neighbors.add(Pair(x - 1, y))
            }
        }
        if (x < map.size - 1) {
            if (map[x + 1][y]  == null) {
                neighbors.add(Pair(x + 1, y))
            }
        }
        if (y > 0) {
            if (map[x][y - 1]  == null) {
                neighbors.add(Pair(x, y - 1))
            }
        }
        if (y < map.size - 1) {
            if (map[x][y + 1]  == null) {
                neighbors.add(Pair(x, y + 1))
            }
        }
        return neighbors
    }

    //heuristic cost estimate
    private fun heuristicCostEstimate2(start: Pair<Int, Int>, end: Pair<Int, Int>): Int {
        return start.first - end.first + start.second - end.second
    }

    // diagonal heuteristic cost estimate
    private fun heuristicCostEstimate3(start: Pair<Int, Int>, end: Pair<Int, Int>): Int {
        return Math.max(Math.abs(start.first - end.first), Math.abs(start.second - end.second))
    }

    //euclidean heuristic cost estimate
    private fun heuristicCostEstimate(start: Pair<Int, Int>, end: Pair<Int, Int>): Int {
        return Math.sqrt(
            Math.pow(
                (start.first - end.first).toDouble(),
                2.0
            ) + Math.pow((start.second - end.second).toDouble(), 2.0)
        ).toInt()
    }
}

fun IterableGrid.findPath(startX: Int, startY: Int, endX: Int, endY: Int): List<Pair<Int, Int>> {
    return AStar.findPathNullable(startX, startY, endX, endY, getGrid())
}
