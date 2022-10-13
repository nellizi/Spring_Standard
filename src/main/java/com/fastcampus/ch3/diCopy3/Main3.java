package com.fastcampus.ch3.diCopy3;


import com.google.common.reflect.ClassPath;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;


@Component class Car {}

@Component class SportsCar extends Car {}

@Component class Truck extends Car {}
@Component class Engine {}

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

            Set<ClassPath.ClassInfo> set = classPath.getTopLevelClasses("com.fastcampus.ch3.diCopy3");

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

    Object getBean(String key) {
        return map.get(key);
    }
}

public class Main3 {
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
