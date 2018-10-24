package com.adam.test;

public class Main {
    public static void main(String[] args){


        StringBuffer sb = new StringBuffer();

        for (int i = 10000;i < 100000;i++){
            sb.append(",('test"+i+"')");
        }
        System.out.println(sb.toString());
    }
}
