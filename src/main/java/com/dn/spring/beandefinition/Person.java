package com.dn.spring.beandefinition;

public class Person {
    private String name;
    private Integer age;

    public Person() {
        System.out.println("调用 Person()");
    }

    @MyAutowried
    public Person(String name) {
        System.out.println("调用 Person(String name)");
        this.name = name;
    }

    public Person(String name, Integer age) {
        System.out.println("调用 Person(String name, int age)");
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}