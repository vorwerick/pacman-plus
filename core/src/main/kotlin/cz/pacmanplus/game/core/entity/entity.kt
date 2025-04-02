package cz.pacmanplus.game.core.entity

import com.artemis.EntityEdit
import com.artemis.World
import cz.pacmanplus.game.core.components.common.BaseComponent
import org.koin.mp.KoinPlatform.getKoin

fun newEntity(name: String): EntityEdit {
    val world: World = getKoin().get<World>()
    val entity = world.createEntity()

    return entity.edit().apply {
        val baseComponent = this.create(BaseComponent::class.java)
        baseComponent.name = name
        baseComponent.alive = true
    }
}


