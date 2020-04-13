package com.dn.spring.cglib.simple;

/**
 * 1
 * 使用 cglib 动态代理时，代理类（在内存中）就是目标类的子类
 */
public class TargetClass {
    
    public void add() {
        System.out.println("add");
    }
    
    public String del() {
        System.out.println("del");
        return "del";
    }
    
    public void update() {
        System.out.println("update");
    }
}
