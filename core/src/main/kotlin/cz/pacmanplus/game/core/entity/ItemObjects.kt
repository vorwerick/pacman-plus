package cz.pacmanplus.game.core.entity

interface ItemObjects {

    fun life(x: Float, y: Float) // 1up
    fun shield(x: Float, y: Float) // temp invulnerability
    fun score(x: Float, y: Float) // base score point
    fun key(x: Float, y: Float) // key
    fun dash(x: Float, y: Float) // blink
    fun sprint(x: Float, y: Float) // temp speed up

}
