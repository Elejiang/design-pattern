# 设计模式

## 写在前面

大家好，我也只是个普通的设计模式学习者，我只是将我学习设计模式的过程和经验总结并分享出来，文中难免有疏漏之处，恳请斧正！

## 设计模式的分类

### 创建型模式  

创建型模式共五种：工厂方法模式、抽象工厂模式、单例模式、建造者模式、原型模式。  

### 结构型模式

结构型模式共七种：适配器模式、装饰器模式、代理模式、外观模式、桥接模式、组合模式、享元模式。

### 行为型模式

行为型模式共十一种：策略模式、模板方法模式、观察者模式、迭代器模式、责任链模式、命令模式、备忘录模式、状态模式、访问者模式、中介者模式、解释器模式。

## 创建型模式

### 简单工厂模式

> 定义了一个创建对象的类，由这个类来封装实例化对象的行为。  

简单工厂模式**并不是**23种设计模式之一，它违反了开闭原则，但我们可以通过它来引出对设计模式的初步印象。我们先不管它的定义，直接看案例：

现在我们有一个抽象Car类：

```java
abstract class Car {
    public abstract void drive();
}
```

以及Car的两个实现类，ACar和BCar：  

```Java
class ACar extends Car{
    @Override
    public void drive() {
        System.out.println("A车drive");
    }
}

class BCar extends Car{
    @Override
    public void drive() {
        System.out.println("B车drive");
    }
}
```

一般来说外界想获取ACar对象的话是：

```Java
Car aCar = new ACar();
```

设想有很多地方我们都用到了这个aCar，有天需求变了，我们要换成BCar，那我们需要把所有new了ACar的地方全部替换；有天又需要新增一个CCar，我们又要替换······  

这太麻烦了，我们**需要一个类帮我们管理创建对象**，这个类就是工厂类：

```Java
class SimpleCarFactory {
    public static Car getCar(String carType) {
        switch (carType) {
            case "A": return new ACar();
            case "B": return new BCar();
            default: return null;
        }
    }
}
```

这样，我们就能根据外界传进来的carType来返回对应的对象，来看看外界如何使用：

```Java
class Main {
    public static void main(String[] args) {
        Car car1 = SimpleCarFactory.getCar("A");
        car1.drive();
        
        Car car2 = SimpleCarFactory.getCar("A");
        car2.drive();
    }
}
```

好像有种脱裤子放屁的感觉？这和new一个对象有啥区别呢？假如很多地方要从工厂获取对象，不还得一个个去改吗，那假如我这样呢：

```Java
class Main {
    public static final String CAR = "A";
    
    public static void main(String[] args) {
        Car car1 = SimpleCarFactory.getCar(CAR);
        car1.drive();
        
        Car car2 = SimpleCarFactory.getCar(CAR);
        car2.drive();
    }
}
```

这样，因为需求的改变，我要从ACar换成BCar，原本需要改两处（真实项目中可能更多处），现在只需要把CAR这一处改了就行，如果我把CAR放入配置文件，甚至不需要修改源码，只需要修改配置文件就行了！ 

而此时，如果新增了一个CCar，我只需去工厂类switch新增一个分支，然后修改CAR就可以把用到Car的地方全部换成CCar。但也正因为需要修改工厂类，这违背了开放封闭原则，简单工厂并没有成为23种设计模式之一。

我们再回头看简单工厂模式的定义：

> 定义了一个创建对象的类，由这个类来封装实例化对象的行为。

**说人话**其实就是***定义一个创建对象的工厂，要获取对象从工厂获取，别再new啦~***

我们再来看看简单工厂中出现的角色：

- 抽象产品 ：定义了产品的规范，描述了产品的主要特性和功能。（Car）

- 具体产品 ：实现或者继承抽象产品的子类。（ACar和BCar）

- 具体工厂 ：提供了创建产品的方法，调用者通过该方法来获取产品。（SimpleCarFactory）

### 工厂方法模式

> 定义一个用于创建对象的接口，让子类决定实例化哪个产品类对象。工厂方法使一个产品类的实例化延迟到其工厂的子类。

先不管定义，我们想想简单工厂出现的毛病：新增一个产品类我们要修改工厂里的switch分支判断。造成这种现象的本质其实是一个工厂能生产的产品太多了，既能生产A产品，又能生产B产品，那如果一个工厂只能生产一种产品，分为A工厂，B工厂呢？

还是以Car举例：

```Java
abstract class Car {
    public abstract void drive();
}

class ACar extends Car {
    @Override
    public void drive() {
        System.out.println("A车drive");
    }
}

class BCar extends Car {
    @Override
    public void drive() {
        System.out.println("B车drive");
    }
}
```

