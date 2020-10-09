package com.rkeeves.taptapexample;

import com.rkeeves.taptap.CtorForInjection;

public class Scene {

    private static int instance_counter = 0;

    private int instance_idx;

    private final PersonService personService;

    @CtorForInjection
    public Scene(PersonService personService) {
        this.personService = personService;
        instance_idx = instance_counter++;
    }

    public void say() {
        System.out.println(personService.getHello() + " brought to you by Scene(id=" + instance_idx + ")");
    }
}
