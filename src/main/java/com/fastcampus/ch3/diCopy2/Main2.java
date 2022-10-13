package com.fastcampus.ch3.diCopy2;


import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


class Car {
}

class SportsCar extends Car {
}

class Truck extends Car {
}

class Engine {
}

class AppContext {
    Map map;

    AppContext() {

        try {
            Properties p = new Properties();
            p.load(new FileReader("config.txt"));
            //properties에 저장된 내용을 map에 저장
            map = new HashMap(p);

            //keySet에 저장된 클래스 이름을 반복문으로 알아내서 객체를 생성 후 map에 저장
            for (Object key : map.keySet()) {
                Class clazz = Class.forName((String) map.get(key));
                map.put(key, clazz.newInstance());
            }

            Class clazz = Class.forName(p.getProperty("car"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Object getBean(String key) {
        return map.get(key);
    }
}

public class Main2 {
    public static void main(String[] args) throws Exception {
        AppContext ac = new AppContext();
        Car car = (Car) ac.getBean("car");
        System.out.println("car = " + car);
        Engine engine = (Engine) ac.getBean("engine");
        System.out.println("engine = " + engine);
    }

    static Object getObject(String key) throws Exception {
        Properties p = new Properties();
        p.load(new FileReader("config.txt"));

        Class clazz = Class.forName(p.getProperty(key));

        return clazz.newInstance();
    }

    static Car getCar() throws Exception {
        Properties p = new Properties();
        p.load(new FileReader("config.txt"));

        Class clazz = Class.forName(p.getProperty("car"));

        return (Car) clazz.newInstance();
    }
}
