package com.challenge.w2m.superheros.aop;

import org.springframework.stereotype.Component;

@Component
public class EmptyComponentForTesting {

    @TimeTracker
    public void methodForTestingCustomAnnotation() { }

}
