package com.fastcampus.ch3.diCopy2.diCopy1;


import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


class Car{}
class SportsCar extends Car{}
class Truck extends Car{}
class Engine{}

class AppContext{
    Map map;

    AppContext(){
        map = new HashMap();
        map.put("car", new SportsCar());
        map.put("engine",new Engine());
    }

    Object getBean(String key){
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

    static Object getObject(String key) throws Exception{
        Properties p = new Properties();
        p.load(new FileReader("config.txt"));

        Class clazz = Class.forName(p.getProperty(key));

        return clazz.newInstance();
    }
    static Car getCar() throws Exception{
        Properties p = new Properties();
        p.load(new FileReader("config.txt"));

        Class clazz = Class.forName(p.getProperty("car"));

        return (Car)clazz.newInstance();
    }
}
