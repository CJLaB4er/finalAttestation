## Приложение Swagger.
Swagger – это инструмент, который помогает разработчикам создавать, документировать и проверять API. API – это набор правил и протоколов, которые позволяют различным системам обмениваться информацией между собой.

Запустим приложение и выполним в браузере запрос:

```html
localhost:8080/swagger-ui/index.html
```

Результат запроса:

![swagger1](./images/swagger1.jpg)
![swagger2](./images/swagger2.jpg)

Графический интерфейс Swagger предоставляет возможность не только в интерактивном режиме изучать спецификацию Rest Full Api, но и отправлять запросы.

![swagger3](./images/swagger3.jpg)

Изучаем схему:

![swagger4](./images/swagger4.jpg)

Выполним POST-запрос на добавление нового пункта:

![swagger5](./images/swagger5.jpg)

Выполним GET-запрос на получение пункта с несуществующим ID:

![swagger6](./images/swagger6.jpg)


Для получение JSON-спецификации выполним в браузере запрос:

```html
http://localhost:8080/v3/api-docs
```

Результат запроса:

![swagger7](./images/swagger7.jpg)

