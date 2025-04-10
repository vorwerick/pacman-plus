package cz.pacmanplus.utils

import com.artemis.BaseSystem
import com.artemis.World
import cz.pacmanplus.game.core.components.common.BaseComponent
import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.LoggerFactory


fun BaseSystem.delete(id: Int, reason: String? = null) {
    val log = LoggerFactory.getLogger(this::class.simpleName)
    val world = getKoin().get<World>()
    val entity = world.getEntity(id)

    world.delete(id)
    log.info("Deleted entity ${entity.getComponent(BaseComponent::class.java).name} id $id [$reason]")
}
