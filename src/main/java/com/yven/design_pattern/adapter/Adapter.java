package com.yven.design_pattern.adapter;

import java.util.List;

/**
 * 适配器类，继承了被适配类，同时实现标准接口
 */
//class Adapter extends Adaptee implements Target{
//
//    @Override
//    public void request() {
//        super.specificRequest();
//    }
//}

public class Adapter implements Target{
    /**
     * 直接关联被适配类
     */
    private Adaptee adaptee;

    private List<Adaptee> adapteeList;

    /**
     *     可以通过构造函数传入具体需要适配的被适配类对象
      */
    public Adapter (Adaptee adaptee) {
        this.adaptee = adaptee;
    }


    @Override
    public void request() {
        // 这里是使用委托的方式完成特殊功能
        this.adaptee.specificRequest();
    }
}
