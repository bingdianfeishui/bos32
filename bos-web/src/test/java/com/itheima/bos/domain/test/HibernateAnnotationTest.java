package com.itheima.bos.domain.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.itheima.bos.domain.Staff;

public class HibernateAnnotationTest {

    
    @Test
    public  void test01(){
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        Session session = null;
        try {
            SessionFactory sf = (SessionFactory) context.getBean("sessionFactory");
            session = sf.openSession();
            
            Staff staff = new Staff();
            staff.setName("zs");
            session.save(staff);
            session.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            if(session!=null) session.close();
            ((AbstractApplicationContext)context).close();
        }
        
    }
}
