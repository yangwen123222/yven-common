package com.yven.design_pattern.decorator;

/**
 * @author shaolong.yang@tuya.com
 * @version 1.0
 * @date 2021/05/08
 */
public class Main {

    public static void main(String[] args) {

        /**
         * 使用的时候，可以灵活的实现装饰和被装饰
         *
         */
        // 被装饰的类component
        ConcreteComponent component = new ConcreteComponent();

        // 使用装饰器A修饰component
        ConcreteDecoratorA concreteDecoratorA = new ConcreteDecoratorA(component);
        concreteDecoratorA.sampleMethod();

        System.out.println();
        // 使用装饰器B修饰装饰器A
        ConcreteDecoratorB concreteDecoratorB = new ConcreteDecoratorB(concreteDecoratorA);
        concreteDecoratorB.sampleMethod();
    }
}
