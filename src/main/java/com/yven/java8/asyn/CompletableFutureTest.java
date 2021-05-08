package com.yven.java8.asyn;

import java.util.concurrent.CompletableFuture;

/**
 * @Description TODO
 * @Author Edmond
 * @Date 2021/01/25 11:01
 * @Version 1.0
 */
public class CompletableFutureTest {
    public static void main(String[] args) {
        completableFutureExample();
    }



    static void completableFutureExample(){
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message");
        System.out.println(cf.getNow(null));

    }
}


