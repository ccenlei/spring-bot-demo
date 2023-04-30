# spring-bot-demo
spring bot demo

## $\color{#FF0000}{controller}$
this package is demos for spring boot controll's use.

* [HelloWorldController](./src/main/java/com/spring/bot/demo/controller/HelloWorldController.java) : a controller demo.
+ [BookController](./src/main/java/com/spring/bot/demo/controller/BookController.java) : a controller demo for book library. 
  - add(): Post-Http for add book to library.
  - deleteById(): Delete-Http for del a book from library.
  - getByName(): Get-Http for query books from library.
* [BookExceptionHandler](./src/main/java/com/spring/bot/demo/controller/BookExceptionHandler.java) : a controller demo for handling BookException and giving a response with detail error message. 
```
curl http://localhost:8333/book/name?name=love

{
    "code": 2001,
    "status": 404,
    "msessage": "book not found",
    "path": "/book/name",
    "timestamp": "2023-04-30T03:19:03.641352Z",
    "data": {
        "book name:": "love"
    }
}
```

## $\color{#FF0000}{component}$
this package is demos for spring boot property's use.

### [LibraryProperties](./src/main/java/com/spring/bot/demo/component/LibraryProperties.java) :  a demo for spring boot loads property.

## $\color{#FF0000}{lombok}$
this package is demos for spring boot lombok's use. more details see: [lomboks](https://hezhiqiang8909.gitbook.io/java/docs/javalib/lombok)

* [SynchronizedDemo](./src/main/java/com/spring/bot/demo/lombok/SynchronizedDemo.java) :  a demo for synchronizing functions.

* [SneakyThrowsDemo](./src/main/java/com/spring/bot/demo/lombok/SneakyThrowsDemo.java) :  a demo for throwing exceptions.

* [GetterLazyDemo](./src/main/java/com/spring/bot/demo/lombok/GetterLazyDemo.java) :  a demo for double check lock.

## $\color{#FF0000}{exception}$
this package is demos for spring bot app exceptions handling ways. there are 3 important classes: 
* [ErrorCode](./src/main/java/com/spring/bot/demo/exception/ErrorCode.java) :  contains app error code, http response code and http response message.
* [xxxException](./src/main/java/com/spring/bot/demo/exception/BaseException.java) : implements of system runnig exceptions.
* [ErrorResponse](./src/main/java/com/spring/bot/demo/exception/ErrorResponse.java) : http response entity.