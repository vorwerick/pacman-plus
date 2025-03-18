package cz.pacmanplus.game.core.entity

import com.artemis.World
import com.badlogic.gdx.math.Vector2
import cz.pacmanplus.game.core.components.common.NameComponent
import cz.pacmanplus.game.core.components.control.ForcePushingComponent
import cz.pacmanplus.game.core.components.control.PlayerInputComponent
import cz.pacmanplus.game.core.components.physics.*
import ktx.artemis.entity
import org.koin.mp.KoinPlatform.getKoin
import org.slf4j.LoggerFactory

object ObstacleFactory {

    val log = LoggerFactory.getLogger("ObstacleFactory")

    fun createWallBox(x: Float, y: Float): Int {
        val world: World = getKoin().get<World>()
        val entity = world.createEntity()
        val edit = entity.edit()


        val nameComponent = edit.create(NameComponent::class.java)
        nameComponent.name = "Wall"

        val positionComponent = edit.create(PositionComponent::class.java)
        positionComponent.x = x
        positionComponent.y = y

        val rectangleCollisionComponent = edit.create(RectangleCollisionComponent::class.java)
        rectangleCollisionComponent.width = 32f
        rectangleCollisionComponent.height = 32f
        rectangleCollisionComponent.solid = true




        log.info("Wall entity created id: ${entity.id} named: $nameComponent on position: $positionComponent")
        return entity.id
    }

    fun createPushableBox(x: Float, y: Float): Int {
        val world: World = getKoin().get<World>()
        val entity = world.createEntity()
        val edit = entity.edit()


        val nameComponent = edit.create(NameComponent::class.java)
        nameComponent.name = "Push box"

        val pushableComponent = edit.create(PushableComponent::class.java)
        pushableComponent.mass = 3

        val movementComponent = edit.create(MovementComponent::class.java)


        val positionComponent = edit.create(PositionComponent::class.java)
        positionComponent.x = x
        positionComponent.y = y

        val rectangleCollisionComponent = edit.create(RectangleCollisionComponent::class.java)
        rectangleCollisionComponent.width = 32f
        rectangleCollisionComponent.height = 32f
        rectangleCollisionComponent.solid = true




        log.info("Wall entity created id: ${entity.id} named: $nameComponent on position: $positionComponent")
        return entity.id
    }

    fun createFlowArea(x: Float, y: Float, dir: Vector2): Int {
        val world: World = getKoin().get<World>()
        val entity = world.createEntity()
        val edit = entity.edit()


        val nameComponent = edit.create(NameComponent::class.java)
        nameComponent.name = "Damage area"

        val positionComponent = edit.create(PositionComponent::class.java)
        positionComponent.x = x
        positionComponent.y = y

        val rectangleCollisionComponent = edit.create(RectangleCollisionComponent::class.java)
        rectangleCollisionComponent.width = 28f
        rectangleCollisionComponent.height = 28f
        rectangleCollisionComponent.solid = false

        val forcePushingComponent = edit.create(ForcePushingComponent::class.java)
        forcePushingComponent.dir = dir
        forcePushingComponent.speed = 10




        log.info("Wall entity created id: ${entity.id} named: $nameComponent on position: $positionComponent")
        return entity.id
    }

    fun createDamageRectArea(x: Float, y: Float): Int {
        val world: World = getKoin().get<World>()
        val entity = world.createEntity()
        val edit = entity.edit()


        val nameComponent = edit.create(NameComponent::class.java)
        nameComponent.name = "Damage area"

        val positionComponent = edit.create(PositionComponent::class.java)
        positionComponent.x = x
        positionComponent.y = y

        val rectangleCollisionComponent = edit.create(RectangleCollisionComponent::class.java)
        rectangleCollisionComponent.width = 28f
        rectangleCollisionComponent.height = 28f
        rectangleCollisionComponent.solid = false

        val damageComponent = edit.create(DamageComponent::class.java)
        damageComponent.damage = 1




        log.info("Wall entity created id: ${entity.id} named: $nameComponent on position: $positionComponent")
        return entity.id
    }
}
