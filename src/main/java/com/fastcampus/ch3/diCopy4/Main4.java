package com.fastcampus.ch3.diCopy4;


import com.google.common.reflect.ClassPath;
import org.checkerframework.checker.signature.qual.DotSeparatedIdentifiersOrPrimitiveType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;


@Component class Car {
    Engine engine;
    Door door;

    @Override
    public String toString() {
        return "Car{" +
                "engine=" + engine +
                ", door=" + door +
                '}';
    }
}

@Component class SportsCar extends Car {}

@Component class Truck extends Car {}
@Component class Engine {}
@Component class Door {}


   class AppContext {
    Map map;

    AppContext() {
        map = new HashMap();
        doComponentScan();

    }

    private void doComponentScan() {
        // 1. 패키지내의 클래스 목록 가져오기
        // 2. 반복문으로 클래스 하나씩 읽어서 @Component 어노테이션 붙어있는지 확인
        // 3. @Component 붙어있으면 객체 생성해서 map에 저장
        try {
            ClassLoader classLoader = AppContext.class.getClassLoader();
            ClassPath classPath = ClassPath.from(classLoader);  // 클래스 목록 가져오기

            Set<ClassPath.ClassInfo> set = classPath.getTopLevelClasses("com.fastcampus.ch3.diCopy4");

            for(ClassPath.ClassInfo classInfo : set){
                Class clazz = classInfo.load();
                Component component = (Component)clazz.getAnnotation(Component.class);
                if(component != null){
                    String id = StringUtils.uncapitalize(classInfo.getSimpleName()); //첫 글자는 대문자로
                    map.put(id,clazz.newInstance());
                }
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    Object getBean(String key) {   return map.get(key);    } // 이름으로 객체 찾기

    Object getBean(Class clazz){   // 객체 타입으로 찾기
        for(Object obj : map.values()){
            if(clazz.isInstance(obj))
                return obj;
        }
        return null;
    }
}

public class Main4 {
    public static void main(String[] args) throws Exception {
        AppContext ac = new AppContext();
        Car car = (Car) ac.getBean("car");
        Engine engine = (Engine) ac.getBean("engine");
        Door door = (Door)ac.getBean(Door.class);

        car.engine = engine;
        car.door = door;

        System.out.println("car = " + car);
        System.out.println("engine = " + engine);
        System.out.println("door = " + door);
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
