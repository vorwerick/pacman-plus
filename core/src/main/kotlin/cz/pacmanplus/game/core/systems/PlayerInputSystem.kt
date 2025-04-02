package cz.pacmanplus.game.core.systems

import com.artemis.Aspect
import com.artemis.BaseEntitySystem
import com.artemis.BaseSystem
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import cz.pacmanplus.game.core.components.control.PlayerInputComponent
import cz.pacmanplus.game.core.components.physics.MovementComponent
import ktx.math.times

class PlayerInputSystem : BaseSystem() {



    override fun processSystem() {
        val player = world.findPlayer()
        if(player != null) {
            val playerInputComponent = player.getComponent(PlayerInputComponent::class.java)

            val moveRight = if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) 1 else 0
            val moveLeft = if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) -1 else 0
            val moveDown = if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) -1 else 0
            val moveUp = if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) 1 else 0


            val horizontal = (moveRight + moveLeft)
            val vertical = (moveUp + moveDown)

            playerInputComponent.dir = Vector2.Zero
            playerInputComponent.dir.x = horizontal.toFloat()
            playerInputComponent.dir.y = vertical.toFloat()

            playerInputComponent.horizontal = horizontal != 0
            playerInputComponent.vertical = vertical != 0


            if (playerInputComponent.vertical && playerInputComponent.horizontal) {
                if (playerInputComponent.lastDirBeforeBoth == 1) { //was horizontal so swap to vertical
                    playerInputComponent.dir.x = 0f
                    playerInputComponent.lastDirBeforeBoth = 1
                } else if (playerInputComponent.lastDirBeforeBoth == 2) {
                    playerInputComponent.dir.y = 0f
                    playerInputComponent.lastDirBeforeBoth = 2
                }

            } else {
                playerInputComponent.lastDirBeforeBoth = 0
                if (horizontal != 0) {
                    playerInputComponent.lastDirBeforeBoth = 1
                }
                if (vertical != 0) {
                    playerInputComponent.lastDirBeforeBoth = 2
                }

            }
            playerInputComponent.useBomb = false
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                world.getSystem(PlayerSystem::class.java).createBomb()
                playerInputComponent.useBomb = true
            }
        }


    }


}
