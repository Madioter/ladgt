/**
 * @Title: SpringTest.java
 * @Package com.madiot.test
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 * @version
 */
package com.madiot.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName: SpringTest
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/21
 */
public class SpringTest {

    public static void main(String[] args) {

        /**
         * <bean id="testData" class="com.madiot.test.TestDomain">
         <property name="data">
         <list>
         <value>java.lang.String</value>
         <value>java.lang.Integer</value>
         </list>
         </property>
         </bean>

         <bean id="testData" class="com.madiot.test.TestDomain">
         <property name="data">
         <map>
         <entry key="String" value="java.lang.String"/>
         <entry key="Integer" value="java.lang.Integer"/>
         </map>
         </property>
         </bean>
         */
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring/spring-common.xml");
        TestDomain testDomain = (TestDomain) ac.getBean("testData");
        System.out.print(testDomain.getData());

    }
}
