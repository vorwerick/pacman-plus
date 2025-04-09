package cz.pacmanplus.game.core.systems

import com.artemis.Aspect
import com.artemis.Entity
import com.artemis.systems.EntityProcessingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import cz.pacmanplus.game.core.components.control.InputComponent
import cz.pacmanplus.game.core.components.control.PlayerComponent
import cz.pacmanplus.game.core.components.control.computer.FindPlayerComponent

//find human player component with input component
class PlayerCacheSystem :
    EntityProcessingSystem(Aspect.all(FindPlayerComponent::class.java, PlayerComponent::class.java)) {

    var playerEntity: Entity? = null
    override fun process(player: Entity?) {
        player?.let {
            playerEntity = it
        }
    }

    fun clear() {
        playerEntity = null
    }


}
