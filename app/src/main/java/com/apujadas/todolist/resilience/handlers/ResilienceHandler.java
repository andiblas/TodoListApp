package com.apujadas.todolist.resilience.handlers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public abstract class ResilienceHandler {
    ResilienceHandler successor;

    public void setSuccessor(ResilienceHandler successor) {
        this.successor = successor;
    }

    public Object handle(Object o, Method method, Object[] args) throws Throwable {
        Annotation annotation;
        if ((annotation = method.getDeclaredAnnotation(classToHandle())) != null) {
            return handleInternal(annotation, o, method, args);
        }
        if (successor != null)
            return successor.handle(o, method, args);
        else
            return method.invoke(o, args);
    }

    public abstract Class<? extends Annotation> classToHandle();

    protected abstract Object handleInternal(Annotation annotation, Object o, Method method, Object[] args) throws Throwable;
}