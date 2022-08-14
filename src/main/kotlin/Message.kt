class Message(
    var userId: Int,
    val messageId: Int,
    var text: String,
    val incoming: Boolean,
    var read: Boolean,
) {

    override fun toString(): String {
        return "(Сообщение №$messageId от Пользователя №$userId: '$text ')\n"
    }
}