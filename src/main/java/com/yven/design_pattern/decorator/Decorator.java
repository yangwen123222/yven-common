package com.yven.design_pattern.decorator;

/**
 * 装饰器基类
 *
 * @author shaolong.yang@tuya.com
 * @version 1.0
 * @date 2021/05/08
 */
public class Decorator implements Component{

    private Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    @Override
    public void sampleMethod() {
        component.sampleMethod();
    }
}
