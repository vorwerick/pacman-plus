package cz.pacmanplus.game.core.systems

import com.artemis.BaseSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import cz.pacmanplus.game.PlayerCamera
import cz.pacmanplus.game.core.entity.CharacterCreator
import cz.pacmanplus.game.core.entity.WallObjects
import cz.pacmanplus.game.core.entity.creator.WallObjectsCreator
import cz.pacmanplus.game.core.systems.grid.WallGridSystem
import org.koin.java.KoinJavaComponent.getKoin

class LevelEditorSystem : BaseSystem() {

    var selected: Int? = null
    var mousePos = Vector3()
    var mouseTile = Vector2()

    var buildType: Int? = null


    override fun processSystem() {

        val camera = getKoin().get<PlayerCamera>()
        mousePos = camera.camera.unproject(Vector3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f))

        mouseTile = Vector2(((mousePos.x + 0) / 32).toInt().toFloat(), ((mousePos.y + 0) / 32).toInt().toFloat())
        val system = world.getSystem(WallGridSystem::class.java)

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            val system = world.getSystem(WallGridSystem::class.java)
            selected = system.wallGrid.getByPosition(mouseTile.x.toInt(), mouseTile.y.toInt())
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            val system = world.getSystem(WallGridSystem::class.java)
            system.wallGrid.getByPosition(mouseTile.x.toInt(), mouseTile.y.toInt())?.let {
                world.delete(it)
            }
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
            selected?.let {
                world.delete(it)
            }
            system.wallGrid.getByPosition(mouseTile.x.toInt(), mouseTile.y.toInt())?.let {
                world.delete(it)
            }

        }

        processCreateWallObjects()
        processCreateItems()
        processCreatePlayer()
    }

    private fun processCreateItems() {


    }

    private fun processCreatePlayer() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)){
            val player = world.findPlayer()
            if(player == null){
                CharacterCreator().player(mouseTile.x * 32, mouseTile.y * 32)
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            val player = world.findPlayer()
            if(player != null){
                player!!.deleteFromWorld()
            }
        }

    }


    private fun create(onCreate: () -> Unit) {
        val system = world.getSystem(WallGridSystem::class.java)

        val isEmpty = system.wallGrid.getByPosition(mouseTile.x.toInt(), mouseTile.y.toInt()) == null
        if (isEmpty) {


            onCreate()
            println("CREATED")
        }

    }

    private fun processCreateWallObjects(){
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {

            create {
                val creator = getKoin().get<WallObjects>()
                creator.bedrock(mouseTile.x * 32, mouseTile.y * 32)
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {

            create {
                val creator = getKoin().get<WallObjects>()
                creator.wall(mouseTile.x * 32, mouseTile.y * 32, 3)
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {

            create {
                val creator = getKoin().get<WallObjects>()
                creator.box(mouseTile.x * 32, mouseTile.y * 32, 3)
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.R)) {

            create {
                val creator = getKoin().get<WallObjects>()
                creator.chest(mouseTile.x * 32, mouseTile.y * 32, 2, 0)
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.T)) {

            create {
                val creator = getKoin().get<WallObjects>()
                creator.turret(mouseTile.x * 32, mouseTile.y * 32, 2, Vector2(1f, 0f))
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {

            create {
                val creator = getKoin().get<WallObjects>()
                creator.stone(mouseTile.x * 32, mouseTile.y * 32)
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.U)) {

            create {
                val creator = getKoin().get<WallObjects>()
                creator.door(mouseTile.x * 32, mouseTile.y * 32, 0)
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.I)) {

            create {
                val creator = getKoin().get<WallObjects>()
                creator.gate(mouseTile.x * 32, mouseTile.y * 32, 0)
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.O)) {

            create {
                val creator = getKoin().get<WallObjects>()
                creator.generator(mouseTile.x * 32, mouseTile.y * 32, 0)
            }
        }
    }
}
