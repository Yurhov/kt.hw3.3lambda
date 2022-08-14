fun main() {
    val chatService = ChatService()
    chatService.createUser("Коля")
    chatService.createUser("Света")
    println(chatService.users)

    chatService.getChats()

    chatService.addNewChat(1, 2, "Добрый день")

    chatService.addNewMessage(2, 1, "Привет")
    chatService.addNewMessage(2, 1, "Как дела?")
    chatService.addNewMessage(1, 2, "Хорошо. А у тебя?")
    chatService.addNewMessage(2, 1, "Всё отлично!")
    chatService.addNewMessage(2, 6, "Привет, давай познакомимся?")
    chatService.addNewMessage(1, 1, "Напоминалка: записаться в МФЦ.")
    chatService.addNewMessage(1, 1, "Напоминалка: Оплатить квартиру")

    chatService.addNewMessage(1, 2, "Я рад")

    chatService.addNewMessage(6, 2, "Я не знакомлюсь")
    chatService.updateMessage(1, 1, "Привет")
    chatService.getChats()

    println("Удалим один из чатов и теперь посмотрим список чатов:")
    chatService.deleteChat(56)
    chatService.deleteChat(1)
    chatService.getChats()
    println(chatService.getMessagesByMessageId(2, 1))
    chatService.deleteMessage(2, 1)
    println(chatService.getMessagesByMessageId(2, 1))
}

