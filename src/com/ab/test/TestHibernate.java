package com.ab.test;

import com.ab.entity.Product;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

Start from Day-21

public class TestHibernate {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        /*
            This line will activate the framework
        */
        configuration.configure("/com/ab/cgfs/hibernate.cfg.xml");

        SessionFactory sessionFactory = configuration.buildSessionFactory();

        /*
            -> configuration.buildSessionFactory(); This method is design based on builder Design pattern
             which construct the complex SessionFactory object by adding multiple small objects.
             -> configuration.buildSessionFactory(); Perform the following activities:
                (1) Loads Mapping file specified in hibernate.cfg.xml file anc checks whether the are well formed or
                    valid or not, if not exception will be thrown. If they well formed and valid the in-Memory meta data
                    of mapping files will be created in the RAM memory where application is running.

                (2) Create multiple services based on the configuration done in hibernate configuration file and
                    mapping files:
                            (1) Dialect Object: To generate Sql quries
                            (2) Connection Provider: Data Source pointing to build JDBC connection pool.
                            (3) Transaction Service: To manage commit or rollback activity either using JDBC transaction service or JTA transaction Service.
                            (4) Generator Class object: To generate Id value for entity object dynamically
                            (5) etc.. and other services

                SessionFactory object means.. It is the object of underlying Hibernate Framework that
                implement org.hibernate.SessionFactory(I) directly or indirectly.
        */

        Session session = sessionFactory.openSession();

        /*
            sessionFactory.openSession(); This method is design based on factory pattern becuase it provde
            abstraction on Session object creation process.

            sessionFactory.openSession(); This method perform the following operation:
                (1) Collect JDBC connection pool through DataSource object represented by ConnectionProvider
                    service of SessionFactory.
                (2) Adds first level cache as Map collection to Session Object.
                (3) Gathers In-Memory metadata of mapping files from SessionFactory.
                (4) Gathers Dialect objects from SessionFactory.
                (5) Having all the above objects, it creates and return Session object.
            So we can say Session object is Connection++
            Session Object is immutable object.

            Session object is the object of underlying Hibernate framework supplied java class that implements
            org.hibernate.Session(I) directly or Indirectly.
        */

        // preapare Entity class object having data
        Product product = new Product();
        product.setPid(1);
        product.setPname("TV");
        product.setPrice(9000.98);
        product.setQty(10.0);
        product.setStatus("available");

        Transaction transaction = null;
        Boolean flag = false;

        try {
            transaction = session.beginTransaction();
            session.save(product);
            flag = true;
        }catch (HibernateException hbe){
            hbe.printStackTrace();;
            flag = false;
        }catch (Exception exception){
            exception.printStackTrace();
            flag = false;
        }finally {
            if(flag){
                transaction.commit();
                System.out.println("Object Saved");
            }else {
                transaction.rollback();
                System.out.println("Object not saved");
            }
            session.close();
            sessionFactory.close();
        }

    }
}

/*
    -> By default Hibernate uses built in JDBC Connection pool (It contain minimum 1 and maximum 20),
    and allow to use 3 party connection pool.

    -> But in production environment we have to use other Connection pool like HickariCP etc.

    -> If hibernate code is placed in standalone application then use:
        1) Hikari CP (BEST)
        2) Apache DBCP2
        3) C3P0
        4) Proxool
        5) Vibur
            and etc..
    -> If hibernate code is placed in web application and distributed application then use:
         1) Server managed JDBC connection pool. EG: Tomcat managed connection pool, Weblogic managed connection pool

    -> Difference between Session and SeesionFactory Object:
    Session Factory	Session Object:
    Session Factory:
        •	Heavy object contain multiple services
        •	It is the factory to create the objects
        •	It is the immutable object, no one can modify
        •	It is thread safe by default
        •	It is taken as one per database software
        •	It is long lived Object
        •	Maintains optional cache L2
        •	The configuration done in hibernate mapping file will create multiple service in SessionFactory object
        •	Created by Builder Pattern
    Session Object
        •	Light weight object less services
        •	It is base object to application to give persistence instruction to hibernate.
        •	It is mutable object we can modified
        •	It is not thread safe by default
        •	It is taken as one per each persistence operation or one per related persistence operation.
        •	It comes under short lived object
        •	Maintains the mandatory First Level cache/ L1 cache
        •	Uses most of the services collecting from SessionFactory object
        •	Created by Factory Pattern




*/
