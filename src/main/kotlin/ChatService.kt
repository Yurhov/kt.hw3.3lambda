class ChatService {
    private val chats = mutableListOf<Chat>()
    val users = mutableListOf<User>()

    /**
     * Поиск чата по двум собеседникам.
     */
    private fun findChat(to: Int, from: Int) =
        chats.indexOfFirst { (it.to + it.from == to + from) }

    /**
     * Создание собеседников.
     */
    internal fun createUser(name: String) = users.add(User(userId = users.size + 1, name))

    /**
     * Создание нового сообщения.
     */
    internal fun addNewMessage(to: Int, from: Int, text: String): Boolean {
        if (findChat(to = to, from = from) == -1) {
            chats.add(
                Chat(
                    to = to,
                    from = from,
                    id = uniqueId(),
                    mutableListOf(
                        Message(
                            userId = to,
                            messageId = uniqueIdMessage(userIdIn = to, userIdOut = from),
                            text = text,
                            incoming = false,
                            read = true
                        )
                    )
                )
            )
            return true
        } else
            chats[findChat(to = to, from = from)].message.add(
                Message(
                    userId = to,
                    messageId = uniqueIdMessage(userIdIn = to, userIdOut = from),
                    text = text,
                    incoming = false,
                    read = true
                )
            )
        return false
    }

    /**
     * Создание нового чата
     */
    internal fun addNewChat(to: Int, from: Int, text: String): Boolean {
        if (findChat(to = to, from = from) == -1) {
            chats.add(
                Chat(
                    to, from, id = uniqueId(),
                    message = mutableListOf(
                        Message(
                            userId = to,
                            messageId = uniqueIdMessage(to, from),
                            text,
                            incoming = false,
                            read = true
                        )
                    )
                )
            )
            return true
        }
        println("Такой чат уже есть!")
        return false
    }

    /**
     * Удаление чата по ID чата.
     */
    internal fun deleteChat(id: Int): Boolean {
        if (chats.indexOfFirst { it.id == id } != -1) {
            chats.removeIf { it.id == id }
            return true
        }
        println("Вы пытаетесь удалить не тот чат, проверьте еще раз!")
        return false
    }

    /**
     * Удаление сообщения по ID собеседника и ID сообщения.
     */
    internal fun deleteMessage(chatId: Int, messageId: Int): Boolean {
        if (chats.any { it.id == chatId }) {
            if (chats[chats.indexOfFirst { it.from == chatId || it.to == chatId }].message.removeIf { it.messageId == messageId })
                return true
        }

        println("Вы пытаетесь удалить не то сообщение, проверьте еще раз!")
        return false
    }

    /**
     * Вывод списка чатов на экран.
     */
    fun getChats() =
        println(chats.ifEmpty { "Чат не найден!" })

    /**
     *Обновление конкретного сообщения.
     */
    internal fun updateMessage(userId: Int, messageId: Int, text: String): Boolean {
        if (chats.any { it.from == userId || it.from == userId }) {
            if (chats[chats.indexOfFirst { it.from == userId || it.to == userId }].message.any { it.messageId == messageId }) {
                chats[chats.indexOfFirst { it.from == userId || it.to == userId }].message.find { it.messageId == messageId }!!.text =
                    text

                return true
            }
            println("Сообщения не найдены!")
        }
        println("Обновление не удалось!")
        return false
    }

    /**
     * Получение сообщения по его ID.
     */
    internal fun getMessagesByMessageId(chatId: Int, messageId: Int): Boolean {
        if (chats.indexOfFirst { it.id == chatId } == -1) {
            println("Чат не найден!")
            return false
        }
        if (chats[chats.indexOfFirst { it.id == chatId }]
                .message.any { it.messageId == messageId }
        ) {
            val chat = chats[chats.indexOfFirst { it.id == chatId }]
                .message
                .filter { it.messageId == messageId }
                .joinToString(prefix = "\n", separator = "||\n") { it.text }
            println("___________________________________________________")
            println("Чат№ $chatId Сообщение №$messageId: $chat")

            return true
        }
        println("Сообщений нет!")
        return false
    }

    /**
     * Получение сообщений по количеству.
     */
    internal fun getMessagesByCount(chatId: Int, count: Int): Boolean {
        if (chats.indexOfFirst { it.id == chatId } != -1) {
            val chat = chats[chats.indexOfFirst { it.id == chatId }]
                .message
                .takeLast(count)
                .joinToString(prefix = "\n", separator = "||\n") { it.text }
            println("___________________________________________________")
            println("Чат№$chatId Последние ($count) сообщений: $chat")
            return true
        }
        println("Чат не найден!")
        return false
    }

    /**
     * Получение сообщений по ID чата.
     */
    internal fun getMessagesByChatId(chatId: Int): Boolean {
        if (chats.indexOfFirst { it.id == chatId } != -1) {
            val chat = chats[chats.indexOfFirst { it.id == chatId }].message
                .joinToString(prefix = "\n", separator = "||\n") { it.text }
            println("________________________________")
            println("Чат№$chatId Сообщения: $chat")
            return true
        }
        println("Сообщений нет!")
        return false
    }

    /**
     * Получение количества чатов, где есть хотя бы одно не прочитанное сообщение.
     */
    internal fun getCountUnreadMessage(userId: Int): Int {
        if (chats.indexOfFirst { it.to == userId } != -1) {
            return chats.filter { it.to == userId }.map { it.message }.size
        }
        println("Непрочитанных сообщений нет!"); return 0
    }

    /**
     * Создание уникального ID для чата.
     */
    private fun uniqueId(): Int {
        if (chats.isEmpty()) {
            return 1
        }
        return chats.last().id + 1
    }

    /**
     * Создание уникального ID для сообщения.
     */
    private fun uniqueIdMessage(userIdIn: Int, userIdOut: Int): Int {
        if (findChat(to = userIdIn, from = userIdOut) == -1)
            return 1

        return chats[findChat(to = userIdIn, from = userIdOut)].message.last().messageId + 1
    }
}