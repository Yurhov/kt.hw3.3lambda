class User(val userId: Int, val name: String) {
    override fun toString(): String {
        return "Пользователь №$userId: Имя = $name\n"
    }
}