# TangSeng-vehicle
用到了接口、instanceof、多态、接口调用、饿汉式单例不需每次new......源自老韩java视频p440
### 单例模式是什么？ 
单例模式是一种软件设计模式，它保证一个类在整个应用程序中只有一个实例。这样就可以保证全局只有一个实例，并且可以全局访问这个实例。
单例模式的一个常见用途是提供一个全局访问点来访问数据库连接、线程池、缓存等共享资源。
单例模式的常用实现方法是将构造函数设为私有，并在类内部创建唯一的实例。然后提供一个公开的静态方法来获取这个实例。这样就可以在整个应用程序中调用这个方法来获取唯一的实例。
例如，在 Java 中，单例模式的实现如下：
```java
public class Singleton {
    private static Singleton instance;
    private Singleton() {}
    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```
在这个例子中，Singleton 类的构造函数被设为私有，这意味着外部代码无法直接创建 Singleton 类的实例。相反，必须调用 getInstance 方法来获取唯一的实例。如果实例尚未创建，则会创建一个新的实例，并将其保存在 instance 变量中。如果实例已经创建，则直接返回保存在 instance 中
