package com;

import java.lang.reflect.*;

public class Re3 {
    public static void main(String[] args) throws Exception {
        // 1. 得到 Boss 类对应的 Class 对象
        Class<?> bossCls = Class.forName("com.Boss");

        // 2. 创建对象
        Object o = bossCls.newInstance();

        // 3. 调用 public 的 hi 方法
        // Method hi = bossCls.getMethod("hi", String.class); // OK
        // 3.1 得到 hi 方法对象
        Method hi = bossCls.getDeclaredMethod("hi", String.class); // OK
        // 3.2 调用
        hi.invoke(o, "易小川~");

        // 4. 调用 private static 方法
        // 4.1 得到 say 方法对象
        Method say = bossCls.getDeclaredMethod("say", int.class, String.class, char.class);
        // 4.2 因为 say 方法是 private, 所以需要暴破，原理和前面讲的构造器和属性一样
        say.setAccessible(true);
        System.out.println(say.invoke(o, 100, "张三", '男'));
        // 4.3 因为 say 方法是 static 的，还可以这样调用 ，可以传入 null
        System.out.println(say.invoke(null, 200, "李四", '女'));

        //  5. 在反射中，如果方法有返回值，统一返回 Object , 但是他运行类型和方法定义的返回类型一致
        Object reVal = say.invoke(null, 300, "王五", '男');
        System.out.println("reVal 的运行类型=" + reVal.getClass()); // String

        // 再演示一个返回的案例
        Method m1 = bossCls.getDeclaredMethod("m1");
        Object reVal2 = m1.invoke(o);
        System.out.println("reVal2 的运行类型=" + reVal2.getClass()); // Monster
    }
}

class Monster {
}

class Boss {
    public int age;
    private static String name;

    public Boss() {
    }

    public Monster m1() {
        return new Monster();
    }

    // 静态方法
    private static String say(int n, String s, char c) {
        return n + " " + s + " " + c;
    }

    // 普通 public 方法
    public void hi(String s) {
        System.out.println("hi " + s);
    }
}