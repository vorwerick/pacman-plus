package cz.pacmanplus.game.core.entity

data class LevelRecord(
    val levelId: Int,
    val currentScore: Int,
    val maxScore: Int,
    val stars: Int,
    val maxStars: Int,
    val difficulty: Int,
    val maxDifficulty: Int,
    val done: Boolean,
    val unlocked: Boolean
) {
}
