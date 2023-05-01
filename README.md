# spring-bot-demo
spring bot demo

## $\color{#FF0000}{controller}$
this package is demos for spring boot controller's use.

* [HelloWorldController](./src/main/java/com/spring/bot/demo/controller/HelloWorldController.java) : a controller demo.
+ [BookController](./src/main/java/com/spring/bot/demo/controller/BookController.java) : a controller demo for book library. 
  - add(): Post-Http for add book to library.
  - deleteById(): Delete-Http for del a book from library.
  - getByName(): Get-Http for query books from library.
* [AppExceptionHandler](./src/main/java/com/spring/bot/demo/controller/AppExceptionHandler.java) : a controller demo for handling all kinds of app BaseException and giving a response with detail error message. 
```
curl http://localhost:8333/api/book/name?name=love

{
    "code": 2001,
    "status": 404,
    "message": "book not found",
    "path": "/book/name",
    "timestamp": "2023-04-30T03:19:03.641352Z",
    "data": {
        "book name:": "love"
    }
}
```
* [MintFunController](./src/main/java/com/spring/bot/demo/controller/MintFunController.java) : mybatis operation demo.(contains pagination query and multi-table join query)
```
curl http://localhost:8333/api/mint/user/all?page=1&rows=3

{
    "total": 5,
    "pageTotal": 2,
    "page": 1,
    "pageRows": 2,
    "dataTs": [
        {
            "id": 1,
            "name": "sjaskillz",
            "addr": "0x4306dd0c18b58e34e587003841d736a449392d49",
            "following": 118,
            "followers": 65
        },
        {
            "id": 2,
            "name": "xpub.eth",
            "addr": "xpub.eth",
            "following": 22,
            "followers": 0
        }
    ]
}

curl http://localhost:8333/api/mint/user/detail/2

{
    "id": 2,
    "name": "xpub.eth",
    "addr": "xpub.eth",
    "following": 22,
    "followers": 0,
    "mnfts": [
        {
            "id": 4,
            "name": "PsychoNauts",
            "tokenId": "#3655",
            "addr": "0xe05590833120f8a671d43aa3e9870fed9361b4ca",
            "creator": "0x75af3607db30bbdf3c51aa59c8a346dab9b2f7b0",
            "total": 7777,
            "ownerId": 2
        },
        {
            "id": 5,
            "name": "PsychoNauts",
            "tokenId": "#3654",
            "addr": "0xe05590833120f8a671d43aa3e9870fed9361b4ca",
            "creator": "0x75af3607db30bbdf3c51aa59c8a346dab9b2f7b0",
            "total": 7777,
            "ownerId": 2
        }
    ]
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
* [xxxException](./src/main/java/com/spring/bot/demo/exception/BaseException.java) : implements of system runtime exceptions.
* [ErrorResponse](./src/main/java/com/spring/bot/demo/exception/ErrorResponse.java) : http response entity.
