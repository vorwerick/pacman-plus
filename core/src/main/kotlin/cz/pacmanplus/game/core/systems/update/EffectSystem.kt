package cz.pacmanplus.game.core.systems.update

import com.artemis.Aspect
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import cz.pacmanplus.game.core.components.attributes.TimeComponent
import cz.pacmanplus.game.core.components.graphics.DrawableEffectComponent
import org.slf4j.LoggerFactory

class EffectSystem : IteratingSystem(Aspect.one(DrawableEffectComponent::class.java)) {
    val log = LoggerFactory.getLogger("EffectSystem")
    override fun process(id: Int) {
        val entity = world.getEntity(id)
        entity.getComponent(DrawableEffectComponent::class.java)?.tick(Gdx.graphics.deltaTime)
    }


}
