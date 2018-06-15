## Getting Started
Склонируйте проект с репозитория 
###Test
Проект запускался с jdk 10.0.1 and gradle 4.6
но совместим также начиная с jdk 8
### Prerequisites

Вам необходимо установить для запуска Jdk 8, Gradle 4.6, MongoDB

Gradle : https://gradle.org/install/

Jdk 8 : http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

MongoDB : https://www.mongodb.com/

### Settings
Для изменения настроек необходимо запустить скрипт server.sh

Далее необходимо ввести команду set имя_константы значение

Выбранная константа изменится.

Ниже перечислен список команд доступных в скрипте:

*[set имя_константы значение]() - устанавливает значение выбранной константе

*[help]() - просмотр основных команд скрипта

*[help const]() - выводит информацию о константах

*[show]() - показывает все значения констант

*[run]() - удаляет превведущий билд, собирает и запускает сервер занова

Доступные константы для изменения:

*[PORT]() - номер порта

*[APP_BASE]() - директория приложения

*[LOCATION]() - расположение верхней директории приложения

*[YANDEX_KEY]() - ключ для яндекс погоды

*[GOOGLE_KEY]() - ключ для определения местоположения

*[REQUEST_GEOCORDINATING] - запрос для определения местоположения

*[YANDEX_WEATHER_REQUEST] - запрос для погоды от яндекса

*[RATES_REQUEST] - запрос для определения валют

*[NAME_DATA_BASE] - имя базы данных

*[COLLECTION_NAME] -имя коллекции

*[FIELD_KEY] - имя ключа

Комментарий к настройке бд :

Значение хранится в коллекции в которой два поля _id и значение ключа 
### Installing
Включить базу данных

Далее необходимо выполнить команды

service mongod start

mongo --host 127.0.0.1:27017

use VisitsDB //при этом необходимо заранее настроить бд с нужнйо коллекцией

Включить скрипт server.sh

и написать команду run

## Built With

* [Vaadin](https://vaadin.com/framework) - используемый  web framework 
* [Gradle](https://gradle.org/) - сборщик
* [Spring](https://spring.io/) - используется для конфигурирования
* [Tomcat](http://tomcat.apache.org/) - веб-сервер
* [Yandex-Weather API](https://tech.yandex.ru/weather/doc/dg/concepts/forecast-response-test-docpage/) - используется для получения погоды
* [GeoCoding API](https://developers.google.com/maps/documentation/geocoding/intro?hl=ru) - используетися для получения координат из строки
* [CenterBank API](https://www.cbr-xml-daily.ru/) - используется для получения курса валют

## Authors

**kostya05983(RustLife)**
Вы можете написать мне на почту : kostya05983@mail.ru


