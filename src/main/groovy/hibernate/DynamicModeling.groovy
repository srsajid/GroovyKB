package hibernate

import org.hibernate.Session
import org.hibernate.internal.SessionImpl

/**
 * Created by sajedur on 11/16/2015.
 */
class DynamicModeling {
    public static void main(String[] args) {
        SessionImpl session = Main.session
        HashMap entity = new HashMap()
        entity.firstName = "Mr"
        entity.lastName = "khorana"
        entity.salary = 10

        def result = session.save("hibernate.Employee", entity)
        println(result)
    }
}