我们想想，A工厂和B工厂都能生产相同的抽象产品Car，那他们应该有个共同的抽象接口：

```Java
interface CarFactory {
    Car createCar();
}
```

工厂的具体实现：

```Java
class ACarFactory implements CarFactory{
    @Override
    public Car createCar() {
        return new ACar();
    }
}

class BCarFactory implements CarFactory{
    @Override
    public Car createCar() {
        return new BCar();
    }
}
```

可以看到，每个工厂只生产自己的产品，确实没有了switch判断，来看看外部如何获取对象：

```Java
class Main {
    public static void main(String[] args) {
        CarFactory carFactory = new ACarFactory();

        Car car = carFactory.createCar();
        car.drive();
    }
}
```

嗯......先获取工厂对象，然后通过工厂获取产品，怎么感觉绕了一圈又回来了？这和直接new一个对象有什么区别，还整个什么工厂弄得更复杂了。这个稍后再提，我们先来看现在代码的可扩展性。

假如我现在要新增一个C产品，需要新增CCar类和CCarFactory类，只有新增，没有修改，倒是符合开闭原则。

那脱裤子放屁的事怎么说？原本是直接new一个对象，现在倒好，先new一个工厂，再从工厂获取对象，说到底还得在客户端new，需求改变还得修改客户端代码。这就涉及到工厂方法的真正优势所在：**它提供了一种灵活的方式来管理对象的创建过程**，现实场景中要获取对象不只是new那么简单，产品的具体创建过程是较为复杂的，而这些复杂的过程都被封装到了服务端的工厂类中，对于客户端来说，只需要知道我想要的产品从哪个工厂中生产，再把那个工厂new出来就行，再调用里面的方法获取对象就行，要替换产品的话只需要替换工厂就行。

>试想，你想要搞辆车，你需要把零件买回家，自己组装，外壳，发动机，方向盘，内饰······这不现实，我们基本上都是去4s店（工厂）直接去提车，4s店（工厂）怎么搞到车的你关心吗？你不关心，你只关心车能不能用。
>
>按照自己组装的做法，你原本计划搞辆奔驰，需要买奔驰的配件自己组装，如果想换宝马，有需要去买宝马的配件自己组装。而有了工厂模式之后，你只要知道4s店的地址（抽象工厂的接口）就行，想要奔驰就去奔驰4s店（具体工厂），想要宝马就去宝马4s店（具体工厂），需求的变化是无可避免的，工厂模式帮你做的是简单的获取产品，而不是预知你需求的变化。

到了这里，我们恍然大悟，简单工厂是把产品的选择放到了工厂内部，这就造成了switch的分支判断，影响了代码的可扩展性；工厂方法是把产品的选择交给了用户，让用户决定要哪个产品，这种方式和直接new对象的区别在于：用工厂封装了对象的获取过程，同时可以灵活替换工厂来替换不同产品。

我们再回头看工厂方法模式的定义：

> 定义一个用于创建对象的接口，让子类决定实例化哪个产品类对象。工厂方法使一个产品类的实例化延迟到其工厂的子类。

**说人话**其实就是***定义一个生产产品的工厂类接口，不同工厂类生产不同产品对象，产品是由工厂来new的，其中生产过程可能较为复杂，但用户不需要知道那么多，你只要知道工厂类接口，根据接口就能获取到对象了***

我们再来看看工厂方法模式中出现的角色：

- 抽象工厂：提供了创建产品的接口，调用者通过它访问具体工厂的工厂方法来创建产品。（CarFactory）

- 具体工厂：主要是实现抽象工厂中的抽象方法，完成具体产品的创建。（ACarFactory和BCarFactory）

- 抽象产品：定义了产品的规范，描述了产品的主要特性和功能。（Car）

- 具体产品：实现了抽象产品角色所定义的接口，由具体工厂来创建，它同具体工厂之间一一对应。（ACar和BCar）

### 抽象工厂模式

> 定义了一个接口用于创建相关或有依赖关系的对象族，而无需明确指定具体类。

先不管定义，我们继续工厂方法的例子，我们知道，一个公司可能不止只生产一种产品，例如美的，有美的空调，美的风扇，美的......按照工厂方法来说，这些产品每一个都应该由一个工厂来产出，但有的情况下，我们需要用一套对应的产品，例如就想用美的的一套产品，那么工厂方法就显得不太合适了，我需要把美的的所有工厂创建出来，然后一个个获取产品，这种情况下，抽象工厂模式就比较合适了。

抽象工厂就是一个工厂不再生成单一产品，而是生产一套产品，先看产品：

```Java
abstract class Car {
    public abstract void drive();
}

abstract class Bike {
    public abstract void ride();
}
```

