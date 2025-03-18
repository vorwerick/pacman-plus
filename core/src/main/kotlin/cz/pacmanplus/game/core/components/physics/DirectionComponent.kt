package cz.pacmanplus.game.core.components.physics

import com.artemis.Component
import com.badlogic.gdx.math.Vector2


class DirectionComponent : Component(){
     var x = 0f
     var y = 0f

     override fun toString(): String {
         val vec = Vector2(x, y).nor()
         return "$vec ${vec.angleDeg()} deg"
     }
 }
