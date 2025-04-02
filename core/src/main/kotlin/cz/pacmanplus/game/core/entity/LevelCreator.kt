package cz.pacmanplus.game.core.entity

import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.LoggerFactory
import kotlin.random.Random

class LevelCreator {

    val log = LoggerFactory.getLogger("LevelCreator")

    fun createLevelDebug(width: Int, height: Int) {
        val wallCreator: WallObjects = getKoin().get()
        val itemCreator: ItemObjects = getKoin().get()
        val floorObjects: FloorObjects = getKoin().get()
        val characterCreator: CharacterCreator = getKoin().get()

        var last = false
        (0 until width).forEach { i ->
            (0 until height).forEach { j ->
                if (i == 1 && j == 1) {
                    floorObjects.start(64f, 64f)
                    characterCreator.player(64f, 64f)
                } else if (i == 1 && j == 2) {

                } else if (i == 2 && j == 1) {

                } else {
                    if (i == 0 || i == width - 1 || j == 0 || j == height - 1) {
                        wallCreator.bedrock(i * 32f + 16, j * 32f + 16)
                    } else {
                        if (i % 2 == 0 && j % 2 == 0) {
                            wallCreator.wall(i * 32f + 16, j * 32f + 16, 3)


                        } else {
                            if (Random.nextInt() % 5 == 0 && !last) {
                                wallCreator.box(i * 32f + 16, j * 32f + 16, 1)
                                last = true
                            } else {
                                last = false
                                if(Random.nextInt() % 15 == 0) {
                                    if(Random.nextBoolean()){
                                        wallCreator.chest(i * 32f + 16, j * 32f + 16, 1)
                                    } else {
                                        wallCreator.gate(i * 32f + 16, j * 32f + 16, false)
                                    }
                                } else {
                                    if(Random.nextInt() % 15 == 0) {
                                        floorObjects.trigger(i * 32f + 16, j * 32f + 16, listOf(0))
                                    } else {
                                        itemCreator.score(i * 32f + 16 + 16, j * 32f + 16 + 16)
                                    }

                                }
                            }
                            //  ObstacleFactory.createFlowArea(i*32f+16, j*32f+16, Vector2(1f, 0f))
                        }
                    }
                }


            }
        }


        log.info("Map created")
    }
}
