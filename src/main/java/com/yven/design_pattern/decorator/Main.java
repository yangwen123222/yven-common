package com.yven.design_pattern.decorator;

/**
 * @author shaolong.yang@tuya.com
 * @version 1.0
 * @date 2021/05/08
 */
public class Main {

    public static void main(String[] args) {
        ConcreteComponent component = new ConcreteComponent();

        ConcreteDecoratorA concreteDecoratorA = new ConcreteDecoratorA(component);
        concreteDecoratorA.sampleMethod();

        System.out.println();
        ConcreteDecoratorB concreteDecoratorB = new ConcreteDecoratorB(concreteDecoratorA);
        concreteDecoratorB.sampleMethod();
    }
}
