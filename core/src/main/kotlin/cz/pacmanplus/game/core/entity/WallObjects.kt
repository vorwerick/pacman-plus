package cz.pacmanplus.game.core.entity

import com.badlogic.gdx.math.Vector2

interface WallObjects {

    fun bedrock(x: Float, y: Float) // unpassable
    fun wall(x: Float, y: Float, hitPoints: Int) // wall
    fun box(x: Float, y: Float, hitPoints: Int) // pushable wall
    fun turret(x: Float, y: Float, hitPoints: Int, direction: Vector2) // pushable wall
    fun generator(x: Float, y: Float, hitPoints: Int) // pushable wall
    fun chest(x: Float, y: Float, hitPoints: Int, keyType: Int) // pick-able wall
    fun stone(x: Float, y: Float) // pushable rolling wall
    fun gate(x: Float, y: Float, group: Int) // switchable door
    fun door(x: Float, y: Float, keyType: Int) // unlockable door
    fun bomb(x: Float, y: Float) // explosion creator

}
