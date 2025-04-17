package cz.pacmanplus.game.core.systems

import com.artemis.Aspect
import com.artemis.Entity
import com.artemis.systems.EntityProcessingSystem
import cz.pacmanplus.game.core.components.control.ComputerPathComponent
import cz.pacmanplus.game.core.components.control.computer.FindPlayerComponent
import cz.pacmanplus.game.core.components.physics.PositionComponent
import cz.pacmanplus.game.core.systems.grid.WallGridSystem
import cz.pacmanplus.utils.findPath

//find computer component with input component
class ComputerPatrolSystem :
    EntityProcessingSystem(Aspect.all(ComputerPathComponent::class.java)) {
    override fun process(e: Entity?) {

        val grid = world.getSystem(WallGridSystem::class.java).wallGrid


        e?.let { computer ->
            var player: Entity? = null
            computer.getComponent(ComputerPathComponent::class.java)?.let { computerPatrolComponent ->

                if (computerPatrolComponent.path != null && computerPatrolComponent.path!!.isNotEmpty()) {

                } else {

                    computer.getComponent(FindPlayerComponent::class.java)?.let { findPlayer ->
                        player = world.findPlayer()

                        if (player != null) {

                            val position = computer.getComponent(PositionComponent::class.java)
                            val tileX = ((position.x + 16) / 32).toInt()
                            val tileY = ((position.y + 16) / 32).toInt()
                            val targetPosition = player!!.getComponent(PositionComponent::class.java)
                            val targetX = ((targetPosition.x + 16) / 32).toInt()
                            val targetY = ((targetPosition.y + 16) / 32).toInt()
                          //  println(targetX .toString()+ " "+targetY + "( ${grid.getGrid().size})")
                            computerPatrolComponent.path = grid.findPath(tileX, tileY, targetX, targetY).toMutableList()
                            //println(computerPatrolComponent.path)

                        }

                    }

                }


            }


        }
    }


}
