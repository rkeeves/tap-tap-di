package com.rkeeves.taptapexample;

import com.rkeeves.taptap.Container;

public class Main {

    public static void main(String[] args) {
        System.out.println("------------------------------");
        {
            System.out.println("# MANUAL - ");
            System.out.println("# Notice that we always use new instances,");
            System.out.println("# aka multiple PersonServices & Scenes exist");
            System.out.println("# Each class has a static instance counter");
            System.out.println("# On instantiation an instance gets its id from the counter,");
            System.out.println("# then the counter gets incremented");
            Scene scene = new Scene(new PersonService());
            scene.say();
            scene = new Scene(new PersonService());
            scene.say();
        }
        System.out.println("------------------------------");
        {
            System.out.println("# DI");
            System.out.println("# Notice that only a 1 PersonService & Scene exist");
            System.out.println("# Also notice that Scene's ctor needs a PersonService instance");
            System.out.println("# We resolve this automatically in the background");
            Container cnt = new Container();
            try {
                Scene scene = cnt.get(Scene.class);
                scene.say();
                scene = cnt.get(Scene.class);
                scene.say();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("------------------------------");
        {
            System.out.println("# Problem of Global versus local product");
            System.out.println("# Notice that even though by instantiating a Scene");
            System.out.println("# we implicitly created a PersonService");
            System.out.println("# but when we explicitly call get PersonService on the container");
            System.out.println("# we get a new one instead");
            System.out.println("# Maybe this is wanted by the user, maybe not,");
            System.out.println("# but my goal was simply to highlight the fact that");
            System.out.println("# in the future this must be configurable");
            System.out.println("# aka we must provide the user more fine-grained control");
            Container cnt = new Container();
            try {
                Scene scene = cnt.get(Scene.class);
                scene.say();
                PersonService personService = cnt.get(PersonService.class);
                System.out.println(personService.getHello());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}