package cz.pacmanplus.editor.entities

data class Object(val id: Int, val x: Int, val y: Int, val objectType: Int){


    fun deserialize(): String{
        return "[$id,$x,$y,$objectType]"
    }

    companion object{
        fun serialize(text: String): Object {
            val t = text.replace("[]", "")
            val params = t.split(",")
            return Object(params[0].toInt(), params[1].toInt(), params[2].toInt(), params[3].toInt())
        }
    }

}
