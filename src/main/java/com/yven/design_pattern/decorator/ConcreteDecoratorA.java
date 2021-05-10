package com.yven.design_pattern.decorator;

/**
 * 装饰器A
 *
 * @author shaolong.yang@tuya.com
 * @version 1.0
 * @date 2021/05/08
 */
public class ConcreteDecoratorA extends Decorator{


    public ConcreteDecoratorA(Component component) {
        super(component);
    }

    @Override
    public void sampleMethod() {
        super.sampleMethod();
        System.out.println("ConcreteDecoratorA sampleMethod");
    }
}
