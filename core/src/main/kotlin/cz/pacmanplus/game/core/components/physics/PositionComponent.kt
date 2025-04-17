package cz.pacmanplus.game.core.components.physics

import com.artemis.Component
import com.badlogic.gdx.math.Vector2

class PositionComponent : Component() {
    var x = 0f
    var y = 0f

    fun getCenterPosition(rectangleCollisionComponent: RectangleCollisionComponent?): Vector2 {
        if (rectangleCollisionComponent == null) {
            return Vector2(x, y)
        }
        return Vector2(x + (rectangleCollisionComponent.width / 2), y + (rectangleCollisionComponent.height / 2))
    }

    fun getCenterPosition(circleCollisionComponent: CircleCollisionComponent?): Vector2 {
        return Vector2(x, y)
    }

    override fun toString(): String {
        return "POSITION x=$x, y=$y"
    }
}
