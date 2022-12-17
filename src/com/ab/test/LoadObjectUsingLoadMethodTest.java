package com.ab.test;

import com.ab.entity.Product;
import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class LoadObjectUsingLoadMethodTest {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();

        configuration.configure("/com/ab/cgfs/hibernate.cfg.xml");

        SessionFactory sessionFactory = configuration.buildSessionFactory();

        Session session = sessionFactory.openSession();

        try {
            // Load object:
            Product proxy = session.load(Product.class,101);
            System.out.println("Name:: " + proxy.getPname());
            System.out.println("PID:: " + proxy.getPid());
        }catch (ObjectNotFoundException oe){
            System.out.println("Record not found with id: 101");
            oe.printStackTrace(); /*this exception is thrown by porxy class getter and setter method*/
        }catch (HibernateException he){
            he.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
            sessionFactory.close();
        }

    }
}