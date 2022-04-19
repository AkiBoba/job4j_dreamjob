В этом блоке мы поговорим о создании веб приложений на языка Java.

Мы будем использовать Spring boot, как основную библиотеку, но касается деталей этого фреймворка не будем.

Основная задача этого блока, понять архитектуру веб приложений.

Мы будем разрабатывать приложение "Работа мечты".

Для работы с этим блоком Вам нужно поставить Ultimate IDEA. Учебные ключи можно получить у Арсентьева Петра.

В системе будут две модели: вакансии и кандидаты. Кандидаты будут публиковать резюме. Кадровики будут публиковать вакансии о работе.

Кандидаты могут откликнуться на вакансию. Кадровик может пригласить на вакансию кандидата.

1. Техническое задание - проект "Работа мечты" [#504836]
2. 1. Что такое клиент - серверное приложение. [#504837]
3. 1. Генератор HTML [#504838] -
4. 3. Bootstrap, Table [#504840]
5. 5. Список Вакансий [#504842]
6. 6. HTML Link [#504843]
7. 7. HTML form. Создание вакансии. [#504844]
8. 8. HTML form. Создание кандидата. [#504845]
9. 9. Обновление кода без перезагрузки сервера. [#504846]

3.2.3. Контроллеры. Spring + Thymeleaf

3. PostController.savePost. Создание вакансии. [#504849 #274879]
Добавил метод add(Post post) для записи новой вакансии в store -
4. 4. PostController.savePost. Редактирование вакансии. [#504850]
5. 5. Создания и редактирования кандидатов. [#504858]

3.2.4. Архитектура Web приложений
2. Связь слоев через Spring DI. [#504856]
+ CandidateService 
+ Собрано в один пакет ru.job4j.dreamjob

5. Создания и редактирования кандидатов. [#504858 #276405] - исправление +


3.2.5. Формы
1. Формы. Поля ввода. [#504853]
2. 2. Формы. Списки. [#504854] минус лишние методы
3. 3. Формы. Загрузка файла на сервер. [#504855] +-

. Подключение к базе в веб приложении. Хранение вакансий.+
2. CandidateDbStore. Хранение кандидатов. [#504861]
3. 4. Многопоточность в базе данных [#504860] --
4. 1. Страница login.html [#504863] -
5. 2. HttpSession [#504864]