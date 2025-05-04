package cz.pacmanplus.editor.entities

class Level(val width: Int, val height: Int, val cellSize: Int, val offsetX: Int,val offsetY: Int){

    val layers: MutableList<Layer> = mutableListOf()

    fun deserialize(): ByteArray{
        return StringBuilder().apply {
            append("?")
            append(width)
            append(":")
            append(height)
            append(":")
            append(cellSize)
            append(":")
            append(offsetX)
            append(":")
            append(offsetY)
            append(":")
            layers.map { it.deserialize() }
            append("!")
        }.toString().toByteArray()
    }

    companion object{
        fun serialize(source: ByteArray): Level? {
           var text = source.decodeToString()
            val start = text[0] == '?'
            val end = text[text.length - 1] == '!'
            if(start && end){
                text = text.removePrefix("?")
                text = text.removeSuffix("!")
                val parts = text.split(":")
                val width = parts[0].toInt()
                val height = parts[1].toInt()
                val cellSize = parts[2].toInt()
                val offsetX = parts[3].toInt()
                val offsetY = parts[4].toInt()

            }

            return null
        }
    }
}
