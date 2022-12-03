package com.ab.test;

import com.ab.entity.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class LoadObjectUsingGetMethodTest {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();

        configuration.configure("/com/ab/cgfs/hibernate.cfg.xml");

        SessionFactory sessionFactory = configuration.buildSessionFactory();

        Session session = sessionFactory.openSession();

        try {
            // Load object:
            Product product = session.get(Product.class,101);
            if(product == null)
                System.out.println("Record Not Found");
            else
                System.out.println("Record Found:: " + product);
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
