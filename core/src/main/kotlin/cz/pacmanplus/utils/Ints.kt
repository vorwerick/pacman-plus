package cz.pacmanplus.utils

data class Ints(val a: Int, val b: Int) {
}

fun Int.sign(): Int {
    if(this> 0){
        return 1
    }
    if(this < 0){
        return -1
    }
    return 0
}
