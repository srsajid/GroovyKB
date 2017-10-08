package groovy

/**
 * Created by sajedur on 10-02-2015.
 */
class MyInterceptor implements Interceptor{
    Object beforeInvoke(Object object, String methodName, Object[] arguments){
        println "  BEFORE $object .$methodName $arguments"
        if( methodName == 'sayHello' )
            arguments[0] += ' and family'
        null //value returned here isn't actually used anywhere else
    }
    boolean doInvoke(){
        true
    } //whether or not to invoke the intercepted
    //method with beforeInvoke's copy of arguments

    Object afterInvoke(Object object, String methodName, Object[] arguments,
                       Object result){
        println "  AFTER $object .$methodName $arguments: $result"
        if( methodName == 'sayHello' ) result= (result as String) + ' and in-laws'
        //we can change the returned value
        result
    }
}