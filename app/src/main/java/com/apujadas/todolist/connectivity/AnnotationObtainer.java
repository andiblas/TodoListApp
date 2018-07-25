package com.apujadas.todolist.connectivity;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

class AnnotationObtainer {
    private Object target;
    private String method;

    public AnnotationObtainer(Object target, String method) {
        this.target = target;
        this.method = method;
    }


    public <T> T getAnnotation(Class<? extends Annotation> classDefinition) {
        Method m = null;
        try {
            m = target.getClass().getMethod(method);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return (T) m.getDeclaredAnnotation(classDefinition);
    }
}
