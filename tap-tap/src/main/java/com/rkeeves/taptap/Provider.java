package com.rkeeves.taptap;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class Provider<T> {

    Class<T> cls;

    Constructor<?> ctor;

    List<Provider<?>> providers;

    T instance;

    public Class<T> getCls() {
        return cls;
    }

    public Provider(Class<T> cls, Constructor<?> ctor, List<Provider<?>> providers) {
        this.cls = cls;
        this.ctor = ctor;
        this.providers = providers;
    }

    public T get() throws CtorException {
        if (instance == null) {
            instance = createInstance();
        }
        return instance;
    }

    private T createInstance() throws CtorException {
        Object args[] = new Object[providers.size()];
        for (int i = 0; i < providers.size(); ++i) {
            try {
                args[i] = providers.get(i).get();
            } catch (CtorException e) {
                throw new CtorException("Exception during calling ctor of intermediate product " + cls.getName(), e);
            }
        }
        try {
            return (T) ctor.newInstance(args);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new CtorException("Exception during calling ctor of final product " + cls.getName(), e);
        }
    }
}