产品有汽车和自行车，同时有A品牌的汽车，A品牌的自行车，B品牌的汽车，B品牌的自行车：

```Java
class ACar extends Car {
    @Override
    public void drive() {
        System.out.println("A drive car");
    }
}

class ABike extends Bike{
    @Override
    public void ride() {
        System.out.println("A ride Bike");
    }
}

class BCar extends Car {
    @Override
    public void drive() {
        System.out.println("B drive car");
    }
}

class BBike extends Bike{
    @Override
    public void ride() {
        System.out.println("B ride Bike");
    }
}
```

现在的工厂就是即能生产汽车，又能生产自行车，接口如下：

```Java
interface Factory {
    Car createCar();
    Bike createBike();
}
```

有A工厂和B工厂实现了工厂接口：

```Java
class AFactory implements Factory{
    @Override
    public Car createCar() {
        return new ACar();
    }

    @Override
    public Bike createBike() {
        return new ABike();
    }
}

class BFactory implements Factory{
    @Override
    public Car createCar() {
        return new BCar();
    }

    @Override
    public Bike createBike() {
        return new BBike();
    }
}
```

来看客户端代码：

```Java
class Main {
    public static void main(String[] args) {
        // 切换这句代码就可以切换一套产品，由A公司切换到B公司
        Factory factory = new AFactory();
//        Factory factory = new BFactory();

        Car car = factory.createCar();
        Bike bike = factory.createBike();
        car.drive();
        bike.ride();
    }
}
```

显然，切换不同的工厂就能直接切换一套产品，而其余代码都不需要变化。

这种模式的缺点也很明显，如果我要在产品族中新增一个产品，例如我想新增一个电瓶车，那么所有工厂（A工厂和B工厂）都需要新增生产这个产品的实现。

优点则是当一个产品族中的多个对象被设计成一起工作时，它能保证客户端始终只使用同一个产品族中的对象。

我们再回头看看抽象工厂模式的定义：

> 定义了一个接口用于创建相关或有依赖关系的对象族，而无需明确指定具体类。

**说人话**其实就是***定义一个工厂接口，能生产一套产品，保证客户端只拿到一套相关的产品***  

我们再看抽象工厂模式中出现的角色：

- 抽象工厂：提供了创建产品的接口，它包含多个创建产品的方法，可以创建多个不同等级的产品。（Factory）

- 具体工厂：主要是实现抽象工厂中的多个抽象方法，完成具体产品的创建。（AFactory和BFactory）

- 抽象产品：定义了产品的规范，描述了产品的主要特性和功能，抽象工厂模式有多个抽象产品。（Car和Bike）

- 具体产品：实现了抽象产品角色所定义的接口，由具体工厂来创建，它 同具体工厂之间是多对一的关系。（ACar、ABike、BCar和BBike）

### 单例模式

> 确保一个类最多只有一个实例，并提供一个全局访问点

有的时候我们希望一个对象在全局是唯一的，在程序的这次运行过程中，不管我什么时候、在哪里、多少次获取这个对象，它还是那个它。

听起来很简单，我们来看实现，单例的实现分为两种：

- 饿汉式：类加载就会导致该单实例对象被创建

- 懒汉式：类加载不会导致该单实例对象被创建，而是首次使用该对象时才会创建

饿汉式的话对象直接被创建，如果对象很大，而一时半会又没被用上，就会导致内存空间的浪费；懒汉的话不会导致内存空间的浪费，被用上了我才创建，但实现一般都较为复杂。

1. 饿汉式

   ```Java
   class EagerSingleton {
       private static EagerSingleton instance = new EagerSingleton();
       
       private EagerSingleton(){}
       
       public static EagerSingleton getInstance() {
           return instance;
       }
   }
   ```

   私有化构造，想获取对象只能调用getInstance()方法，而这个方法返回的对象是static静态成员变量，在类加载的时候就被创建。有的会把静态成员变量的赋值放到静态代码块中，两者没啥区别，都是随着类的加载被创建。

2. 懒汉式-方式1（线程不安全）

   ```Java
   class UnsafeThreadLazySingleton {
       private static UnsafeThreadLazySingleton instance;
       
       private UnsafeThreadLazySingleton(){}
       
       public static UnsafeThreadLazySingleton getInstance() {
           if (instance == null) {
               instance = new UnsafeThreadLazySingleton();
           }
           return instance;
       }
   }
   ```

   同样是私有化构造和静态成员变量的形式，但赋值操作被推迟到了getInstance()，只有第一次调用getInstance()时才会创建对象，但这显然是线程不安全的，多线程下可能导致instance被多次重新赋值。

