package cz.pacmanplus.game.core.entity

import com.artemis.World
import com.badlogic.gdx.math.Vector2
import cz.pacmanplus.game.core.components.common.NameComponent
import cz.pacmanplus.game.core.components.control.PlayerInputComponent
import cz.pacmanplus.game.core.components.physics.*
import ktx.artemis.entity
import org.koin.mp.KoinPlatform.getKoin
import org.slf4j.LoggerFactory
import kotlin.random.Random

object LevelFactory {

    val log = LoggerFactory.getLogger("LevelFactory")

    fun createLevelDebug() {

        ItemFactory.createCoin(200f, 232f)
        ItemFactory.createCoin(232f, 232f)
        ItemFactory.createCoin(264f, 232f)
        (0 until 10).forEach {  i ->
            (0 until 10).forEach {  j ->
                if(i % 2==0 && j%2 == 0){
                    if(Random.nextBoolean()){
                        ObstacleFactory.createWallBox(i*32f+16, j*32f+16)
                    } else {
                        if(Random.nextBoolean()){
                            ObstacleFactory.createWallBox(i*32f+16, j*32f+16)
                        } else {
                            ObstacleFactory.createPushableBox(i*32f+16, j*32f+16)
                        }
                    }

                } else{
                    ObstacleFactory.createFlowArea(i*32f+16, j*32f+16, Vector2(1f, 0f))
                }

            }
        }

        CharacterFactory.createPlayer(0f, 0f)
        ObstacleFactory.createDamageRectArea(264f, 232f)







        log.info("Map created")
    }
}
