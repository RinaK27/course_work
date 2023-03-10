**Перечень автоматизируемых сценариев**

**Покупка по дебетовой карте**

Отправка формы, заполненной валидными значениями с действующей картой.
1. Ввести в поле «Номер карты» данные карты APPROVED
2. Ввести валидные данные в поле «Месяц» 
3. Ввести валидные данные в поле «Год»
3. Ввести валидные данные в поле «Владелец»
4. Ввести валидные данные в поле «CVC/CVV»
5. Нажать на кнопку «Продолжить»
<br/>*Ожидаемый результат: Окно с уведомлением «Успешно» + «Операция одобрена Банком».*</br>

Отправка формы с недействующей картой.
1. Ввести в поле «Номер карты» данные карты DECLINED
2. Ввести валидные данные в поле «Месяц» 
3. Ввести валидные данные в поле «Год»
3. Ввести валидные данные в поле «Владелец»
4. Ввести валидные данные в поле «CVC/CVV»
5. Нажать на кнопку «Продолжить»
<br/>*Ожидаемый результат: Окно с уведомлением «Отказ» + «Операция отклонена Банком».*</br>

Отправка пустой формы.
1. Нажать на кнопку «Продолжить»
<br/>*Ожидаемый результат: Уведомление под каждой графой формы «Поле обязательно для заполнения».*</br>

Граничные значения поля номер карты.
1. Ввести в поле 15 символов
<br/>*Ожидаемый результат: Уведомление «Неверный формат».*</br>
2. Ввести в поле 16 символов
<br/>*Ожидаемый результат: Поле заполняется.*</br>
3. Ввести в поле 17 символов
<br/>*Ожидаемый результат: После 16 символов поле не заполняется.*</br>

Граничные значения поля месяц. 
1. Ввести в поле 00
<br/>*Ожидаемый результат: Уведомление «Неверно указан срок действия карты».*</br>
2. Ввести в поле 01
<br/>*Ожидаемый результат: Поле заполняется.*</br>
3. Ввести в поле 02
<br/>*Ожидаемый результат: Поле заполняется.*</br>
4. Ввести в поле 11
<br/>*Ожидаемый результат: Поле заполняется.*</br>
5. Ввести в поле 12
<br/>*Ожидаемый результат: Поле заполняется.*</br>
6. Ввести в поле 13
<br/>*Ожидаемый результат: Уведомление «Неверно указан срок действия карты».*</br>

Граничные значения поля год.
1. Ввести в поле предыдущий год
<br/>*Ожидаемый результат: Уведомление «Истек срок действия карты».*</br>
2. Ввести в поле текущий год (+ текущий месяц или более поздний по 12)
<br/>*Ожидаемый результат: Поле заполняется.*</br>
3. Ввести в поле следующий за текущим год
<br/>*Ожидаемый результат: Поле заполняется.*</br>
4. Ввести в поле текущий год + 3 года (срок действия дебетовой карты)
<br/>*Ожидаемый результат: Поле заполняется.*</br>

Валидация поля владелец.
1. Ввести в поле значение на кириллице
<br/>*Ожидаемый результат: Уведомление «Неверный формат».*</br>
2. Ввести в поле значение на латинице 
<br/>*Ожидаемый результат: Поле заполняется.*</br>
3. Ввести в поле случайный набор символов
<br/>*Ожидаемый результат: Уведомление «Неверный формат».*</br>

Валидация поля CVC/CVV код.
1. Ввести в поле любые 3 цифры
<br/>*Ожидаемый результат: Поле заполняется.*</br>
2. Ввести в поле значение на латинице 
<br/>*Ожидаемый результат: Уведомление «Неверный формат».*</br>
3. Ввести в поле значение на кириллице
<br/>*Ожидаемый результат: Уведомление «Неверный формат».*</br>
4. Ввести в поле случайный набор символов
<br/>*Ожидаемый результат: Уведомление «Неверный формат».*</br>

**Перечень используемых инструментов с обоснованием выбора:**

- Среда разработки Intellij IDEA – для написания автотестов на java;
- Система автоматической сборки Gradle, для подключения к проекту зависимостей;
- JUnit 5 - для написания автотестов и их запуска;
- Git – для контроля версий кода;
- Lombok - для автоматического создания геттеров, сеттеров и конструкторов;
- Faker - для генерации тестовых данных;
- Selenide - для автоматизированного тестирования веб-приложений;
- MySQL - свободная реляционная система управления базами данных;
- Docker desktop - для создания контейнеров;
- Allure - для составления отчетов прогонки тестов.

**Перечень и описание возможных рисков при автоматизации:**

Подготовка тестового окружения/настройка инструментов для тестирования и составление отчетов о тестировании могут занять значимую часть времени.
Поиск селекторов на web-странице.

**Интервальная оценка с учётом рисков в часах:**
- Планирование – 3 часа.
- Подключение и настройка тестового окружения – 8 часов.
- Написание и проведение автотестов – 40 часов.
- Составление отчетной документации по итогам тестирования – 10 часов.
- С учетом рисков, дополнительно заложено 10 часов.

**План сдачи работ: когда будут проведены автотесты, результаты их проведения и отчёт по автоматизации:**

Проведение автотестов – до 26.02.2023.
Подготовка отчетной документации – до 06.03.2023.
