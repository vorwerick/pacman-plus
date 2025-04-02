package cz.pacmanplus.game.core.entity

import com.badlogic.gdx.math.Vector2

interface FloorObjects {

    fun floor(x: Float, y: Float) // base floor
    fun switch(x: Float, y: Float, affectedIds: List<Int>) // switchable button
    fun trigger(x: Float, y: Float, affectedIds: List<Int>) // one-way button
    fun enemySpawner(x: Float, y: Float) // enemy spawner
    fun enemyGate(x: Float, y: Float) // enemy portal
    fun teleport(x: Float, y: Float, targetId: Int) // teleport from A to B
    fun lava(x: Float, y: Float) // persistent damage area
    fun explosion(x: Float, y: Float) // short-term damage area
    fun trapdoor(x: Float, y: Float) // switchable damage area
    fun ventilator(x: Float, y: Float, direction: Vector2, force: Int) // force area
    fun void(x: Float, y: Float) // no path
    fun start(x: Float, y: Float) // player default position
    fun finish(x: Float, y: Float) // finish trigger area
    fun boxSpawner(x: Float, y: Float) // box spawner point

}
