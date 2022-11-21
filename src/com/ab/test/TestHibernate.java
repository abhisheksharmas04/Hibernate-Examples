package com.ab.test;

import com.ab.entity.Product;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

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
        product.setPid(1002);
        product.setPname("TV");
        product.setPrice(9000.98);
        product.setQty(10.0);
        product.setStatus("available");

        Transaction transaction = null;
        Boolean flag = false;

        try {
            transaction = session.beginTransaction();
            /*begin transaction by disableing autocommit mode underlying on DB S/w
                By default it uses JDBC for Transaction Management.

                Internally calls con.setAutoCommit(false) to simple begin the transaction.

                Hibernate can use either JDBC or JTA for transaction Management. By default it uses JDBC
                to change to JTA we need to add extra instruction in hibernate.cfg.xml file.

                NOTE:: Transaction object in hibernate is nothing but the object of underlying hibernate
                framework supplied java class that implements org.hibernate.Transaction(I)

                In Plain JDBC: To being the transaction we call con.setAutoCommit(false)
                To commit the transaction we call con.commit();
                To rollback transaction we call con.rollBack();

                In JTA Tx Management:
                    InitalContext ic = new InitialContext();
                    UserTransaction ut = (UserTransaction).lookup("---");
                    To begain Tx --> ut.begin()
                    To commit Tx --> ut.commit()
                    To rollback Tx --> ut.rollBack()

                In Hibernate Tx Management:
                    To begain transcation : Transaction tx = Session.beginTransaction()
                    To commit Tx: tx.commit()
                    To rollback Tx: tx.rollback()
            */
            //session.save(product);
            int idVal = (int) session.save(product);
            System.out.println("Generated Id value: " + idVal);
            /*
                session.save(product) to only save the object but also return the ID value
                session.save(product) perform the following :
                    (1) Takes the entity object and identifies the id field from mapping file.
                    (2) Gives save object persistance instructions to hibernate framework.
                    (3) keeps the received objects of entity class in the first level cache i.e. l1 cache.
                    (4) Gathers/ Generate id value from id property and returns back to client application as Serializable object

                    NOTE:: Wrapper class objects are serializable object by default.
                Signature of session.save() method:
                    public Serializable save(Object obj)

                  session.save() method does not sends and execute query in db s/w, it just give save object persistance instruction to Hibernate f/w
                  the actual persistance take place when Tx.commit() method is called

                  if the id generator is cfg using <generator> then the session.save() method returns the
                  generated id value back to client app otherwise it returns the value assigned to id property
                  as the id value back to app.

            */
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
                /*
                    Tx.commit():
                        (1) collects objects of entity classes that are there in L1 cache or first level cache
                            on which pending persistence operation are there.
                        (2) takes the  persistence instruction to hibernate framework like save object instruction by
                            calling session.save() method.
                        (3) Generates/uses SQL queries to complete persistence instructions like insert sql for session.save()
                            sends and execute that query in DB s/w to complete persistence operations like inserting records
                        (4) Call conn.commit() or ut.commit() internally


                */
                System.out.println("Object Saved");
            }else {
                transaction.rollback();
                /*
                    Tx.rollback():
                        (1) Tx.rollback() method do undo changes happened in db s/w
                */
                System.out.println("Object not saved");
            }
            session.close();
            /*
                session.close() close the session with DB s/w from hibernate app
                Internally call con.close() method to realsed the JDCB con object back to connection pool
                Vanishes L1/First level cache by destroying object in it.
                return the realised connection object to client applicaiton.
            */
            sessionFactory.close();
            /*
                sessionFactory.close();
                    (1) Destroys the sessionFactory i.e. deactivates the hibernate framework by realising
                    multiple service resources that are associated with sessionFactory objects:
                    they are JDCB connection pool, L2 Cache, SecondLevel cache, Dialect service.

                    NOTE:: first close all active session objects before closing all session factory object.
            */
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
