package proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by sajedur on 10/31/2016.
 */
public class Test {
    public static void main(String[] args) {
        InvocationHandler handler = new MyInvocationHandler();
        MyInterface proxy = (MyInterface) Proxy.newProxyInstance(MyInterface.class.getClassLoader(), new Class[]{MyInterface.class}, handler);
        proxy.hello();

        MyClass myClass = new MyClass();
        ProxyFactory factory = new ProxyFactory(myClass);
        factory.addAdvice(new MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                System.out.println("* * * * * ");
                return null;
            }
        });
        myClass = (MyClass) factory.getProxy();
        myClass.hello();
    }
}

interface MyInterface {
    public void hello();
}

class MyClass {

    public void hello(){
        System.out.println("Tut Tut Tut Tut");
    }
}