package com.rkeeves.taptapexample;

import com.rkeeves.taptap.CtorForInjection;

public class PersonService {

    private static int instance_counter = 0;

    private int instance_idx;

    @CtorForInjection
    public PersonService() {
        instance_idx = instance_counter++;
    }

    public String getHello() {
        return "Message by PersonService(id=" + instance_idx + ")";
    }
}