3. 懒汉式-方式2（线程安全）

   ```Java
   class SafeThreadLazySingleton {
       private static SafeThreadLazySingleton instance;
       
       private SafeThreadLazySingleton(){}
       
       public static SafeThreadLazySingleton getInstance() {
           synchronized (SafeThreadLazySingleton.class) {
               if (instance == null) {
                   instance = new SafeThreadLazySingleton();
               }
           }
           return instance;
       }
   }
   ```

   我们自然想到加锁来保证线程安全，但这又引发了性能问题——我每次要获取对象时还要经过一层繁重的锁检验？要知道，我只是在首次使用需要创建对象的时候才可能引发并发问题，对象一旦被创建完毕，if (instance == null) 如同虚设，永远不成立，我锁住了一段”无效代码“？

4. 懒汉式-方式3（双重检查锁）

   ```Java
   class DCLLazySingleton {
       private static volatile DCLLazySingleton instance;
       
       private DCLLazySingleton(){}
       
       public static DCLLazySingleton getInstance() {
           if (instance == null) {
               synchronized (DCLLazySingleton.class) {
                   if (instance == null) {
                       instance = new DCLLazySingleton();
                   }
               }
           }
           return instance;
       }
   }
   ```

   直接加锁会导致性能问题，我们就改变加锁的条件，只有instance为null的时候，也就是首次使用需要创建对象的时候，我们才进入锁竞争，对象创建的环节。代码中出现了两个if判断，这就是大名鼎鼎的DCL，double-checked locking 双检锁。

   第一个if判断：判断是否是第一次获取对象，因为是懒汉式，如果是第一次获取对象，就进入创建对象环节，否则直接返回对象——要知道if判断可比锁竞争轻量多了

   第二个if判断：第一个if判断可能放进来很多狼（线程），这些狼（线程）被门（锁）挡在外面，第一个进来的狼直接取走了肉（创建了对象），但门（锁）外的狼（线程）不知道啊，等第一个狼（线程）走的时候，门（锁）外的狼（线程）又进来一头，这时候需要用**第二个if**告诉它，你走吧，肉（对象）已经被取走（创建）了，你别再取（创建）了，我只有一块肉（只要一个对象）。

   可以看到，这里的instance是被volatile修饰的，这涉及到对象的创建过程，指令重排等问题，被volatile修饰后可以禁止指令重排，保证**对象的创建是完整的，不会出现空指针问题**。

5. 懒汉式-方式4（静态内部类方式）

   ```Java
   class StaticInnerClass {
       private StaticInnerClass(){}
       
       private static class StaticInnerClassHolder {
           private static final StaticInnerClass INSTANCE = new StaticInnerClass();
       }
       
       public static StaticInnerClass getInstance() {
           return StaticInnerClassHolder.INSTANCE;
       }
   }
   ```

   JVM 在加载外部类的过程中，是不会加载静态内部类的，只有内部类的属性/方法被调用时才会被加载，并初始化其静态属性。也就是说只有第一次调用getInstance()方法时，涉及到静态内部类，这时StaticInnerClassHolder才会被加载，加载时初始化其静态属性INSTANCE，同时被getInstance()方法返回。由于是类加载的形式，所以是线程安全的，同时避免了使用锁，性能也客观。

6. 枚举

   ```Java
   enum EnumSingleton {
       INSTANCE;
   }
   ```

   十分简洁优雅，唯一不会被破坏的单例模式，绝对单例，线程安全。属于饿汉式。

我们来看上述单例如何获取：

```Java
class Main {
    public static void main(String[] args) {
        UnsafeThreadLazySingleton instance1 = UnsafeThreadLazySingleton.getInstance();
        SafeThreadLazySingleton instance2 = SafeThreadLazySingleton.getInstance();
        EagerSingleton instance3 = EagerSingleton.getInstance();
        DCLLazySingleton instance4 = DCLLazySingleton.getInstance();
        StaticInnerClass instance5 = StaticInnerClass.getInstance();
        EnumSingleton instance6 = EnumSingleton.INSTANCE;
    }
}
```

单例模式中单例类中一般都有其余方法，获取单例后可以使用这些方法。

破坏单例：

- 序列化与反序列化：单例类实现Serializable接口，将对象写入文件后读取，每次读取到的是不同对象
- 反射：将无参构造通过反射设置为可见，然后创建对象，创建得到的是不同对象

反破坏单例：

- 序列化与反序列化：单例类中定义private Object readResolve()方法，方法返回单例对象。因为在反序列化时如果类中有个叫readResolve的方法，就会执行这个方法并返回结果。
- 反射：私有构造方法进行单例对象的非空判断即可，如果不为空，说明已经存在单例对象了，还想反射创建新的单例对象是不允许的，抛异常；为空，允许创建。

单例模式较为简单，但实现方法较多，需要根据不同场景下选择不同方式实现。