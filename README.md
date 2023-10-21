# TangSeng-vehicle
用到了接口、instanceof、多态、接口调用、饿汉式单例不需每次new......源自老韩java视频p440
## 单例模式是什么？ 
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

## 单例模式的7中写法
饥饿模式：类加载时就初始化，线程安全，但是会浪费内存

双重检查锁：懒加载，线程安全，但是会有指令重排的问题

静态内部类：懒加载，线程安全，但是会有指令重排的问题

枚举：懒加载，线程安全，但是不够灵活

容器：懒加载，线程不安全，但是可以用ConcurrentHashMap解决

ThreadLocal：懒加载，线程安全，但是只能在同一个线程中共享

CAS：懒加载，线程安全，但是会有ABA问题

### 饥饿
```java
public class Singleton {
    private static Singleton instance = new Singleton();

    private Singleton() {
    }

    public static Singleton getInstance() {
        return instance;
    }
}
```

### synchronized 
```java
public class Singleton {
    private static Singleton instance;
    private Singleton() {}
    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```
缺点：性能开销较大。每次调用getInstance()都需要进行同步，即使实例已经创建。所以有下面的双重检查锁写法

为什么检查2次？
外层 if (instance == null)
这个检查是为了避免不必要的同步。如果实例已经被创建，那么这一检查可以避免进入同步块，从而提高性能。

内层 if (instance == null)
这个检查是在同步块内部进行的，并且是为了确保只有一个实例被创建。
### 双重检查锁
```java
public class Singleton {
    private static volatile Singleton instance;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

### 静态内部类
```java
public class Singleton {
    private Singleton() {
    }

    private static class SingletonHolder {
        private static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
```

### 枚举
```java
public enum Singleton {
    INSTANCE;

    public void doSomething() {
        System.out.println("do something");
    }
}
```

### 容器
```java
public class Singleton {
    private static Map<String, Object> singletonMap = new ConcurrentHashMap<>();

    private Singleton() {
    }

    public static Object getInstance(String className) {
        synchronized (singletonMap) {
            if (!singletonMap.containsKey(className)) {
                try {
                    singletonMap.put(className, Class.forName(className).newInstance());
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return singletonMap.get(className);
    }
}
```

### ThreadLocal
```java
public class Singleton {
    private static final ThreadLocal<Singleton> threadLocal = new ThreadLocal<>();

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (threadLocal.get() == null) {
            threadLocal.set(new Singleton());
        }
        return threadLocal.get();
    }
}
```

### CAS
```java
public class Singleton {
    private static final AtomicReference<Singleton> INSTANCE = new AtomicReference<>();

    private Singleton() {
    }

    public static Singleton getInstance() {
        for (; ; ) {
            Singleton singleton = INSTANCE.get();
            if (singleton != null) {
                return singleton;
            }
            singleton = new Singleton();
            if (INSTANCE.compareAndSet(null, singleton)) {
                return singleton;
            }
        }
    }
}
```
