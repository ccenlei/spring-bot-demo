# spring-bot-demo
spring bot demo

## $\color{#FF0000}{controller}$
this package is demos for spring boot controller's use.
* [HelloWorldController](./src/main/java/com/spring/bot/demo/controller/HelloWorldController.java) : a controller demo. 
```
curl http://localhost:8333/hello/say?name=world

Hello world!

（port根据实际配置修改）

## $\color{#FF0000}{mvn打包指令}$
在项目根目录下，使用Maven 命令 mvn clean package 进行打包。
打包成功后，会在 target 目录下生成WAR 文件。
