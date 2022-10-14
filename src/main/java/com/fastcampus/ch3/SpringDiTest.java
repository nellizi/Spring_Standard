//package com.fastcampus.ch3;
//
//import org.checkerframework.common.initializedfields.qual.EnsuresInitializedFields;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.GenericXmlApplicationContext;
//
//class Car{}
//class Engine{}
//class Door{}
//
//public class SpringDiTest {
//    public static void main(String[] args) {  //config.xml을 읽어다 저장소에 객체 저장. ApplicationContext 는 저장소이다.
//        ApplicationContext ac = new GenericXmlApplicationContext("config.xml");
//        Car car = (Car)ac.getBean("car");
//        // Car car = ac.getBean("car", Car.class); 로 쓸 수 있다.
//        Engine engine = (Engine) ac.getBean("engine");
//        Door door = (Door) ac.getBean("door");
//    }
//}
