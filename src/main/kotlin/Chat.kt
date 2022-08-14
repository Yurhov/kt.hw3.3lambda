open class Chat(
    val to: Int,
    val from: Int,
    val id: Int = 0,

    var message: MutableList<Message> = mutableListOf<Message>(),
) {

    override fun toString(): String {
        return "Чат №$id(Участники: Пользователь №$from и Пользователь №$to\n" +
                "| Сообщения чата:\n${message} \n____________________________\n"
    }


}