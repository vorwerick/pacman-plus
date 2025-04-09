package cz.pacmanplus.game.core.systems

import com.artemis.Aspect
import com.artemis.BaseSystem
import com.artemis.Entity
import com.artemis.EntitySystem
import com.artemis.systems.EntityProcessingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import cz.pacmanplus.game.core.components.control.ComputerComponent
import cz.pacmanplus.game.core.components.control.InputComponent
import cz.pacmanplus.game.core.components.control.PlayerComponent

//find computer component with input component
class ComputerInputSystem :
    EntityProcessingSystem(Aspect.all(InputComponent::class.java, ComputerComponent::class.java)) {
    override fun process(computer: Entity?) {
        computer?.let {
            val inputComponent = computer.getComponent(InputComponent::class.java)

            val moveRight =
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) 1 else 0
            val moveLeft =
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) -1 else 0
            val moveDown =
                if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) -1 else 0
            val moveUp = if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) 1 else 0


            val horizontal = (moveRight + moveLeft)
            val vertical = (moveUp + moveDown)

            inputComponent.dir = Vector2.Zero
            inputComponent.dir.x = horizontal.toFloat()
            inputComponent.dir.y = vertical.toFloat()

            inputComponent.horizontal = horizontal != 0
            inputComponent.vertical = vertical != 0


            if (inputComponent.vertical && inputComponent.horizontal) {
                if (inputComponent.lastDirBeforeBoth == 1) { //was horizontal so swap to vertical
                    inputComponent.dir.x = 0f
                    inputComponent.lastDirBeforeBoth = 1
                } else if (inputComponent.lastDirBeforeBoth == 2) {
                    inputComponent.dir.y = 0f
                    inputComponent.lastDirBeforeBoth = 2
                }

            } else {
                inputComponent.lastDirBeforeBoth = 0
                if (horizontal != 0) {
                    inputComponent.lastDirBeforeBoth = 1
                }
                if (vertical != 0) {
                    inputComponent.lastDirBeforeBoth = 2
                }

            }
            inputComponent.useBomb = false
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                world.getSystem(PlayerSystem::class.java).createBomb()
                inputComponent.useBomb = true
            }
        }
    }


}
