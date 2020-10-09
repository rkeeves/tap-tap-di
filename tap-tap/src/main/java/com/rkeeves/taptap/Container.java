package com.rkeeves.taptap;

import java.lang.reflect.Constructor;
import java.util.*;

public class Container {
    private final Map<Class<?>, Provider<?>> providers = new HashMap<>();

    private <T> void register(Class<T> cls) throws NoCtorForInjectionFoundOnClassException, IntermediateInjectableNotConstructableException {
        if (!providers.containsKey(cls)) {
            providers.put(cls, createProviderFor(cls));
        }
    }

    private <T> Provider<T> createProviderFor(Class<T> cls) throws NoCtorForInjectionFoundOnClassException, IntermediateInjectableNotConstructableException {
        Constructor<?> ctor = Arrays
                .stream(cls.getConstructors())
                .filter(c -> c.isAnnotationPresent(CtorForInjection.class))
                .findFirst().orElseThrow(() -> new NoCtorForInjectionFoundOnClassException(cls.toString()));
        List<Provider<?>> intermediates = new ArrayList<>();
        for (Class<?> paramCls : ctor.getParameterTypes()) {
            Provider<?> provider = providers.get(cls);
            if (provider == null) {
                for (Provider<?> prov : intermediates) {
                    if (prov.getCls().equals(paramCls)) {
                        provider = prov;
                        break;
                    }
                }
                if (provider == null) {
                    try {
                        provider = createProviderFor(paramCls);
                        intermediates.add(provider);
                    } catch (NoCtorForInjectionFoundOnClassException e) {
                        throw new IntermediateInjectableNotConstructableException("Failed to create provider for intermediate " + paramCls.getName() + " during resolving final product " + cls.getName(), e);
                    }
                }
            }

        }
        return new Provider<>(cls, ctor, intermediates);
    }

    public <T> T getNoAutoRegistration(Class<T> cls) throws InjectableNotRegisteredException, CtorException {
        Provider<?> provider = providers.get(cls);
        if (provider == null) {
            throw new InjectableNotRegisteredException("Injectable " + cls.getName() + " has not been registered");
        }
        return (T) provider.get();
    }

    public <T> T get(Class<T> cls) throws CtorException, IntermediateInjectableNotConstructableException, NoCtorForInjectionFoundOnClassException {
        if (!providers.containsKey(cls)) {
            register(cls);
        }
        return (T) providers.get(cls).get();
    }
}