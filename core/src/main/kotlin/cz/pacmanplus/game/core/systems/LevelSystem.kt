package cz.pacmanplus.game.core.systems

import com.artemis.Aspect
import com.artemis.Entity
import com.artemis.systems.EntityProcessingSystem
import cz.pacmanplus.game.GameState
import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.LoggerFactory

class LevelSystem :
    EntityProcessingSystem(
        Aspect.all(),
    ) {
    val log = LoggerFactory.getLogger("LevelSystem")


    override fun process(e: Entity?) {

        val gameState = getKoin().get<GameState>()
        if (gameState.paused) {
            return
        }



    }


}
