package metaprogramming

import org.codehaus.groovy.ast.ClassNode

/**
 * Created by sajedur on 9/9/2015.
 */
class MyMetaClass implements MetaClass {

    @Override
    Object invokeMethod(Class aClass, Object o, String s, Object[] objects, boolean b, boolean b1) {
        return null
    }

    @Override
    Object getProperty(Class aClass, Object o, String s, boolean b, boolean b1) {
        return null
    }

    @Override
    void setProperty(Class aClass, Object o, String s, Object o1, boolean b, boolean b1) {

    }

    @Override
    Object invokeMissingMethod(Object o, String s, Object[] objects) {
        return null
    }

    @Override
    Object invokeMissingProperty(Object o, String s, Object o1, boolean b) {
        return null
    }

    @Override
    Object getAttribute(Class aClass, Object o, String s, boolean b) {
        return null
    }

    @Override
    void setAttribute(Class aClass, Object o, String s, Object o1, boolean b, boolean b1) {

    }

    @Override
    void initialize() {

    }

    @Override
    List<MetaProperty> getProperties() {
        return null
    }

    @Override
    List<MetaMethod> getMethods() {
        return null
    }

    @Override
    ClassNode getClassNode() {
        return null
    }

    @Override
    List<MetaMethod> getMetaMethods() {
        return null
    }

    @Override
    int selectConstructorAndTransformArguments(int i, Object[] objects) {
        return 0
    }

    @Override
    MetaMethod pickMethod(String s, Class[] classes) {
        return null
    }

    @Override
    List<MetaMethod> respondsTo(Object o, String s, Object[] objects) {
        return null
    }

    @Override
    List<MetaMethod> respondsTo(Object o, String s) {
        return null
    }

    @Override
    MetaProperty hasProperty(Object o, String s) {
        return null
    }

    @Override
    MetaProperty getMetaProperty(String s) {
        return null
    }

    @Override
    MetaMethod getStaticMetaMethod(String s, Object[] objects) {
        return null
    }

    @Override
    MetaMethod getMetaMethod(String s, Object[] objects) {
        return null
    }

    @Override
    Class getTheClass() {
        return null
    }

    @Override
    Object invokeConstructor(Object[] objects) {
        return null
    }

    @Override
    Object invokeMethod(Object o, String s, Object[] objects) {
        return null
    }

    @Override
    Object invokeMethod(Object o, String s, Object o1) {
        return null
    }

    @Override
    Object invokeStaticMethod(Object o, String s, Object[] objects) {
        return null
    }

    @Override
    Object getProperty(Object o, String s) {
        return null
    }

    @Override
    void setProperty(Object o, String s, Object o1) {

    }

    @Override
    Object getAttribute(Object o, String s) {
        return null
    }

    @Override
    void setAttribute(Object o, String s, Object o1) {

    }
}
