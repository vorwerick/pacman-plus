package cz.pacmanplus.game.core.systems.update

import com.artemis.Aspect
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import cz.pacmanplus.game.core.components.attributes.TimeComponent
import org.slf4j.LoggerFactory

class TimerSystem : IteratingSystem(Aspect.one(TimeComponent::class.java)) {
    val log = LoggerFactory.getLogger("TimerSystem")
    override fun process(id: Int) {
        val entity = world.getEntity(id)
        entity.getComponent(TimeComponent::class.java)?.tick(Gdx.graphics.deltaTime)
    }


}
