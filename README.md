## REST-API для работы с базой данных информации о пользователях посредством Java, Spring и Vaadin

---

### В процессе:
* БД - PostgreSQL, поднята в Docker - контейнере;
* Основная сущность [User](https://github.com/MikhailAkulov/Test-Task-For-TR-Soft-Vaadin/blob/main/src/main/java/trsoft/test_task/model/User.java);
* Интерфейс для работы с базой данных [UserRepository](https://github.com/MikhailAkulov/Test-Task-For-TR-Soft-Vaadin/blob/main/src/main/java/trsoft/test_task/repository/UserRepository.java);
* Основной страницей приложения должна была стать [UserList](https://github.com/MikhailAkulov/Test-Task-For-TR-Soft-Vaadin/blob/main/src/main/java/trsoft/test_task/views/UserList.java)
  * тут метод заполнения таблицы и кнопки;
* Страницей редактирования и создания контактов - [ManageUser](https://github.com/MikhailAkulov/Test-Task-For-TR-Soft-Vaadin/blob/main/src/main/java/trsoft/test_task/views/ManageUser.java)
  * тут заполнение формы;
