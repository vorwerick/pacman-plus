package cz.pacmanplus.game.core.systems

import com.artemis.Aspect
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.math.Vector3
import cz.pacmanplus.game.PlayerCamera
import cz.pacmanplus.game.core.components.control.PlayerInputComponent
import cz.pacmanplus.game.core.components.physics.PositionComponent
import ktx.graphics.lerpTo
import org.koin.java.KoinJavaComponent.getKoin

class PlayerSystem : IteratingSystem(Aspect.all(PlayerInputComponent::class.java)) {
    override fun process(id: Int) {
        val player = getWorld().getEntity(id)
        val position = player.getComponent(PositionComponent::class.java)
        val cam = getKoin().get<PlayerCamera>()

        cam.camera.position.set(Vector3(position.x, position.y, 0f))
        cam.camera.update()
    }


}
