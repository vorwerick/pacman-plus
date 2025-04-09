package cz.pacmanplus.game.core.systems.update

import com.artemis.Aspect
import com.artemis.BaseSystem
import com.artemis.World
import com.artemis.systems.IteratingSystem
import com.artemis.utils.IntBag
import com.badlogic.gdx.Gdx
import cz.pacmanplus.game.core.components.attributes.DelayComponent
import cz.pacmanplus.game.core.components.attributes.LifespanComponent
import org.slf4j.LoggerFactory

class ClockSystem : IteratingSystem(Aspect.one(DelayComponent::class.java)) {
    val log = LoggerFactory.getLogger("FrameTickSystem")
    override fun process(id: Int) {
        val entity = world.getEntity(id)
        entity.getComponent(DelayComponent::class.java)?.tick(Gdx.graphics.deltaTime)
    }


}

fun World.findLifespanEntities(): IntBag {
    return this.aspectSubscriptionManager.get(
        Aspect.all(LifespanComponent::class.java)
    ).entities ?: IntBag()
}
