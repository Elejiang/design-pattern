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

这太麻烦了，我们需要一个类帮我们管理创建对象，这个类就是工厂类：

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

到了这里，我们恍然大悟，简单工厂是把产品的选择放到了工厂内部，这就造成了switch的分支判断，影响了代码的可扩展性；工厂方法是把产品的选择交给了用户，让用户决定要哪个产品，这种方式和直接new对象的区别在于：用工厂封装了对象的获取过程，同时可以灵活替换工厂来替换不同产品。

我们再回头看工厂方法模式的定义：

> 定义一个用于创建对象的接口，让子类决定实例化哪个产品类对象。工厂方法使一个产品类的实例化延迟到其工厂的子类。

**说人话**其实就是***定义一个生产产品的工厂类接口，不同工厂类生产不同产品对象，产品是由工厂来new的，其中生产过程可能较为复杂，但用户不需要知道那么多，你只要知道工厂类接口，根据接口就能获取到对象了***

我们再来看看工厂方法模式中出现的角色：

- 抽象工厂：提供了创建产品的接口，调用者通过它访问具体工厂的工厂方法来创建产品。（CarFactory）

- 具体工厂：主要是实现抽象工厂中的抽象方法，完成具体产品的创建。（ACarFactory和BCarFactory）

- 抽象产品：定义了产品的规范，描述了产品的主要特性和功能。（Car）

- 具体产品：实现了抽象产品角色所定义的接口，由具体工厂来创建，它同具体工厂之间一一对应。（ACar和BCar）

