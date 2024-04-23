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

> 对类的实例化过程进行了抽象，能够将软件模块中对象的创建和对象的使用分离。

**说人话**其实就是***创建对象用的模式***



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

而此时，如果新增了一个CCar，我只需去工厂类switch新增一个分支，然后修改CAR就可以把用到Car的地方全部换成CCar。但也正因为需要**修改工厂类**，这违背了开放封闭原则，简单工厂并没有成为23种设计模式之一（其实23种设计模式也并不一定都满足设计模式的[六大原则](#设计模式六大原则)）。

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

我们想想，A工厂和B工厂都能生产相同的抽象产品Car，那他们应该有个共同的工厂抽象接口：

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

可以看到，**每个工厂只生产自己的产品**，确实没有了switch判断，来看看外部如何获取对象：

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

那脱裤子放屁的事怎么说？原本是直接new一个对象，现在倒好，先new一个工厂，再从工厂获取对象，说到底还得在客户端new，需求改变还得修改客户端代码。这就涉及到工厂方法的真正优势所在：**它提供了一种灵活的方式来管理对象的创建过程**，现实场景中要获取对象不只是new那么简单，产品的具体创建过程是较为复杂的，而这些复杂的过程都被封装到了服务端的工厂类中，对于客户端来说，只需要知道我想要的产品从哪个工厂中生产，再把那个工厂new出来就行（实际上有了Spring中的IoC，工厂都是隐式的），再调用里面的方法获取对象就行，要替换产品的话只需要替换工厂就行。

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

这种模式的缺点也很明显，如果我要在产品族中新增一个产品，例如我想新增一个电瓶车，那么所有工厂（A工厂和B工厂）都需要新增生产这个产品的实现，这其实违背了开闭原则。

优点则是当一个产品族中的多个对象被设计成一起工作时，它能保证客户端始终只使用同一个产品族中的对象（我就想用A公司的一套产品，或者我就想用B工厂的一套产品，我不想a产品用A公司的，b产品用B公司的）。

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

   第一个if判断：判断是否是第一次获取对象，因为是懒汉式，如果是第一次获取对象，就进入创建对象环节，否则直接返回对象——if判断比锁竞争轻量多了

   第二个if判断：高并发下第一个if判断可能放进来很多线程，比如A、B、C，这些线程被锁阻塞住，只有一个线程能进入临界区，比如线程A进来了，创建了对象，等A走的时候，B或者C又进来一个，这时候需要用**第二个if**告诉它，你走吧，对象已经被创建了，你别再创建了，我只要一个对象就够了。

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



### 原型模式

> 用一个已经创建的实例作为原型，通过复制该原型对象来创建一个和原型对象相同的新对象。

假如我在使用一个对象，用着正爽呢，这时别人说你对象给我也爽爽，我又不太想把自己对象分享给他，万一他把我对象的属性给弄乱了怎么办？这时候就需要把我们的对象克隆一份给对方爽爽，两者互不干扰。

怎么克隆呢？先定义一个接口：

```Java
public interface Cloneable {
    protected Object clone();
}
```

而Java中JDK也给出了Cloneable接口，且为空，即：

```Java
public interface Cloneable {
}
```

因为Object中有clone()方法，子类直接重写即可，Cloneable只需要定义规范，不需要定义方法

让对象类实现这个接口：

```Java
class Person implements Cloneable {
    private String name;
    private int age;
    
    public Person() {}
    
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    @Override
    public Person clone() throws CloneNotSupportedException {
        return (Person) super.clone();
    }
}
```

这里方便起见我并没有展示get和set等方法。重写的clone方法直接调用父类（Object）中的方法即可。

来看看客户端怎么使用：

```Java
class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        Person zhangsan = new Person("张三", 23);
        Person zhangsanClone = zhangsan.clone();
        System.out.println(zhangsan.toString());
        System.out.println(zhangsanClone.toString());
    }
}
```

这时zhangsan和zhangsanClone是同一个类的两个不同的对象，但其内部的属性是一样的。

原型模式的克隆分为浅克隆和深克隆，又叫浅拷贝和深拷贝。

>浅克隆：创建一个新对象，新对象的属性和原来对象完全相同，对于非基本类型属性，仍指向原有属性所指向的对象的内存地址。
>
>深克隆：创建一个新对象，属性中引用的其他对象也会被克隆，不再指向原有对象地址。

**说人话**其实就是浅克隆中，母体和克隆体的***引用数据类型***还是指向原来的相同对象；深克隆中，母体和克隆体的***引用数据类型***也进行了深克隆，是不同的对象。

上述直接使用Object中的clone方法属于浅克隆，要进行深克隆的话将对象写入文件后再读取即可。

我们再回头看原型模式的定义：

> 用一个已经创建的实例作为原型，通过复制该原型对象来创建一个和原型对象相同的新对象。

**说人话**其实就是***将一个对象克隆一份，获得新对象，新对象和原对象的内部属性相同***

我们再看看原型模型中出现的角色：

- 抽象原型类：规定了具体原型对象必须实现的的 clone() 方法。（Cloneable）

- 具体原型类：实现抽象原型类的 clone() 方法，它是可被复制的对象。（Person）

- 访问类：使用具体原型类中的 clone() 方法来复制新的对象。（Main）

  

### 建造者模式

> 使用多个简单的对象一步步构建成一个复杂的对象，将一个复杂对象的构建与表示分离，使得同样的构建过程可以创建不同的表示。

先不管定义，我们知道一台电脑主机，里面有cpu，显卡，内存，硬盘等，这些组件(简单对象)是不会变的，但每个简单的对象有不同品牌，不同品牌的组合可以组合成不同主机(复杂对象)，这里我们先来一个电脑类：

```Java
class Computer {
    private String audio;
    private String keyboard;
    private String master;
    private String mouse;
    private String screen;
}
```

这里方便起见，没有展示get和set等方法，我们知道电脑由音响，键盘，主机，鼠标，显示器组成就行了。

我们来看一个抽象的电脑组装类：

```Java
abstract class ComputerBuilder {
    Computer computer = new Computer();
    
    public Computer getComputer() {
        return computer;
    }
    
    public abstract void buildAudio();
    public abstract void buildKeyboard();
    public abstract void buildMaster();
    public abstract void buildMouse();
    public abstract void buildScreen();
}
```

这里具体组件的构件都是抽象方法，我们来看具体的构建类是怎么组装一台电脑的：

```Java
class HPComputerBuilder extends ComputerBuilder{
    @Override
    public void buildAudio() {
        computer.setAudio("hp音响");
    }
    @Override
    public void buildKeyboard() {
        computer.setKeyboard("hp键盘");
    }
    @Override
    public void buildMaster() {
        computer.setMaster("hp主机");
    }
    @Override
    public void buildMouse() {
        computer.setMouse("hp鼠标");
    }
    @Override
    public void buildScreen() {
        computer.setScreen("hp显示器");
    }
}
```

这里定义一个HP电脑建造者，将父类的computer中的每个简单对象构建出来，但具体怎么组装其实是不归它管的，我们需要再来个指挥者：

```Java
class Director {
    private ComputerBuilder computerBuilder;

    public Director(ComputerBuilder computerBuilder) {
        this.computerBuilder = computerBuilder;
    }

    public Computer constructComputer() {
        computerBuilder.buildMaster();
        computerBuilder.buildAudio();
        computerBuilder.buildKeyboard();
        computerBuilder.buildMouse();
        computerBuilder.buildScreen();
        return computerBuilder.getComputer();
    }
}
```

往指挥者传入建造者后，指挥者有个方法指挥建造者如何组装，一般来说有**顺序要求**，先组装这个，再组装那个，这些顺序的控制就由指挥者的这个方法来控制，指挥者只管指挥顺序组装，创建组件的任务是建造者的。

来看客户端如何获取到一台电脑：

```Java
class Main {
    public static void main(String[] args) {
        // 指挥者传入不同建造者即可建造同一复杂产品的不同组合
        Director director = new Director(new HPComputerBuilder());
        Computer computer = director.constructComputer();
        System.out.println(computer.toString());
    }
}
```

往指挥者传入建造者，然后指挥一下，就获取到对象了，我想新增一种组合，只需要新增一个建造者：

```Java
class DELLComputerBuilder extends ComputerBuilder{
    @Override
    public void buildAudio() {
        computer.setAudio("dell音响");
    }
    @Override
    public void buildKeyboard() {
        computer.setKeyboard("dell键盘");
    }
    @Override
    public void buildMaster() {
        computer.setMaster("dell主机");
    }
    @Override
    public void buildMouse() {
        computer.setMouse("dell鼠标");
    }
    @Override
    public void buildScreen() {
        computer.setScreen("dell显示器");
    }
}
```

再往指挥者传入这个建造者对象，就可以获取到同一复杂产品（电脑）相同建造顺序（指挥者指挥顺序）的不同简单产品（电脑组件）的组合。

建造者模式优点：

- 建造者模式的封装性很好。使用建造者模式可以有效的封装变化，在使用建造者模式的场景中，一般产品类和建造者类是比较稳定的，因此，将主要的业务逻辑封装在指挥者类中对整体而言可以取得比较好的稳定性。

- 在建造者模式中，客户端不必知道产品内部组成的细节，将产品本身与产品的创建过程解耦，使得相同的创建过程可以创建不同的产品对象。

- 可以更加精细地控制产品的创建过程 。将复杂产品的创建步骤分解在不同的方法中，使得创建过程更加清晰，也更方便使用程序来控制创建过程。

- 建造者模式很容易进行扩展。如果有新的需求，通过实现一个新的建造者类就可以完成，基本上不用修改之前已经测试通过的代码，因此也就不会对原有功能引入风险。符合开闭原则。

缺点：

- 建造者模式所创建的产品一般具有较多的共同点，其组成部分相似，如果产品之间的差异性很大，则不适合使用建造者模式，因此其使用范围受到一定的限制。

建造者模式和工厂方法模式对比：

> 工厂方法模式注重的是整体对象的创建方式；而建造者模式注重的是部件构建的过程，意在通过一步一步地精确构造创建出一个复杂的对象。
>
> 我们举个简单例子来说明两者的差异，如要制造一个超人，如果使用工厂方法模式，直接产生出来的就是一个力大无穷、能够飞翔、内裤外穿的超人；而如果使用建造者模式，则需要组装手、头、脚、躯干等部分，然后再把内裤外穿，于是一个超人就诞生了。

我们再回头看建造者模式定义：

> 使用多个简单的对象一步步构建成一个复杂的对象，将一个复杂对象的构建与表示分离，使得同样的构建过程可以创建不同的表示。

**说人话**其实就是***一个复杂对象有多个简单对象，我搞个建造者，这个建造者来创建每个简单对象，再来个指挥者，指挥者来把这些简单对象按照顺序来组合成一个复杂对象***

我们再看建造者模式出现的角色：

- 抽象建造者类：这个接口规定要实现复杂对象的那些部分的创建，并不涉及具体的部件对象的创建。 （ComputerBuilder）

- 具体建造者类：实现 Builder 接口，完成复杂产品的各个部件的具体创建方法。在构造过程完成后，提供产品的实例。 （HPComputerBuilder和DELLComputerBuilder）

- 产品类：要创建的复杂对象。（Computer）

- 指挥者类：调用具体建造者来创建复杂对象的各个部分，在指导者中不涉及具体产品的信息，只负责保证对象各部分完整创建或按某种顺序创建。 （Director）





## 结构型模式

> 结构型模式描述如何将类或对象按某种布局组成更大的结构。它分为类结构型模式和对象结构型模式，前者采用继承机制来组织接口和类，后者釆用组合或聚合来组合对象。

**说人话**其实就是***搞类与类之间关系，以此来做大做强的***



### 适配器模式

> 将一个类的接口转换成客户希望的另外一个接口，使得原本由于接口不兼容而不能一起工作的那些类能一起工作。

我们知道，电脑一般只能读取SD卡（大大的那个，插电脑上的），要读TF卡（小小的那个，插mp4上的）的话需要用转换器，转换器不止能把TF卡转成SD接口，你想转成USB都行......

那来吧，SDCard 和 TFCard：

```Java
interface SDCard {
    String readSD();
    void writeSD(String msg);
}

interface TFCard {
    String readTF();
    void writeTF(String msg);
}
```

SDCard 和 TFCard的具体实现：

```Java
class SDCardImpl implements SDCard {
    @Override
    public String readSD() {
        return "read SD card";
    }
    
    @Override
    public void writeSD(String msg) {
        System.out.println("write SD card:" + msg);
    }
}

class TFCardImpl implements TFCard {
    @Override
    public String readTF() {
        return "read TF card";
    }
    
    @Override
    public void writeTF(String msg) {
        System.out.println("write TF card:" + msg);
    }
}
```

再来个电脑，嗯......只能读取SD卡的电脑：

```Java
class Computer {
    public String readSD(SDCard sdCard) {
        return sdCard.readSD();
    }
    
    public void writeSD(SDCard sdCard, String msg) {
        sdCard.writeSD(msg);
    }
}
```

我们现在的需求是，我要让电脑读取TFCard，但显然TFCard是传不到电脑的两个方法里的，方法只认SDCard！我们需要一个转换器，也就是适配器，适配器的实现分为两种——类适配器模式和对象适配器模式。

1. 类适配器模式

   类适配器模式的实现是**实现目标接口**（SDCard），**继承原本的类**（TFCard）。实现了目标接口，根据多态，电脑就”认“它，能识别它；继承原本的类，表示适配器运行的还是原本方法（TFCard里的数据）。

   > 想想转换器是不是外观上像SDCard，大大的，接口也和SD卡一样（实现接口），但还是需要插入一张TFCard（继承原本的类）才有数据

   ```Java
   class AdapterTF2SD extends TFCardImpl implements SDCard{ // 实现目标类，继承原本类
       // 重写目标类接口方法
       @Override
       public String readSD() {
           // 但内部偷梁换柱，换成了原本类的方法
           return readTF();
       }
       
       @Override
       public void writeSD(String msg) {
           writeTF(msg);
       }
   }
   ```

   虽然方法名是读SD卡（实现SDCard接口），但里面具体实现已经被偷梁换柱成了TFCard的读取方法（继承了TFCardImpl类），写入方法同理。

   来看看客户端的调用：

   ```Java
   class Main {
       public static void main(String[] args) {
           Computer computer = new Computer();
           
           SDCardImpl sdCard = new SDCardImpl();
           
           // SD卡正常读写没问题
           System.out.println(computer.readSD(sdCard));
           computer.writeSD(sdCard, "write to sd");
           
           // 创建适配器，适配器因为继承关系其实相当于已经插入了一张TF卡
           AdapterTF2SD adapter = new AdapterTF2SD();
           System.out.println(computer.readSD(adapter));
           computer.writeSD(adapter, "write to adapter");
       }
   }
   ```

2. 对象适配器模式

   还是需要适配器去实现SDCard接口，这样才能被电脑识别，但不再继承TFCard，而是内聚一个TFCard的对象（其实这才像是往转换器里插入一张TF卡，随时可以替换TF卡，而类适配器模式创建出适配器后TF卡就相当于固定了）：

   ```Java
   class AdapterTF2SD2 implements SDCard{
       private TFCard tfCard;
       
       public AdapterTF2SD2(TFCard tfCard) {
           this.tfCard = tfCard;
       }
       
       @Override
       public String readSD() {
           return tfCard.readTF();
       }
       
       @Override
       public void writeSD(String msg) {
           tfCard.writeTF(msg);
       }
   }
   ```

   同样可以把重写SDCard接口的两个方法内部具体实现偷梁换柱，换成TFCard的，来看客户端代码：

   ```Java
   class Main {
       public static void main(String[] args) {
           Computer computer = new Computer();
   
           AdapterTF2SD2 adapter2 = new AdapterTF2SD2(new TFCardImpl());
           System.out.println(computer.readSD(adapter2));
           computer.writeSD(adapter2, "write to adapter2");
       }
   }
   ```

我们再回头看适配器模式定义：

> 将一个类的接口转换成客户希望的另外一个接口，使得原本由于接口不兼容而不能一起工作的那些类能一起工作。

**说人话**其实就是***你的接口不接受我，那我就实现你的接口，但方法逻辑还是走我的，这下你接受不？***

我们再来看适配器模式出现的角色：

- 目标接口：当前系统业务所期待的接口，它可以是抽象类或接口。（SDCard）

- 适配者类：它是被访问和适配的现存组件库中的组件接口。（TFCard）

- 适配器类：它是一个转换器，通过继承或引用适配者的对象，把适配者接口转换成目标接口，让客户按目标接口的格式访问适配者。（AdapterTF2SD 和 AdapterTF2SD2）

  

### 装饰者模式

> 指在不改变现有对象结构的情况下，动态地给该对象增加一些职责（即增加其额外功能）的模式。

现有一家快餐店，有炒面、炒饭这些快餐，同时可以额外附加鸡蛋、培根这些配菜，当然加配菜需要额外加钱，每个配菜的价钱通常不太一样，那么计算总价就会显得比较麻烦。

按照传统方法，我们需要有”加鸡蛋的炒面“、”加火腿的炒面“、”加鸡蛋的炒饭“和”加火腿的炒饭“这些类。现在如果我需要新增一个炒粉主食呢，因为有两个配菜，需要增加两个类，如果又要再加火腿配菜呢，因为有三个主食了，需要增加三个类，如果一个主食可以不只放一个配菜呢，”火腿鸡蛋炒面“，”鸡蛋培根炒粉“······

透过现象看本质，其实上述案例的主角是主食，配菜都是主食的一些附加功能，**加了配菜的主食仍然是主食**。既然如此，先来一个食物类：

```Java
abstract class FastFood {
    private float price;
    
    private String desc;

    public abstract float cost();
}
```

为方便起见，并没有展示get和set等方法，我们只看关键代码——主食有价钱和描述两个属性，同时有个计算当前主食价格的方法，这里的cost()计算得到的价格并不一定为price，因为可能有配菜。

再来个炒面类，是个主食：

```Java
class FriedNoodles extends FastFood {
    public FriedNoodles() {
        super(12, "炒面");
    }
    
    public float cost() {
        return getPrice();
    }
}
```

没有附加配菜的炒面价格就是其本身，所以cost方法就是返回price。

我们再来想配菜类应该怎么处理，前面我们说到**加了配菜的主食仍然是主食**，那么配菜应该是这样的：接收一个主食，并能够返回一个主食，主食还是那个主食，但已经被加了小料（被附加了功能），所以配菜也应该继承食物类：

```Java
abstract class Garnish extends FastFood {
    private FastFood fastFood;
    
    public Garnish(FastFood fastFood, float price, String desc) {
        super(price,desc);
        this.fastFood = fastFood;
    }
}
```

来看具体的配菜类：

```Java
class Egg extends Garnish {
    public Egg(FastFood fastFood) {
        // 接收一个主食，还有自己的价钱和描述（对主食进行功能增强）
        super(fastFood, 1, "鸡蛋");
    }
    
    @Override
    public float cost() {
        return getPrice() + getFastFood().getPrice();
    }
    
    @Override
    public String getDesc() {
        //打印信息，如鸡蛋炒面，也就是对原功能进行增强
        return super.getDesc() + getFastFood().getDesc();
    }
}
```

可以看到配菜的构造方法是接收一个主食，同时重写了主食的方法，调用了自己的getPrice()，又调用了主食的getPrice()，这就是对主食的getPrice()的增强，为其附加了自己的功能(自己的getPrice())，getDesc()方法的增强同理。同时他也是继承于FastFood，本质上还是个主食，可以继续被增强。

直接看客户端代码：

```Java
class Main {
    public static void main(String[] args) {
        //点一份炒饭
        FastFood food = new FriedNoodles();
        //花费的价格
        System.out.println(food.getDesc() + " " + food.cost() + "元");

        //点一份加鸡蛋的炒饭
        FastFood food1 = new FriedNoodles();
        // 配菜接收主食，同时返回的也是主食，可以被food1重新接收，但里面方法的功能已经被增强了
        food1 = new Egg(food1);
        //花费的价格
        System.out.println(food1.getDesc() + " " + food1.cost() + "元");
    }
}
```

如果想要扩展主食，如炒饭，只需要增加一个炒饭类就行，要新增配菜，也只需要新增一个配菜类就行，而主食加的配菜，方法的增强都是可以灵活变通的，想加两个鸡蛋都不成问题~

我们再回头看装饰者模式的定义：

> 指在不改变现有对象结构的情况下，动态地给该对象增加一些职责（即增加其额外功能）的模式。

**说人话**其实就是***炒饭可以加配料，加了配料的炒饭还是炒饭！***

我们再看装饰者模式出现的角色：

- 抽象构件：定义一个抽象接口以规范准备接收附加责任的对象。（FastFood）

- 具体构件 ：实现抽象构件，通过装饰角色为其添加一些职责。（FiredNoodles）

- 抽象装饰 ： 继承或实现抽象构件，并包含具体构件的实例，可以通过其子类扩展具体构件的功能。（Garnish）

- 具体装饰 ：实现抽象装饰的相关方法，并给具体构件对象添加附加的责任。（Egg）

  

### 代理模式

> 一个类代表另一个类的功能

代理分为静态代理和动态代理，我们先来看静态代理：

1. 静态代理

   现有一个卖票接口：

   ```Java
   interface SellTickets {
       void sell();
   }
   ```

   我们可以从火车站买票：

   ```Java
   class TrainStation implements SellTickets {
       public void sell() {
           System.out.println("火车站卖票");
       }
   }
   ```

   也可以从代售点买票：

   ```Java
   class ProxyPoint implements SellTickets {
       private TrainStation station = new TrainStation();
       
       public void sell() {
           System.out.println("代理点收取一些服务费用");
           station.sell();
       }
   }
   ```

   从代理点可以对原对象（火车站）的卖票方法进行一些增强（收取一些服务费用）。

   在某些情况下，我们以后买票就可以从代理对象（代理点）处买票，实行被增强的方法，代理类是提前写好的，所以叫静态代理。

2. 动态代理

   动态代理分为JDK代理和CGLIB代理

   1. JDK代理

      仍然是上述接口和火车站，我们可以通过JDK自带的Proxy类中的newProxyInstance()直接获取代理对象：

      ```Java
      class JDKProxyFactory {
      
          private TrainStation station = new TrainStation();
      
          public SellTickets getProxyObject() {
              //使用Proxy获取代理对象
              /*
                  newProxyInstance()方法参数说明：
                      ClassLoader loader ： 类加载器，用于加载代理类，使用真实对象的类加载器即可
                      Class<?>[] interfaces ： 真实对象所实现的接口，代理模式真实对象和代理对象实现相同的接口
                      InvocationHandler h ： 代理对象的调用处理程序
               */
              return (SellTickets) Proxy.newProxyInstance(
                      station.getClass().getClassLoader(),
                      station.getClass().getInterfaces(),
                      new InvocationHandler() {
                          /*
                              InvocationHandler中invoke方法参数说明：
                                  proxy ： 代理对象
                                  method ： 对应于在代理对象上调用的接口方法的 Method 实例
                                  args ： 代理对象调用接口方法时传递的实际参数
                           */
                          public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                              System.out.println("代理点收取一些服务费用(JDK动态代理方式)");
                              //执行真实对象
                              Object result = method.invoke(station, args);
                              return result;
                          }
                      });
          }
      }
      ```

      JDK代理要求被代理的对象需要实现接口。

   2. CGLIB代理

      JDK代理需要被代理的对象实现接口，这就限制了其使用范围，CGLIB代理则不需要代理对象实现接口，但其由第三方提供，并非JDK自带

      ```xml
      <dependency>
          <groupId>cglib</groupId>
          <artifactId>cglib</artifactId>
          <version>2.2.2</version>
      </dependency>
      ```

      由代理工厂实现MethodInterceptor接口，方法的增强实现在intercept中

      ```Java
      class CGLIBProxyFactory implements MethodInterceptor {
          private TrainStation target = new TrainStation();
          public TrainStation getProxyObject() {
              //创建Enhancer对象，类似于JDK动态代理的Proxy类，下一步就是设置几个参数
              Enhancer enhancer =new Enhancer();
              //设置父类的字节码对象
              enhancer.setSuperclass(target.getClass());
              //设置回调函数
              enhancer.setCallback(this);
              //创建代理对象
              return (TrainStation) enhancer.create();
          }
      
          /*
              intercept方法参数说明：
                  o ： 代理对象
                  method ： 真实对象中的方法的Method实例
                  args ： 实际参数
                  methodProxy ：代理对象中的方法的method实例
           */
          public TrainStation intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
              System.out.println("代理点收取一些服务费用(CGLIB动态代理方式)");
              TrainStation result = (TrainStation) methodProxy.invoke(target, args);
              // 另一种方式，不需要目标对象实例
              // TrainStation result = (TrainStation) methodProxy.invokeSuper(o, args);
              return result;
          }
      }
      ```

客户端调用：

```Java
class Main {
    public static void main(String[] args) {
        // 静态代理
        ProxyPoint pp = new ProxyPoint();
        pp.sell();
        // JDK动态代理
        JDKProxyFactory jdkProxyFactory = new JDKProxyFactory();
        SellTickets proxyObject = jdkProxyFactory.getProxyObject();
        proxyObject.sell();
        // CGLIB动态代理
        CGLIBProxyFactory cglibProxyFactory = new CGLIBProxyFactory();
        TrainStation proxyObject1 = cglibProxyFactory.getProxyObject();
        proxyObject1.sell();
    }
}
```

使用CGLib实现动态代理，CGLib底层采用ASM字节码生成框架，使用字节码技术生成代理类，在JDK1.6之前比使用Java反射效率要高。唯一需要注意的是，CGLib不能对声明为final的类或者方法进行代理，因为CGLib原理是动态生成被代理类的子类。
 在JDK1.6、JDK1.7、JDK1.8逐步对JDK动态代理优化之后，在调用次数较少的情况下，JDK代理效率高于CGLib代理效率，只有当进行大量调用的时候，JDK1.6和JDK1.7比CGLib代理效率低一点，但是到JDK1.8的时候，JDK代理效率高于CGLib代理。所以如果有接口使用JDK动态代理，如果没有接口使用CGLIB代理。

我们再回头看代理模式定义：

> 一个类代表另一个类的功能

**说人话**其实就是***当前类的功能我想用，但我觉得还不太行，我想对其增强，我就创个plus版本的代理类，代理类有着增强后的方法***

我们再看代理模式中出现的角色：

- 抽象主题类： 通过接口或抽象类声明真实主题和代理对象实现的业务方法。（SellTickets）

- 真实主题类： 实现了抽象主题中的具体业务，是代理对象所代表的真实对象，是最终要引用的对象。（TrainStation）

- 代理类 ： 提供了与真实主题相同的接口，其内部含有对真实主题的引用，它可以访问、控制或扩展真实主题的功能。（JDKProxyFactory和CGLIBProxyFactory中获取的代理对象）

  

### 外观模式

> 又名门面模式，是一种通过为多个复杂的子系统提供一个一致的接口，而使这些子系统更加容易被访问的模式

炒股是个大学问，你可以购买多支股票，抛出多支股票，有时还想买点国债，卖点国债，玩玩房地产......如果这些子系统都需要你亲力亲为来一一操作的话，是很麻烦的，我们一般找个基金帮我们管理，可以进行一次性全部买入，一次性全部卖出等操作

需要操作的子系统：

```Java
class Stock1 {
    public void sell() {
        System.out.println("股票1卖出");
    }
    public void buy() {
        System.out.println("股票1买入");
    }
}

class Stock2 {
    public void sell() {
        System.out.println("股票2卖出");
    }
    public void buy() {
        System.out.println("股票2买入");
    }
}

class Stock3 {
    public void sell() {
        System.out.println("股票3卖出");
    }
    public void buy() {
        System.out.println("股票3买入");
    }
}

class Realty1 {
    public void sell() {
        System.out.println("房地产1卖出");
    }
    public void buy() {
        System.out.println("房地产1买入");
    }
}

class NationalDebt1 {
    public void sell() {
        System.out.println("国债1卖出");
    }
    public void buy() {
        System.out.println("国债1买入");
    }
}
```

基金类：

```Java
class Fund {
    Stock1 stock1;
    Stock2 stock2;
    Stock3 stock3;
    NationalDebt1 nationalDebt1;
    Realty1 realty1;
    
    public Fund() {
        stock1 = new Stock1();
        stock2 = new Stock2();
        stock3 = new Stock3();
        nationalDebt1 = new NationalDebt1();
        realty1 = new Realty1();
    }
    
    public void buyFund() {
        stock1.buy();
        stock2.buy();
        stock3.buy();
        nationalDebt1.buy();
        realty1.buy();
    }
    
    public void sellFund() {
        stock1.sell();
        stock2.sell();
        stock3.sell();
        nationalDebt1.sell();
        realty1.sell();
    }
}
```

可以看到，基金类可以**一键**帮我们操作对应的买入和卖出操作，我们只需要把东西交给基金管理即可，客户端代码也是十分简单：

```Java
class Main {
    public static void main(String[] args) {
        Fund fund = new Fund();
        fund.buyFund();
        fund.sellFund();
    }
}
```

到这里，我们清楚了，外观模式中的外观其实就是我们和子系统之间沟通的入口，我们只能也只需要通过这个入口对子系统进行操作就行了，无需了解复杂的子系统内部结构。

我们再回头看外观模式定义：

> 又名门面模式，是一种通过为多个复杂的子系统提供一个一致的接口，而使这些子系统更加容易被访问的模式

**说人话**其实就是***找个管家帮我管理下家中事务，有什么事我只需要跟管家沟通即可***

我们再看外观模式中出现的角色：

- 外观角色：为多个子系统对外提供一个共同的接口。

- 子系统角色：实现系统的部分功能，客户可以通过外观角色访问它。

  

### 桥接模式

> 将抽象与实现分离，使它们可以独立变化。它是用组合关系代替继承关系来实现，从而降低了抽象和实现这两个可变维度的耦合度。

现按照手机品牌划分，有OPPO，Vivo等等，每个手机发展自己的软件，软件继承于手机，分为OPPOGame，VivoGame，OppoAppStore， VivoAppStore......那么每增加一个手机，就需要对所有软件进行一个实现，每新增一个软件，就需要让所有手机实现它。

如果按照手机软件划分，有Game，AppStore等等，每个软件都有不同的手机上的实现，手机实现继承于软件，分为GameOppo，GameVivo，AppStoreOppo，AppstoreVivo......嗯......和上面的问题如出一辙。

造成这种现象的原因其实是软件和手机品牌是通过继承关系实现的，两者耦合程度太高。不妨试试内聚的实现。

软件的接口：

```Java
interface Software {
    void run();
}
```

抽象的手机：

```Java
abstract class Phone {
    protected Software software; // 内聚了一个软件
    
    public void setSoftware(Software software) {
        this.software = software;
    }
    
    public abstract void run();
}
```

手机的具体实现：

```Java
class Oppo extends Phone {
    @Override
    public void run() {
        System.out.print("Oppo:");
        software.run();
    }
}

class Vivo extends Phone {
    @Override
    public void run() {
        System.out.print("Vivo:");
        software.run();
    }
}
```

具体软件实现：

```Java
class AppStore implements Software {
    @Override
    public void run() {
        System.out.println("Appstore run");
    }
}

class Game implements Software {
    @Override
    public void run() {
        System.out.println("game run");
    }
}
```

这样，我们可以往手机里传不同的软件就可以直接运行：

```Java
class Main {
    public static void main(String[] args) {
        AppStore appStore = new AppStore();
        Game game = new Game();
        
        Oppo oppo = new Oppo();
        Vivo vivo = new Vivo();
        
        oppo.setSoftware(appStore);
        oppo.run();
        oppo.setSoftware(game);
        oppo.run();
        vivo.setSoftware(appStore);
        vivo.run();
        vivo.setSoftware(game);
        vivo.run();
    }
}
```

继承的耦合使程序的扩展显得麻烦，那两者就通过聚合关系解除耦合，我想新增一个软件无需管手机的事，只管根据软件接口写软件运行的代码就可以了，不管是什么手机，都能根据我的那个接口运行我，而新增手机也只需要内聚一个软件对象，知道软件运行的接口就可以运行传进来的软件，代码显得十分灵活。

我们再来看桥接模式的定义：

>将抽象与实现分离，使它们可以独立变化。它是用组合关系代替继承关系来实现，从而降低了抽象和实现这两个可变维度的耦合度。

**说人话**其实就是***两个类的”融合“不要通过继承了，这样不好扩展，用聚合+接口的形式***

我们再看桥接模式出现的角色：

- 抽象化：定义抽象类，并包含一个对实现化对象的引用。（Phone）

- 扩展抽象化：是抽象化角色的子类，实现父类中的业务方法，并通过组合关系调用实现化角色中的业务方法。（Oppo 和 Vivo）

- 实现化：定义实现化角色的接口，供扩展抽象化角色调用。（Software）

- 具体实现化：给出实现化角色接口的具体实现。（Appstore 和Game）

  

### 组合模式

> 又名部分整体模式，是用于把一组相似的对象当作一个单一的对象。组合模式依据树形结构来组合对象，用来表示部分以及整体层次。这种类型的设计模式属于结构型模式，它创建了对象组的树形结构。

打开你的电脑硬盘，可以看到硬盘中既有文件夹又有文件，而文件夹里既有文件夹又有文件，文件夹里既有文件夹又有文件，文件夹里既有文件夹又有文件......无限套娃了是吧，假如文件夹和文件是无关系的对象，那么对象的管理显得十分麻烦，假如我要遍历一个文件夹里的内容，我需要对当前遍历到的对象进行**判断**，如果是文件夹，继续深入遍历，如果是文件，则读取。

那么能不能把文件夹和文件统一，抽象出一个**相同的访问方法**呢，组合模式来了：

把文件夹和文件抽象出一个接口：

```Java
interface Component {
    public void add(Component c);
    public void remove(Component c);
    public Component getChild(int i);
    public void operation();
}
```

文件，即叶子节点继承该接口：

```Java
class Leaf implements Component{
    private String name;
    
    public Leaf(String name) {
        this.name = name;
    }
    
    @Override
    public void add(Component c) {} // 文件不需要进行添加操作，方法空实体
    @Override
    public void remove(Component c) {} // 文件不需要进行删除操作，方法为空实体
    @Override
    public Component getChild(int i) {
        return null; // 文件不能根据索引获取对象，返回null
    }
    
    @Override
    public void operation() {
        System.out.println("树叶"+name+"：被访问！"); // 文件可以被访问
    }

}
```

因为是文件，所以新增、移除和获取下一层次的child是空方法，只需要重点实现被访问时的方法operation即可，再看文件夹的实现：

```Java
class Composite implements Component {
    private String name;
    
    private ArrayList<Component> children = new ArrayList<Component>();
    
    public Composite(String name) {
        this.name = name;
    }
    
    public void add(Component c) {
        children.add(c);
    }
    
    public void remove(Component c) {
        children.remove(c);
    }
    
    public Component getChild(int i) {
        return children.get(i);
    }
    
    public void operation() {
        // 文件夹的操作即遍历每一个子节点
        for (Component component : children) {
            component.operation();
        }
    }
}
```

因为是文件夹，可能里面还包含其它子节点，这些子节点可能是文件也可能还是文件夹，但无所谓，他们都是Component，文件夹都照收不误，而访问文件夹时，其实就是想对里面的每个Component访问，得了，遍历吧，反正不管子节点是文件夹还是文件，都是执行operation方法，多简单。

来看看客户端调用：

```Java
class Main {
    public static void main(String[] args) {
        Composite root = new Composite("根节点");
        root.add(new Leaf("叶子节点1"));
        root.add(new Leaf("叶子节点2"));
        root.add(new Leaf("叶子节点3"));
        Composite composite1 = new Composite("节点1");
        root.add(composite1);
        composite1.add(new Leaf("叶子节点4"));
        composite1.add(new Leaf("叶子节点5"));
        
        root.operation();
    }
}
```

我们再回头看组合模式的定义：

> 又名部分整体模式，是用于把一组相似的对象当作一个单一的对象。组合模式依据树形结构来组合对象，用来表示部分以及整体层次。这种类型的设计模式属于结构型模式，它创建了对象组的树形结构。

**说人话**其实就是***不管你是文件还是文件夹，我都把你们看成一种对象处理，你们去实现相同的接口，方便我建造树形结构和遍历***

我们再看组合模式中出现的角色：

- 抽象根节点：定义系统各层次对象的共有方法和属性，可以预先定义一些默认行为和属性。（Component）

- 树枝节点：定义树枝节点的行为，存储子节点，组合树枝节点和叶子节点形成一个树形结构。（Composite）

- 叶子节点：叶子节点对象，其下再无分支，是系统层次遍历的最小单位。（Leaf）

  

### 享元模式

> 运用共享技术来有效地支持大量细粒度对象的复用。它通过共享已经存在的对象来大幅度减少需要创建的对象数量、避免大量相似对象的开销，从而提高系统资源的利用率。

俄罗斯方块分为L型方块，T型方块，O型方块，I型方块等等，每个方块在一把游戏中又不止出现一次，一把游戏得创建多少个方块对象......我的老年机表示吃不消了；围棋棋盘有361个落点，一局游戏算上被提走的子，又要创建多少个棋子对象，围棋在线对局又同时进行着多少场对局......服务器表示吃不消了

上述情景其实都是重复创建了大量相似乃至相同的对象，我们就想，能不能把对象拿来复用？听起来好像和单例有点像，两者的区别我们稍后再说，先看享元实现俄罗斯方块共享，抽象方块类：

```Java
abstract class AbstractBox {
    public abstract String getShape();

    public void display(String color) {
        System.out.println("方块形状：" + this.getShape() + " 颜色：" + color);
    }
}
```

不同形状的方块：

```Java
class IBox extends AbstractBox {
    @Override
    public String getShape() {
        return "I";
    }
}

class LBox extends AbstractBox {
    @Override
    public String getShape() {
        return "L";
    }
}

class OBox extends AbstractBox {
    @Override
    public String getShape() {
        return "O";
    }
}
```

关键代码来了，我们需要一个工厂来复用对象，同时这个工厂最好是单例的，让我们在程序的整个运行生命周期都是同一个工厂：

```Java
class BoxFactory {
    private static HashMap<String, AbstractBox> map;
    
    private BoxFactory() {
        map = new HashMap<>();
        loadBoxes();
    }
    
    public static BoxFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }
    
    private static class SingletonHolder {
        private static final BoxFactory INSTANCE = new BoxFactory();
    }
    
    public AbstractBox getBox(String key) {
        return map.get(key);
    }

    private void loadBoxes() {
        Properties prop = new Properties();
        try (InputStream input = BoxFactory.class.getClassLoader().getResourceAsStream("flyweightconfig.properties")) {
            prop.load(input);
            for (String key : prop.stringPropertyNames()) {
                String className = prop.getProperty(key);
                try {
                    Class<?> clazz = Class.forName(className);
                    AbstractBox box = (AbstractBox) clazz.getDeclaredConstructor().newInstance();
                    map.put(key, box);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

这里我采取的是从配置文件从读取需要共享的类，并放入工厂的map中，配置文件信息如下：

```properties
I=com.example.designpattern.flyweight.IBox
L=com.example.designpattern.flyweight.LBox
O=com.example.designpattern.flyweight.OBox
```

可以看到对象被我们创建并放入到map中，客户端需要获取对象是可以复用对象的：

```Java
class Main {
    public static void main(String[] args) {
        BoxFactory factory = BoxFactory.getInstance();
        AbstractBox iBox1 = factory.getBox("I");
        AbstractBox iBox2 = factory.getBox("I");
        AbstractBox lBox1 = factory.getBox("L");
        AbstractBox lBox2 = factory.getBox("L");
        AbstractBox oBox1 = factory.getBox("O");
        AbstractBox oBox2 = factory.getBox("O");
        System.out.println(iBox1 == iBox2);
        System.out.println(lBox1 == lBox2);
        System.out.println(oBox1 == oBox2);
        iBox1.display("红色");
        iBox2.display("绿色");
        lBox1.display("蓝色");
        lBox2.display("紫色");
        oBox1.display("粉色");
        oBox2.display("白色");
    }
}
```

这里的iBox1和iBox2，lBox1和lBox2，oBox1和oBox2是同一个对象，但他们可以有不同的颜色展示。实现了对象的复用，减少内存开销。

我们再来看享元模式和单例模式的区别：

- 单例模式保证一个类只有一个实例，并提供一个全局访问点。常被用来**管理**一些共享的资源，比如数据库连接池、线程池等

- 享元模式是尽可能地**减少系统中的对象数量**，从而提高系统的性能。它通过共享具有相同状态的对象来达到这个目的。享元模式通常会定义一个工厂类来创建和管理共享的对象，而客户端在使用时只需要向工厂类请求共享对象即可。

我们再回头看享元模式的定义：

> 运用共享技术来有效地支持大量细粒度对象的复用。它通过共享已经存在的对象来大幅度减少需要创建的对象数量、避免大量相似对象的开销，从而提高系统资源的利用率。

**说人话**其实就是***重复利用对象，减少资源开销***

我们再回头看享元模式出现的角色：

- 抽象享元角色（Flyweight）：通常是一个接口或抽象类，在抽象享元类中声明了具体享元类公共的方法，这些方法可以向外界提供享元对象的内部数据（内部状态），同时也可以通过这些方法来设置外部数据（外部状态）。（AbstractBox）

- 具体享元（Concrete Flyweight）角色 ：它实现了抽象享元类，称为享元对象；在具体享元类中为内部状态提供了存储空间。通常我们可以结合单例模式来设计具体享元类，为每一个具体享元类提供唯一的享元对象。（IBox，LBox，OBox）

- 非享元（Unsharable Flyweight)角色 ：并不是所有的抽象享元类的子类都需要被共享，不能被共享的子类可设计为非共享具体享元类；当需要一个非共享具体享元类的对象时可以直接通过实例化创建。（本例中未出现）

- 享元工厂（Flyweight Factory）角色 ：负责创建和管理享元角色。当客户对象请求一个享元对象时，享元工厂检査系统中是否存在符合要求的享元对象，如果存在则提供给客户；如果不存在的话，则创建一个新的享元对象。（BoxFactory）





## 行为型模式

> 行为型模式用于描述程序在运行时复杂的流程控制，即描述多个类或对象之间怎样相互协作共同完成单个对象都无法单独完成的任务，它涉及算法与对象间职责的分配。

**说人话**其实就是***类与类互帮互助，做大做强的***



### 策略模式

> 定义了一系列算法，并将每个算法封装起来，使它们可以相互替换，且算法的变化不会影响使用算法的客户。策略模式属于对象行为模式，它通过对算法进行封装，把使用算法的责任和算法的实现分割开来，并委派给不同的对象对这些算法进行管理。

商场时不时就有促销活动，有中秋促销，国庆促销，春节促销，平时也动不动整个促销，每个促销活动的优惠都不太一样，中秋是买一送一，国庆是满200减50......正常来说，每种促销方案都是一个方法，然后用switch判断进行选择，但这样的代码明显是不符合开闭原则的。

如果将每个方案都用类表示，再来个上下文环境替我们管理，就可以做到灵活切换了

策略类接口：

```Java
interface Strategy {
    void show();
}
```

不同策略：

```Java
class StrategyA implements Strategy {
    public void show() {
        System.out.println("买一送一");
    }
}

class StrategyB implements Strategy {
    public void show() {
        System.out.println("满200元减50元");
    }
}

class StrategyC implements Strategy {
    public void show() {
        System.out.println("满1000元加一元换购任意200元以下商品");
    }
}
```

上下文环境：

```Java
class Mall {
    private Strategy strategy;

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public void mallShow() {
        strategy.show();
    }
}
```

上下文环境中传入策略，然后调用方法就可以执行策略，根据传入的策略不同，可以有不同促销方案，客户端代码：

```Java
class Main {
    public static void main(String[] args) {
        Mall mall = new Mall();
        
        mall.setStrategy(new StrategyA());
        mall.mallShow();
        mall.setStrategy(new StrategyB());
        mall.mallShow();
        mall.setStrategy(new StrategyC());
        mall.mallShow();
    }
}
```

如果要新增方案，直接新增一个策略类即可，然后往上下文环境Mall中传入即可。

我们再回头看策略模式的定义：

> 定义了一系列算法，并将每个算法封装起来，使它们可以相互替换，且算法的变化不会影响使用算法的客户。策略模式属于对象行为模式，它通过对算法进行封装，把使用算法的责任和算法的实现分割开来，并委派给不同的对象对这些算法进行管理。

**说人话**其实就是***每种策略都是独立的，根据情况可以选择不同策略，灵活替换***

我们再看策略模式中出现的角色：

- 抽象策略类：这是一个抽象角色，通常由一个接口或抽象类实现。此角色给出所有的具体策略类所需的接口。（Strategy）

- 具体策略类：实现了抽象策略定义的接口，提供具体的算法实现或行为。（StrategyA、StrategyB 和 StrategyC）

- 环境类：持有一个策略类的引用，最终给客户端调用。（Mall）

  

### 模板方法模式

> 定义一个操作中的算法骨架，而将算法的一些步骤延迟到子类中，使得子类可以不改变该算法结构的情况下重定义该算法的某些特定步骤。

炒菜的过程其实是有规律的，不管你是辣椒炒肉，手撕包菜，肉沫茄子.....都是倒油，热油，下菜，下调料，翻炒。其中倒油，热油，翻炒这几步是固定的，做不同菜的关键在于你下的菜和下的调料的不同，那我们其实就可以抽取出一个模板来简化做菜过程，做菜模板：

```Java
abstract class CookTemplate {
    public final void cookProcess() {
        //第一步：倒油
        this.pourOil();
        //第二步：热油
        this.heatOil();
        //第三步：倒蔬菜
        this.pourVegetable();
        //第四步：倒调味料
        this.pourSauce();
        //第五步：翻炒
        this.fry();
    }

    public void pourOil() {
        System.out.println("倒油");
    }

    public void heatOil() {
        System.out.println("热油");
    }

    //第三步：倒的菜是不一样的
    public abstract void pourVegetable();

    //第四步：倒调味料是不一样
    public abstract void pourSauce();

    public void fry(){
        System.out.println("炒啊炒啊炒到熟啊");
    }
}
```

这是个抽象类，里面有两个抽象方法，倒菜和倒调料需要交给不同的子类实现，其余方法都是固定的，而且整个炒菜的过程也给出了，同时用final修饰，防止子类篡改过程。

炒茄子：

```Java
class CookEggplant extends CookTemplate{
    @Override
    public void pourVegetable() {
        System.out.println("下锅的蔬菜是茄子");
    }

    @Override
    public void pourSauce() {
        System.out.println("下锅的酱料是蒜蓉");
    }
}
```

炒包菜：

```Java
class CookCabbage extends CookTemplate{
    @Override
    public void pourVegetable() {
        System.out.println("下锅的蔬菜是包菜");
    }

    @Override
    public void pourSauce() {
        System.out.println("下锅的酱料是辣椒");
    }
}
```

客户端：

```Java
class Main {
    public static void main(String[] args) {
        CookCabbage cookCabbage = new CookCabbage();
        cookCabbage.cookProcess();
        CookEggplant cookEggplant = new CookEggplant();
        cookEggplant.cookProcess();
    }
}
```

不同子类就可以做出不同的菜啦

我们再回头看模板方法模式的定义：

> 定义一个操作中的算法骨架，而将算法的一些步骤延迟到子类中，使得子类可以不改变该算法结构的情况下重定义该算法的某些特定步骤。

**说人话**其实就是***给你个模板，你把里面不太确定的地方按照你自己的想法实现下，就有一个约定好的完整算法了***

我们再看模板方法模式中出现的角色：

- 抽象类：负责给出一个算法的轮廓和骨架。它由一个模板方法和若干个基本方法构成。（CookTemplate）
  - 模板方法：定义了算法的骨架，按某种顺序调用其包含的基本方法。（cookProcess()）
  - 基本方法：是实现算法各个步骤的方法，是模板方法的组成部分。基本方法又可以分为三种：
    - 抽象方法：一个抽象方法由抽象类声明、由其具体子类实现。(pourVegetable() 和 pourSauce())
    - 具体方法 ：一个具体方法由一个抽象类或具体类声明并实现，其子类可以进行覆盖也可以直接继承。(pourOil()、heatOil() 和 fry())
    - 钩子方法：在抽象类中已经实现，包括用于判断的逻辑方法和需要子类重写的空方法两种。一般钩子方法是用于判断的逻辑方法，这类方法名一般为isXxx，返回值类型为boolean类型。（本例中未出现）

* 具体子类：实现抽象类中所定义的抽象方法和钩子方法，它们是一个顶级逻辑的组成步骤。（CookEggplant 和 CookCabbage）

  

### 观察者模式

> 又被称为发布-订阅（Publish/Subscribe）模式，它定义了一种一对多的依赖关系，让多个观察者对象同时监听某一个主题对象。这个主题对象在状态变化时，会通知所有的观察者对象，使他们能够自动更新自己。

你有没有想过，当你关注的公众号更新时，为什么你能收到更新提醒消息，你关注的up主更新时，为什么你的能收到动态......其实只要用户订阅了一个主题，这个主题更新时，由主题遍历每一位订阅者，通知他们有消息更新，就实现啦，是不是很简单，直接上代码，主题接口：

```Java
interface Subject {
    //增加订阅者
    void attach(Observer observer);

    //删除订阅者
    void detach(Observer observer);

    //通知订阅者更新消息
    void notify(String message);
}
```

订阅者接口：

```Java
interface Observer {
    void update(String message);
}
```

不同的订阅者：

```Java
class QQUser implements Observer {
    private String name;
    
    public QQUser(String name) {
        this.name = name;
    }
    
    @Override
    public void update(String message) {
        System.out.println("QQ用户" + name + "收到一条信息：" + message);
    }
}

class WechatUser implements Observer {
    private String name;
    
    public WechatUser(String name) {
        this.name = name;
    }
    
    @Override
    public void update(String message) {
        System.out.println("微信用户" + name + "收到一条信息：" + message);
    }
}
```

具体主题类：

```Java
class SubscriptionSubject implements Subject {
    private List<Observer> observers = new ArrayList<>();

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notify(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}
```

notify是关键代码，是遍历所有订阅者并给他们发消息，来看客户端：

```Java
class Main {
    public static void main(String[] args) {
        SubscriptionSubject subscriptionSubject = new SubscriptionSubject();
        
        WechatUser wechatUser = new WechatUser("路人甲");
        QQUser qqUser = new QQUser("路人乙");
        
        subscriptionSubject.attach(wechatUser);
        subscriptionSubject.attach(qqUser);
        
        subscriptionSubject.notify("鸽鸽出新歌了");
    }
}
```

当主题更新时，调用notify方法，就能将消息传给每一位订阅者了。

再回头看观察者模式的定义：

> 又被称为发布-订阅（Publish/Subscribe）模式，它定义了一种一对多的依赖关系，让多个观察者对象同时监听某一个主题对象。这个主题对象在状态变化时，会通知所有的观察者对象，使他们能够自动更新自己。

**说人话**其实就是***我关注你了，你有状态变化别忘记通知所有关注了你的人，这样我就能收到消息了***

我们再回头看观察者模式中出现的角色：

- 抽象主题（抽象被观察者）：抽象主题角色把所有观察者对象保存在一个集合里，每个主题都可以有任意数量的观察者，抽象主题提供一个接口，可以增加和删除观察者对象。（Subject）

- 具体主题（具体被观察者）：该角色将有关状态存入具体观察者对象，在具体主题的内部状态发生改变时，给所有注册过的观察者发送通知。（SubscriptionSubject）

- 抽象观察者：是观察者的抽象类，它定义了一个更新接口，使得在得到主题更改通知时更新自己。（Observer）

- 具体观察者：实现抽象观察者定义的更新接口，以便在得到主题更改通知时更新自身的状态。（QQUser 和 WechatUser）

  

### 迭代器模式

> 提供一个对象来顺序访问聚合对象中的一系列数据，而不暴露聚合对象的内部表示。 

其实就是遍历嘛，来看迭代器接口：

```Java
interface Iterator {
    boolean hasNext();
    Object next();
}
```

是否有下一个，已经获取下一个，两个方法，很简单，现有一个学生类：

```Java
class Student {
    private String name;
    
    public Student(String name) {
        this.name = name;
    }
}
```

为方便展示，这里不展示get和set等方法，直接看学生迭代器的实现：

```Java
class StudentIteratorImpl implements Iterator {
    private List<Student> list;
    
    private int position = 0;

    public StudentIteratorImpl(List<Student> list) {
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        return position < list.size();
    }

    @Override
    public Object next() {
        Student currentStudent = list.get(position);
        position++;
        return currentStudent;
    }
}
```

除此之外，我们应该再来个容器，里面放着学生集合，同时可以获取到学生迭代器遍历集合：

```Java
interface Aggregate {
    void add(Object obj);

    void remove(Object obj);

    Iterator getIterator();
}
```

具体实现：

```Java
class StudentAggregateImpl implements Aggregate {

    private List<Student> list = new ArrayList<>();

    @Override
    public void add(Object obj) {
        this.list.add((Student) obj);
    }

    @Override
    public void remove(Object obj) {
        this.list.remove((Student) obj);
    }

    @Override
    public Iterator getIterator() {
        return new StudentIteratorImpl(list);
    }
}
```

客户端代码：

```Java
class Main {
    public static void main(String[] args) {
        Student student1 = new Student("张三");
        Student student2 = new Student("李四");
        Student student3 = new Student("王五");
        // 获取容器
        Aggregate aggregate = new StudentAggregateImpl();
        // 容器中添加对象
        aggregate.add(student1);
        aggregate.add(student2);
        aggregate.add(student3);
        // 获取迭代器
        Iterator iterator = aggregate.getIterator();
        // 迭代器遍历
        while (iterator.hasNext()) {
            Student student = (Student) iterator.next();
            System.out.println(student.getName());
        }
    }
}
```

我们再回头看迭代器模式的定义：

> 提供一个对象来顺序访问聚合对象中的一系列数据，而不暴露聚合对象的内部表示。 

**说人话**其实就是***定义一套遍历接口***

我们再看迭代器模式中出现的角色：

* 抽象聚合：定义存储、添加、删除聚合元素以及创建迭代器对象的接口。（Aggregate）

* 具体聚合：实现抽象聚合类，返回一个具体迭代器的实例。（StudentAggregateImpl）

* 抽象迭代器：定义访问和遍历聚合元素的接口，通常包含 hasNext()、next() 等方法。（Iterator）

* 具体迭代器：实现抽象迭代器接口中所定义的方法，完成对聚合对象的遍历，记录遍历的当前位置。（StudentIteratorImpl）

  

### 责任链模式

> 又名职责链模式，为了避免请求发送者与多个请求处理者耦合在一起，将所有请求的处理者通过前一对象记住其下一个对象的引用而连成一条链；当有请求发生时，可将请求沿着这条链传递，直到有对象处理它为止。

现需要开发一个请假流程控制系统。请假一天以下的假只需要小组长同意即可；请假1天到3天的假还需要部门经理同意；请求3天到7天还需要总经理同意才行。那我们的流程应该是这样的，先由小组长看假条，小组长能批就直接给你批了，小组长没权限批，就让小组长转交给部门经理，部门经理看他有没有权限，没权限继续往上传。

假条，为方便展示，只展示部分方法：

```Java
class LeaveRequest {
    private String name;// 请假人
    private int num;// 请假天数
    private String content;// 请假内容

    public LeaveRequest(String name, int num, String content) {
        this.name = name;
        this.num = num;
        this.content = content;
    }
}
```

处理人：

```Java
abstract class Handler {
    protected final static int NUM_ONE = 1;
    protected final static int NUM_THREE = 3;
    protected final static int NUM_SEVEN = 7;
	// 能批假天数的范围
    private int numStart;
    private int numEnd;
    // 上级领导，批不了就交给上级来批
    private Handler nextHandler;

    public Handler(int numStart) {
        this.numStart = numStart;
    }

    public Handler(int numStart, int numEnd) {
        this.numStart = numStart;
        this.numEnd = numEnd;
    }

    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public final void submit(LeaveRequest leave) {
    	// 有权限就自己处理了
        if (leave.getNum() >= this.numStart && leave.getNum() <= this.numEnd || null == nextHandler) {
            handleLeave(leave);
        } else {
        // 没权限就交给上级去批
            nextHandler.submit(leave);
        }
    }
	// 每位领导批假的方式不太一样，定义为抽象，交给子类具体实现
    protected abstract void handleLeave(LeaveRequest leave);
}
```

小组组长：

```Java
class GroupLeader extends Handler {
    public GroupLeader() {
        super(Handler.NUM_ONE, Handler.NUM_THREE);
    }
    
    @Override
    protected void handleLeave(LeaveRequest leave) {
        System.out.println(leave.getName() + "请假" + leave.getNum() + "天," + leave.getContent() + "。");
        System.out.println("小组长审批：同意。");
    }
}
```

部门经理：

```Java
class Manager extends Handler {
    public Manager() {
        super(Handler.NUM_THREE, Handler.NUM_SEVEN);
    }

    @Override
    protected void handleLeave(LeaveRequest leave) {
        System.out.println(leave.getName() + "请假" + leave.getNum() + "天," + leave.getContent() + "。");
        System.out.println("部门经理审批：同意。");
    }
}
```

总经理：

```Java
class GeneralManager extends Handler {
    public GeneralManager() {
        super(Handler.NUM_SEVEN);
    }

    @Override
    protected void handleLeave(LeaveRequest leave) {
        System.out.println(leave.getName() + "请假" + leave.getNum() + "天," + leave.getContent() + "。");
        System.out.println("总经理审批：同意。");
    }
}
```

客户端：

```Java
class Main {
    public static void main(String[] args) {
        LeaveRequest leave1 = new LeaveRequest("张三",1,"身体不适");
        LeaveRequest leave2 = new LeaveRequest("李四",5,"回家种地");
        LeaveRequest leave3 = new LeaveRequest("王五",9,"家里老母猪生了");

        GroupLeader groupLeader = new GroupLeader();
        Manager manager = new Manager();
        GeneralManager generalManager = new GeneralManager();
        // 设置传递链，小组组长的上级是部门经理，部门经理的上级是总经理
        groupLeader.setNextHandler(manager);
        manager.setNextHandler(generalManager);
        // 不管是什么假条，都先交给小组组长批，小组组长能批就别麻烦上级领导了，领导很忙~
        groupLeader.submit(leave1);
        groupLeader.submit(leave2);
        groupLeader.submit(leave3);
    }
}
```

一般责任链要有个兜底的人，不管什么请求他都能处理，但要放到压轴，小弟能处理就交给小弟处理吧~

我们再回头看责任链模式的定义：

> 又名职责链模式，为了避免请求发送者与多个请求处理者耦合在一起，将所有请求的处理者通过前一对象记住其下一个对象的引用而连成一条链；当有请求发生时，可将请求沿着这条链传递，直到有对象处理它为止。

**说人话**其实就是***你没权限？那你给我转交给有权限的人来处理，直到有人给我处理！***

我们再看责任链模式中出现的角色：

- 抽象处理者：定义一个处理请求的接口，包含抽象处理方法和一个后继连接。（Handler）

- 具体处理者：实现抽象处理者的处理方法，判断能否处理本次请求，如果可以处理请求则处理，否则将该请求转给它的后继者。（GroupLeader、Manager 和 GeneralManager）

- 客户类：创建处理链，并向链头的具体处理者对象提交请求，它不关心处理细节和请求的传递过程。（Main）

  

### 命令模式

> 将一个请求封装为一个对象，使发出请求的责任和执行请求的责任分割开。这样两者之间通过命令对象进行沟通，这样方便将命令对象进行存储、传递、调用、增加与管理。

我们去路边吃烧烤，是直接向师傅说，给我来串烤羊肉，三串淀粉肠，师傅需要边烤边记住每个人需要什么，人一旦多起来，师傅可能会遗漏某一单，或者记错某一单；我们去饭店，一般是找服务员点餐，服务员再通知后台厨师，这样，服务员专心负责传达命令，厨师专心负责做菜，避免了客户与厨师之间的耦合，整个过程井然有序。

厨师既能炒蔬菜，又能炒肉：

```Java
class Cook {
    public void cookVegetables(String vegetable) {
        System.out.println("炒蔬菜：" + vegetable);
    }

    public void cookMeat(String meat) {
        System.out.println("炒肉：" + meat);
    }
}
```

抽象命令类：

```Java
abstract class Command {
    // 厨子，也就是命令的接收者
    protected Cook receiver;
    
    protected String information;
    
    public Command(Cook receiver, String information) {
        this.receiver = receiver;
        this.information = information;
    }
    // 不同命令的执行是不同的，蔬菜命令需要厨子执行炒蔬菜的方法，肉命令需要厨子执行炒肉的方法
    abstract public void executeCommand();
}
```

具体命令：

```Java
class VegetablesCommand extends Command{
    public VegetablesCommand(Cook receiver, String information) {
        super(receiver, information);
    }

    @Override
    public void executeCommand() {
        // 让命令接收者执行特点的方法
        receiver.cookVegetables(information);
    }
}

class MeatCommand extends Command{
    public MeatCommand(Cook receiver, String information) {
        super(receiver, information);
    }

    @Override
    public void executeCommand() {
        // 让命令接收者执行特点的方法
        receiver.cookMeat(information);
    }
}
```

服务员类：

```Java
class Waiter {
    private List<Command> orders = new ArrayList<>();
    // 新增命令
    public void addOrder(Command command) {
        orders.add(command);
        System.out.println("新增订单：" + command.information);
    }
    // 移除命令
    public void cancelOrder(Command command) {
        orders.remove(command);
        System.out.println("取消订单：" + command.information);
    }
    // 遍历每个命令，让命令执行
    public void executeOrders() {
        for (Command command : orders) {
            command.executeCommand();
        }
    }
}
```

客户端代码：

```Java
class Main {
    public static void main(String[] args) {
        Cook cook = new Cook();
        Command command1 = new MeatCommand(cook, "辣椒炒肉");
        Command command2 = new MeatCommand(cook, "肉沫茄子");
        Command command3 = new VegetablesCommand(cook, "耗油花菜");
        Command command4 = new VegetablesCommand(cook, "农家青菜");
        Waiter waiter = new Waiter();
        // 新增命令
        waiter.addOrder(command1);
        waiter.addOrder(command2);
        waiter.addOrder(command3);
        waiter.addOrder(command4);
        // 取消某个命令
        waiter.cancelOrder(command2);
        // 执行所有存在的命令
        waiter.executeOrders();
    }
}
```

我们再回头看命令模式的定义：

> 将一个请求封装为一个对象，使发出请求的责任和执行请求的责任分割开。这样两者之间通过命令对象进行沟通，这样方便将命令对象进行存储、传递、调用、增加与管理。

**说人话**其实就是***不要直接向命令执行者（厨师）下达命令，引入一个中间人（服务员）传达命令***

我们再看命令模式中出现的角色：

- 抽象命令类： 定义命令的接口，声明执行的方法。（Command）

- 具体命令：具体的命令，实现命令接口；通常会持有接收者，并调用接收者的功能来完成命令要执行的操作。（MeatCommand 和 VegetablesCommand）

- 实现者/接收者： 接收者，真正执行命令的对象。任何类都可能成为一个接收者，只要它能够实现命令要求实现的相应功能。（Cook）

- 调用者/请求者（Invoker）角色： 要求命令对象执行请求，通常会持有命令对象，可以持有很多的命令对象。这个是客户端真正触发命令并要求命令执行相应操作的地方，也就是说相当于使用命令对象的入口。（Waiter）

  

### 备忘录模式

> 又叫快照模式，在不破坏封装性的前提下，捕获一个对象的内部状态，并在该对象之外保存这个状态，以便以后当需要时能将该对象恢复到原先保存的状态。
>

其实就是**存档**，就是保存一个对象的当前状态，随时可以恢复，windows里的ctrl+z就是这个

先来备忘录接口，接口内方法为空，只起到声明的作用，详情我们稍后在讲：

```Java
interface IMemento {
}
```

备忘录发起者，也就是需要存档的对象，省略了get和set方法：

```Java
class Originator {
    private String state;

    public Memento saveToMemento() {
        return new Memento(state);
    }

    public void getStateFromMemento(IMemento memento) {
        state = ((Memento) memento).getState();
    }

}
```

里面有两个方法，存档和读档，是从备忘录类读取的，来看备忘录类：

```Java
class Memento implements IMemento {
    private String state;

    public Memento(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
```

实现备忘录接口IMemento，其实就相当于被声明为IMemento的子类，然后有需要被存档的成员变量以及获取方法，这里不应该提供set方法，因为一个存档被创建后就不应该被修改，应该是只读的。

除此之外，我们应该还有个管理者帮助我们进行存档和读档操作：

```Java
/**
 * 管理者只能看到窄接口，不允许修改备忘录对象
 */
class Caretaker {

    private List<IMemento> mementoList = new ArrayList<>();

    public void add(IMemento memento) {
        mementoList.add(memento);
    }

    public IMemento get(int index) {
        if (index >= mementoList.size()) return null;
        return mementoList.get(index);
    }

}
```

管理者有个mementoList，也就是所有存档集合，然后有添加存档和获取存档的方法，但添加和获取存档的对象都是IMemento接口对象，而这是个空接口，对于管理者来说，这就是个**窄接口**，只能传递，不能访问，也就是说**管理者只能帮助管理，不能访问存档**，仔细体悟这里多态的精髓吧~

再回头看备忘录发起者中读档的方法：

```Java
public void getStateFromMemento(IMemento memento) {
	state = ((Memento) memento).getState();
}
```

同样是接收IMemento接口，但他可以将这个接口**向下转型**成Memento对象，然后获取里面的数据，所以这个接口对于备忘录发起者来说是**宽接口**

客户端代码：

```Java
class Main {
    public static void main(String[] args) {
        // 获取存档管理者
        Caretaker caretaker = new Caretaker();
        // 获取备忘录发起者
        Originator originator = new Originator();
        // 设置状态
        originator.setState("State 1");
        // 存档
        caretaker.add(originator.saveToMemento());
        // 设置状态
        originator.setState("State 2");
        // 存档
        caretaker.add(originator.saveToMemento());
        // 设置状态
        originator.setState("State 3");
        // 读档
        originator.getStateFromMemento(caretaker.get(0));
        // 查看当前状态
        System.out.println(originator.getState());
        // 再读档
        originator.getStateFromMemento(caretaker.get(1));
        // 查看当前状态
        System.out.println(originator.getState());
    }
}
```

我们再回头看备忘录模式的定义：

> 又叫快照模式，在不破坏封装性的前提下，捕获一个对象的内部状态，并在该对象之外保存这个状态，以便以后当需要时能将该对象恢复到原先保存的状态。

**说人话**其实就是***存档！***

我们再看备忘录模式中出现的角色：

- 发起人：记录当前时刻的内部状态信息，提供创建备忘录和恢复备忘录数据的功能，实现其他业务功能，它可以访问备忘录里的所有信息。（Originator）

- 备忘录：负责存储发起人的内部状态，在需要的时候提供这些内部状态给发起人。（Memento）

- 管理者：对备忘录进行管理，提供保存与获取备忘录的功能，但其不能对备忘录的内容进行访问与修改。（Caretaker）

  

### 状态模式

> 对有状态的对象，把复杂的“判断逻辑”提取到不同的状态对象中，允许状态对象在其内部状态发生改变时改变其行为。

一个电梯有开门状态，关门状态，停止状态，运行状态（虽然运行和关门状态实际上是同时存在的，但我们这里假设两者只能同时存在其中之一）。每一种状态改变，都有可能要根据其他状态来更新处理。例如，如果电梯门现在处于运行时状态，就不能进行开门操作，而如果电梯门是停止状态，就可以执行开门操作。

如果按照平时的写法， 难免有大量的switch判断。根据当前状态，转移到其余状态的转移方程有4 * 4 = 16种（自己转移到自己也需要进行判断）。如果这些东西都写到一个类中未免显得过于臃肿，而且扩展性较低。那不妨将状态抽象出来，每个状态都是一个状态类，然后由一个电梯（Context）进行状态切换操作，但具体的切换逻辑还是由每个状态自己负责，先看抽象出来的状态类：

```Java
abstract class LiftState {
    protected Context context;
    
    public void setContext(Context context) {
        this.context = context;
    }
    
    public abstract void open();

    public abstract void close();

    public abstract void run();

    public abstract void stop();
}
```

这个类表示，我这个状态属于一个具体的电梯（Context）（废话，脱离了电梯还扯什么电梯状态），然后在当前状态下，可以进行开，关，运行，停止这四种状态的切换。先看开启状态：

```Java
class OpeningState extends LiftState {
    @Override
    public void open() {
        System.out.println("电梯已处于开启状态...");
    }

    @Override
    public void close() {
        System.out.println("电梯关闭...");
        context.setLiftState(Context.closingState);
    }

    @Override
    public void run() {
        System.out.println("电梯处于开启状态，无法运行");
    }

    @Override
    public void stop() {
        System.out.println("电梯处于开启状态，不需要停止");
    }
}
```

开启状态下切换成开启状态，这不是脱裤子放屁吗；开启状态下切换成关闭状态，这自然是允许的，执行对应的逻辑后（这里是控制台打印）将电梯（context）的状态设置为关闭状态；开启状态下切换成允许状态，除非你不要命啦，不予切换；开启状态下切换成停止状态，按现实来说开启状态下已经是停止状态了，但这里因为场景需要，我们假设不能直接切换。

同理分析其余三种状态：

```Java
// 关闭状态
class ClosingState extends LiftState {
    @Override
    public void close() {
        System.out.println("电梯已处于关闭状态...");
    }

    @Override
    public void open() {
        System.out.println("电梯开启...");
        super.context.setLiftState(Context.openingState);
    }

    @Override
    public void run() {
        System.out.println("电梯运行...");
        super.context.setLiftState(Context.runningState);
    }

    @Override
    public void stop() {
        System.out.println("电梯停止...");
        super.context.setLiftState(Context.stoppingState);
    }
}
// 运行状态
class RunningState extends LiftState {

    @Override
    public void open() {
        System.out.println("电梯处于运行状态，无法开启");
    }

    @Override
    public void close() {
        System.out.println("电梯处于运行状态，不需要关闭");
    }

    @Override
    public void run() {
        System.out.println("电梯已处于运行状态...");
    }

    @Override
    public void stop() {
        System.out.println("电梯停止...");
        super.context.setLiftState(Context.stoppingState);
    }
}
// 停止状态
class StoppingState extends LiftState {
    @Override
    public void open() {
        System.out.println("电梯开启...");
        super.context.setLiftState(Context.openingState);
    }

    @Override
    public void close() {
        System.out.println("电梯关闭...");
        super.context.setLiftState(Context.closingState);
    }

    @Override
    public void run() {
        System.out.println("电梯运行...");
        super.context.setLiftState(Context.runningState);
    }

    @Override
    public void stop() {
        System.out.println("电梯已处于停止状态...");
    }
}
```

再来看看我们的电梯，也就是Context：

```Java
class Context {
    // 将状态设置为静态常量，方便切换
    public final static OpeningState openingState = new OpeningState();
    public final static ClosingState closingState = new ClosingState();
    public final static RunningState runningState = new RunningState();
    public final static StoppingState stoppingState = new StoppingState();
    
    // 当前电梯状态
    private LiftState liftState;

    // 设置电梯状态，切换电梯状态的同时让电梯状态知道它是属于哪个电梯的，电梯和电梯状态是相互聚合，你中有我，我中有你的
    public void setLiftState(LiftState liftState) {
        this.liftState = liftState;
        this.liftState.setContext(this);
    }
    // 当前电梯需要开启
    public void open() {
        liftState.open();
    }
    // 当前电梯需要关闭
    public void close() {
        liftState.close();
    }
    // 当前电梯需要运行
    public void run() {
        liftState.run();
    }
    // 当前电梯需要停止
    public void stop() {
        liftState.stop();
    }
}
```

这样，电梯状态的切换具体判断逻辑就由每个状态自己负责了，电梯（Context）的职责就显得十分清爽。

客户端代码：

```Java
class Main {
    public static void main(String[] args) {
        Context context = new Context();
        // 默认初始停止状态
        context.setLiftState(new ClosingState());
        context.stop();
        context.open();
        context.run();
        context.close();
        context.run();
        context.open();
        context.close();
        context.run();
        context.stop();
    }
}
```

电梯只管切换，整个代码中没有出现switch语句。

我们再回头看状态模式的定义：

> 对有状态的对象，把复杂的“判断逻辑”提取到不同的状态对象中，允许状态对象在其内部状态发生改变时改变其行为。

**说人话**其实就是***每个状态都是独立的，并且每个状态自己负责转移到其它状态的逻辑***

我们再看状态模式中出现的角色：

- 环境：也称为上下文，它定义了客户程序需要的接口，维护一个当前状态，并将与状态相关的操作委托给当前状态对象来处理。（Context）

- 抽象状态：定义一个接口，用以封装环境对象中的特定状态所对应的行为。（LiftState）

- 具体状态：实现抽象状态所对应的行为。（OpeningState、ClosingState、RunningState 和 StoppingState）

  

### 访问者模式

> 封装一些作用于某种数据结构中的各元素的操作，它可以在不改变这个数据结构的前提下定义作用于这些元素的新的操作。

在主人的家中，有猫有狗，人可以给这些宠物喂食，人又分为主人和其他人，也就是不同访问者，不同访问者喂同样的猫猫狗狗得到的反应是不一样的。

先来看人接口：

```Java
interface Person {
    void feedCat(Cat cat);

    void feedDog(Dog dog);
}
```

可以喂猫喂狗，没什么毛病，再来看动物类接口：

```Java
interface Animal {
    void accept(Person person);
}
```

可以接收人的投喂，没什么毛病，再来看人的具体实现：

```java
class Owner implements Person {
    @Override
    public void feedCat(Cat cat) {
        System.out.println("主人喂猫，并说了声，我家猫可爱吧");
    }

    @Override
    public void feedDog(Dog dog) {
        System.out.println("主人喂狗，并说了声，我家狗可爱吧");
    }
}

class Someone implements Person {
    @Override
    public void feedCat(Cat cat) {
        System.out.println("其他人喂猫，夸了句，你家猫真听话");
    }

    @Override
    public void feedDog(Dog dog) {
        System.out.println("其他人喂狗，夸了句，你家狗好活泼");
    }
}
```

人分为主人和其他人，两者方法的执行逻辑是不一样的，来看动物的具体实现：

```Java
class Cat implements Animal {
    @Override
    public void accept(Person person) {
        person.feedCat(this);
        System.out.println("好好吃，喵喵喵！！！");
    }
}

class Dog implements Animal {
    @Override
    public void accept(Person person) {
        person.feedDog(this);
        System.out.println("好好吃，汪汪汪！！！");
    }
}
```

接收人的投喂，并调用人的投喂方法，把自己传递进去（你拿个猫条在猫面前，猫就向你**主动**求食，求投喂了），再来看主人的家吧：

```Java
class Home {
    // 家里有许多动物，虽然是不同的，但都有个共同接口，照收不误
    private List<Animal> animals = new ArrayList<>();

    // 当前访问者对每个节点进行访问，也就是把每个动物投喂一遍
    public void action(Person person) {
        for (Animal animal : animals) {
            animal.accept(person);
        }
    }

    public void add(Animal animal) {
        animals.add(animal);
    }
}
```

来看客户端代码：

```Java
class Main {
    public static void main(String[] args) {
        // 准备数据
        Home home = new Home();
        home.add(new Dog());
        home.add(new Cat());
        // 主人投喂
        Owner owner = new Owner();
        home.action(owner);
        // 其他人投喂
        Someone someone = new Someone();
        home.action(someone);
    }
}
```

不同人投喂（不同访问者访问）同一个屋子里的猫和狗（同样的被访问者），得到的反应是不一样的，具体的不一样逻辑是在人的投喂方法（访问者的方法实现）中。

我们再回头看访问者模式的定义：

>封装一些作用于某种数据结构中的各元素的操作，它可以在不改变这个数据结构的前提下定义作用于这些元素的新的操作。

**说人话**其实就是***不同访问者访问相同对象，得到的反应是不一样的***

我们再看访问者模式中出现的角色：

- 抽象访问者：定义了对每一个元素访问的行为，它的参数就是可以访问的元素，它的方法个数理论上来讲与元素类个数是一样的（有几种动物就有几种投喂方式），从这点不难看出，访问者模式要求元素类的个数不能改变。（Person）

- 具体访问者：给出对每一个元素类访问时所产生的具体行为。（Owner和Someone）

- 抽象元素：定义了一个接受访问者的方法，其意义是指，每一个元素都要可以被访问者访问。（Animal）

- 具体元素： 提供接受访问方法的具体实现，而这个具体的实现，通常情况下是使用访问者提供的访问该元素类的方法。（Dog和Cat）

- 对象结构：定义当中所提到的对象结构，对象结构是一个抽象表述，具体点可以理解为一个具有容器性质或者复合对象特性的类，它会含有一组元素，并且可以迭代这些元素，供访问者访问。（Home）

  

### 中介者模式

> 又叫调停模式，定义一个中介角色来封装一系列对象之间的交互，使原有对象之间的耦合松散，且可以独立地改变它们之间的交互。

以前打电话的话是需要先打给邮电局的总机的，然后”诶，你好，帮我转接一下张三“。这里的接线员就是起到了中介的作用，所有人只需要知道中介的联系方式，然后要和谁联系让中介帮忙联系就行；但中介却需要认识所有人，以便转发消息。

来看中介者接口：

```Java
interface Mediator {
    void register(Colleague colleague); // 客户注册

    void relay(String from, String to,String ad); // 转发
}
```

抽象同事类（用户）：

```Java
abstract class Colleague {
    protected Mediator mediator;
    
    protected String name;
    
    public Colleague(String name) {
        this.name = name;
    }
    
    public void setMedium(Mediator mediator) {
        this.mediator = mediator;
    }
    
    public abstract void send(String to, String ad);
    
    public abstract void receive(String from, String ad);
}
```

同事类需要认识中介，故内聚一个中介者对象，同时要有发消息和接收消息的方法，来看具体实现：

```Java
class ConcreteColleague extends Colleague{
    public ConcreteColleague(String name) {
        super(name);
    }

    @Override
    public void send(String to, String ad) {
        mediator.relay(name, to, ad);
    }

    @Override
    public void receive(String from, String ad) {
        System.out.println(name + "接收到来自" + from + "的消息:" + ad);
    }
}
```

发消息直接调用中介者的方法转发消息即可，接收消息直接接收即可（接收消息方法一般由中介者调用，中介来联系通知所有人），来看中介者的实现：

```Java
class ConcreteMediator implements Mediator {
    private List<Colleague> colleagues = new ArrayList<>();
    
    @Override
    public void register(Colleague colleague) {
        if (!colleagues.contains(colleague)) {
            colleagues.add(colleague);
            colleague.setMedium(this);
        }
    }
    
    @Override
    public void relay(String from, String to, String ad) {
        for (Colleague cl : colleagues) {
            String name = cl.getName();
            if (name.equals(to)) {
                cl.receive(from, ad);
            }
        }
    }
}
```

中介需要认识所有用户，用集合存储，新增用户直接往集合中添加用户，同时告诉用户，我是你的中介，以后你要给谁发消息找我就行；转发消息则遍历所有用户，找到目标用户，给他发消息，并告诉他是谁给他发的消息。根据实际场景，可以将集合换成Map或其它集合。

客户端代码：

```Java
class Main {
    public static void main(String[] args) {
        Mediator mediator = new ConcreteMediator();
        Colleague colleague1 = new ConcreteColleague("张三");
        Colleague colleague2 = new ConcreteColleague("李四");
        Colleague colleague3 = new ConcreteColleague("王五");
        mediator.register(colleague1);
        mediator.register(colleague2);
        mediator.register(colleague3);

        colleague1.send("李四", "晚上出来吃饭，叫上王五");
        colleague2.send("王五", "张三叫咱一起吃饭");
        colleague3.send("张三", "李四和我说了，老地方见");
    }
}
```

我们再回头看中介者模式的定义：

> 又叫调停模式，定义一个中介角色来封装一系列对象之间的交互，使原有对象之间的耦合松散，且可以独立地改变它们之间的交互。

**说人话**其实就是***找个中介解除同事之间的耦合***

我们再看中介者模式中出现的角色：

- 抽象中介者：它是中介者的接口，提供了同事对象注册与转发同事对象信息的抽象方法。（Mediator）

- 具体中介者：实现中介者接口，定义一个 List 来管理同事对象，协调各个同事角色之间的交互关系，因此它依赖于同事角色。（ConcreteMediator）

- 抽象同事类：定义同事类的接口，保存中介者对象，提供同事对象交互的抽象方法，实现所有相互影响的同事类的公共功能。（Colleague）

- 具体同事类：是抽象同事类的实现者，当需要与其他同事对象交互时，由中介者对象负责后续的交互。（ConcreteColleague）

  

### 解释器模式

> 给定一个语言，定义它的文法表示，并定义一个解释器，这个解释器使用该标识来解释语言中的句子。

说白了，约定一门语言，然后搞个解释器把他翻译翻译，比如MySQL翻译SQL语言，我们弄个简单版的~

先把一些概念说清楚，一条SQL，"select id, name from student where id = 1"按照颗粒细分，可以拆分为"select", "id", "," "name", "from", "student", "where", "id", "=", "1"这些，其中标点符号我们是不关心的，而”id“, "name","student","1"这些其实就算是**终结符表达式**；"select","from","where"这些就算是**非终结符表达式**，我们可以看到，终结符表达式是不可拆分的最小单元，而非终结符表达式一般需要配合几个终结符表达式来进行运算，例如"select"这个非终结符表达式其实是需要跟上"id","name"这两个终结符表达式才有意义的，"from"需要"student","where"需要”id“和"1"。

那么上述SQL根据非终结符表达式的运算就可以翻译成”返回id，name列“，”从学生表中“,”查询id = 1的数据“，再把三个运算结果按照顺序组合，完成输出结果就是”从学生表中查询id = 1的数据，返回id，name列“。不管是终结符表达式还是非终结符表达式，都属于表达式，且都需要被翻译，所以有表达式接口：

```Java
interface Expression {
    String interpret(Context context);
}
```

Context其实就是一个管理上下文资源与最终结果的环境：

```Java
class Context {
    // 最终返回结果
    private StringBuilder result = new StringBuilder();
    // 上下文资源
    private HashMap<String, String> map = new HashMap<>();
    // 创建Context的时候加载资源
    public Context() {
        loadMap();
    }

    public String getValue(String key) {
        return map.get(key);
    }

    public StringBuilder getResult() {
        return result;
    }
    // 从配置文件中读取资源，放入map中
    public void loadMap() {
        Properties prop = new Properties();
        try (InputStream input = Context.class.getClassLoader().getResourceAsStream("interpreter.properties")) {
            InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8);
            prop.load(reader);
            for (String key : prop.stringPropertyNames()) {
                String value = prop.getProperty(key);
                map.put(key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

来看配置文件中都有什么：

```properties
student=学生
id=id
name=名字
```

其实就相当于字典，我们再来看字符串终结符表达式：

```Java
class StringTerminalExpression implements Expression{
    private String val;

    public StringTerminalExpression(String val) {
        this.val = val;
    }
    
    @Override
    public String interpret(Context context) {
        String interpretResult = context.getValue(val);
        return interpretResult == null ? val : interpretResult;
    }
}
```

对于终结符表达式，解释的结果其实就是先去找下有没有这个字符串对应的翻译，有的话把翻译的结果返回，否则返回本身，例如”student“的解释结果是”学生“，“1”的解释结果还是“1”

再看select这个非终结符表达式：

```Java
class SelectExpression implements Expression{
    private List<Expression> cols = new ArrayList<>();

    public void addCols(Expression col) {
        cols.add(col);
    }

    @Override
    public String interpret(Context context) {
        StringJoiner interpretResult = new StringJoiner(", ");
        for (Expression col : cols) {
            interpretResult.add(col.interpret(context));
        }
        context.getResult().append("返回").append(interpretResult).append("列");
        return interpretResult.toString();
    }
}
```

select非终结符表达式需要其它终结符表达式（查询哪几列）来进行运算（字符串拼接），解释的结果其实拼接在context里的result的，例如这里解释的结果就是**“返回id, 名字列"**

再看from这个非终结符表达式：

```Java
class FromExpression implements Expression {
    private Expression table;

    public FromExpression(Expression table) {
        this.table = table;
    }

    @Override
    public String interpret(Context context) {
        String interpretResult = "从" + table.interpret(context) + "表中查询";
        context.getResult().append(interpretResult);
        return interpretResult;
    }
}
```

from非终结符表达式需要再来一个终结符表达式（表名）来进行解释，解释的结果是**”从学生表中查询“**

再看where这个非终结符表达式：

```java
class WhereExpression implements Expression{
    private StringTerminalExpression filed;
    private StringTerminalExpression value;

    public WhereExpression(StringTerminalExpression filed, StringTerminalExpression value) {
        this.filed = filed;
        this.value = value;
    }

    @Override
    public String interpret(Context context) {
        String interpretResult = filed.interpret(context) + " = " + value.interpret(context);
        context.getResult().append(interpretResult).append("的数据，");
        return interpretResult;
    }
}
```

where同样需要两个终结符表达式来进行解释（这里没考虑多条件查询，只是简单的单条件查询），where的解释结果为**”id = 1的数据，“**

我们现在通过三个非终结符解释器，已经可以拿到了**“返回id, 名字列"，"从学生表中查询"，”id = 1的数据，“**这三个解释，我们**按照一定的顺序先后解释**，就可以拿到最终的解释结果：**”从学生表中查询id = 1的数据，返回id，名字列“**

好，那具体怎么操作，我们需要再来个Client类，帮助我们把这些解释串起来，**仔细看代码中的注释**：

```Java
class Client {
    // SQL中的单词
    private String[] SQL;
    // Context上下文环境类
    private Context context = new Context();
    // 指示现在获取到哪个单词了
    private int nextExpressionIndex = 0;
    // 把一条完整SQL进行分词，去除空格，逗号，等号，分号
    public Client(String SQL) {
        this.SQL = SQL.split("[\\s,=;]+");
    }
    // 获取解释结果
    public String getInterpretResult() {
        // 先获取到sql的第一个单词，判断是什么类型sql，我们这里只实现了查询sql
        String sqlType = nextExpression();
        switch (sqlType) {
            // 如果是查询sql，返回查询sql解析结果
            case "select" : return interpretSelectSQL();
            // 如果是更新sql，返回更新sql解析结果，
            case "update" : return "暂不支持解析update语句";
            default: return "无法解析SQL";
        }
    }
    //获取到下一个单词
    private String nextExpression() {
        return nextExpressionIndex < SQL.length ? SQL[nextExpressionIndex++] : null;
    }
    // 解析查询sql，此时已经指向了第二个单词
    private String interpretSelectSQL() {
        // 先创建select解释器，需要传入列来解释
        SelectExpression selectExpression = new SelectExpression();
        String col = nextExpression();
        // 如果到了“from”这个单词，说明列已经全部放入select解释器了，开始解释from
        while (!"from".equals(col)) {
            selectExpression.addCols(new StringTerminalExpression(col));
            col = nextExpression();
        }
        // from解释器需要传入一个表名，而from后面一个单词就是了，直接传入即可
        FromExpression fromExpression = new FromExpression(new StringTerminalExpression(nextExpression()));
        // 这里有个where单词，先让遍历跳过，然后往where解释器传入一对key-value
        nextExpression();
        WhereExpression whereExpression = new WhereExpression(new StringTerminalExpression(nextExpression()), new StringTerminalExpression(nextExpression()));

        // 解释器已经准备完毕，开始解释，注意按照一定顺序，先解释from，再解释where，再解释select
        fromExpression.interpret(context);
        whereExpression.interpret(context);;
        selectExpression.interpret(context);
        return context.getResult().toString();
    }
}
```

来看客户端代码：

```Java
class Main {
    public static void main(String[] args) {
        Client client = new Client("select id, name from student where id = 1");
        String interpretResult = client.getInterpretResult();
        System.out.println(interpretResult);
    }
}
```

打印输出结果：从学生表中查询id = 1的数据，返回id, 名字列

这样，我们就完成了一个简单的单表单条件的查询SQL解释。可以看到，解释的过程中是很看重SQL的语法的，必须是“select 列1, 列2, 列3 from 表名 where key = value”这种形式，列可以有多个，但最好在配置文件中给出翻译。

我们再回头看解释器模式的定义：

> 给定一个语言，定义它的文法表示，并定义一个解释器，这个解释器使用该标识来解释语言中的句子。

**说人话**其实就是***约定一门语言，你按照约定好的语法来写，我就能解释你是什么意思***

我们再看解释器模式中出现的角色：

- 抽象表达式：定义解释器的接口，约定解释器的解释操作，主要包含解释方法 interpret()。（Expression）

- 终结符表达式：是抽象表达式的子类，用来实现文法中与终结符相关的操作，文法中的每一个终结符都有一个具体终结表达式与之相对应。（StringTerminalExpression）

- 非终结符表达式：也是抽象表达式的子类，用来实现文法中与非终结符相关的操作，文法中的每条规则都对应于一个非终结符表达式。（SelectExpression、WhereExpression 和 FromExpression）

- 环境：通常包含各个解释器需要的数据或是公共的功能，一般用来传递被所有解释器共享的数据，后面的解释器可以从这里获取这些值。（Context）

- 客户端：主要任务是将需要分析的句子或表达式转换成使用解释器对象描述的抽象语法树，然后调用解释器的解释方法，当然也可以通过环境角色间接访问解释器的解释方法。（Client）





## 设计模式六大原则

> 这里再补充一下设计模式原则，之所以放到后面是因为我个人觉得其实学完设计模式再看设计模式的原则虽然不符合正常的学习顺序，但却能对六大原则有更深的感悟



### 总原则——开闭原则

> 一个软件实体，如类、模块和函数应该对扩展开放，对修改关闭。

在程序需要进行拓展的时候，不能去修改原有的代码，而是要扩展原有代码，实现一个热插拔的效果。所以一句话概括就是：为了使程序的扩展性好，易于维护和升级。

想要达到这样的效果，我们需要使用接口和抽象类等。



### 单一职责原则

> 一个类应该只有一个发生变化的原因。

不要存在多于一个导致类变更的原因，也就是说每个类应该实现单一的职责，否则就应该把类拆分。

### 里氏替换原则

> 所有引用基类的地方必须能透明地使用其子类的对象。

任何基类可以出现的地方，子类一定可以出现。里氏替换原则是继承复用的基石，只有当衍生类可以替换基类，软件单位的功能不受到影响时，基类才能真正被复用，而衍生类也能够在基类的基础上增加新的行为。

里氏代换原则是对“开-闭”原则的补充。实现“开闭”原则的关键步骤就是抽象化。而基类与子类的继承关系就是抽象化的具体实现，所以里氏替换原则是对实现抽象化的具体步骤的规范。里氏替换原则中，子类对父类的方法尽量不要重写和重载。因为父类代表了定义好的结构，通过这个规范的接口与外界交互，子类不应该随便破坏它。

### 依赖倒装原则

> 1、上层模块不应该依赖底层模块，它们都应该依赖于抽象。
>
> 2、抽象不应该依赖于细节，细节应该依赖于抽象。

面向接口编程，依赖于抽象而不依赖于具体。写代码时用到具体类时，不与具体类交互，而与具体类的上层接口交互。

### 接口隔离原则

> 1、客户端不应该依赖它不需要的接口。
>
> 2、类间的依赖关系应该建立在最小的接口上。

每个接口中不存在子类用不到却必须实现的方法，如果不然，就要将接口拆分。使用多个隔离的接口，比使用单个接口（多个接口方法集合到一个的接口）要好。

### 迪米特法则（最少知道原则）

> 只与你的直接朋友交谈，不跟“陌生人”说话。

一个类对自己依赖的类知道的越少越好。无论被依赖的类多么复杂，都应该将逻辑封装在方法的内部，通过public方法提供给外部。这样当被依赖的类变化时，才能最小的影响该类。

最少知道原则的另一个表达方式是：只与直接的朋友通信。类之间只要有耦合关系，就叫朋友关系。耦合分为依赖、关联、聚合、组合等。我们称出现为成员变量、方法参数、方法返回值中的类为直接朋友。局部变量、临时变量则不是直接的朋友。我们要求陌生的类不要作为局部变量出现在类中。

### 合成复用原则

> 尽量使用对象组合/聚合，而不是继承关系达到软件复用的目的。

合成或聚合可以将已有对象纳入到新对象中，使之成为新对象的一部分，因此新对象可以调用已有对象的功能。





## 写在后面

至此，23种设计模式介绍完毕。