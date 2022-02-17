package br.com.dialogystudios.base.data.model

data class Todo(
    val title: String,
    val description: String,
    val timestamp: Long
) {
    fun toMap(): Map<String, Any> {
        return mapOf<String, Any>(
            "title" to title,
            "description" to description,
            "timestamp" to timestamp
        )
    }

    companion object {
        fun from(map: Map<String, Any>): Todo {
            return Todo(
                title = map.get("title") as String ?: "",
                description = map.get("description") as String ?: "",
                timestamp = map.get("timestamp") as Long ?: System.nanoTime()
            )
        }

        fun newBlank(): Todo = Todo("", "", System.nanoTime())
    }
}