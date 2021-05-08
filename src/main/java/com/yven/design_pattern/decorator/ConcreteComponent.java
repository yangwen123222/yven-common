package com.yven.design_pattern.decorator;

/**
 * @author shaolong.yang@tuya.com
 * @version 1.0
 * @date 2021/05/08
 */
public class ConcreteComponent implements Component{
    @Override
    public void sampleMethod() {
        System.out.println("ConcreteComponent sampleMethod");
    }
}
