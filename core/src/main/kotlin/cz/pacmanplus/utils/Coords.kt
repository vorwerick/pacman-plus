package cz.pacmanplus.utils

data class Coords(var x: Int, var y: Int){
    override fun toString(): String {
        return "($x, $y)"
    }
}
