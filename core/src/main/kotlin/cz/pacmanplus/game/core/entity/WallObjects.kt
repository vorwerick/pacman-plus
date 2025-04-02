package cz.pacmanplus.game.core.entity

interface WallObjects {

    fun bedrock(x: Float, y: Float) // unpassable
    fun wall(x: Float, y: Float, hitPoints: Int) // wall
    fun box(x: Float, y: Float, hitPoints: Int) // pushable wall
    fun chest(x: Float, y: Float, hitPoints: Int) // pick-able wall
    fun stone(x: Float, y: Float) // pushable rolling wall
    fun gate(x: Float, y: Float, enabled: Boolean) // switchable door
    fun door(x: Float, y: Float) // unlockable door
    fun bomb(x: Float, y: Float) // explosion creator

}
