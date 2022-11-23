package com.ab.test;

import com.ab.entity.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class TestPersistanceMethod {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();

        configuration.configure("/com/ab/cgfs/hibernate.cfg.xml");

        SessionFactory sessionFactory = configuration.buildSessionFactory();

        Session session = sessionFactory.openSession();

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
            session.persist(product);
            flag = true;
        } catch (HibernateException hbe) {
            hbe.printStackTrace();
            flag = false;
        } catch (Exception exception) {
            exception.printStackTrace();
            flag = false;
        } finally {
            if (flag) {
                transaction.commit();
                System.out.println("Object Saved");
            } else {
                transaction.rollback();
                System.out.println("Object not saved");
            }
            session.close();
            sessionFactory.close();
        }
    }
}
/*
Signature of session.save(--) method:
    public Serializable save(Object obj)
save() return type is Serializable because, We can generally take either simple data type or wrapper datatype
or String data type property as Identity property/id field i.e. save return the generated/ gathered id value
either in the form of Wrapper classes object or String class object. Since all Wrapper classes and String classes
are implementing Serializable interface. The Serializable(I) is given as return type.

Signature of persists method:
    public void persist(Object obj);

Difference between session.save() & session.persist()
Session.save():
•	Give save object persistence instruction to hibernate and return either generated or gathered id value.
•	Supports to work with different type of Id generator like increment, sequence, hilo and etc.. to generate the id value for the id field.
•	Return type is Serializable i.e return the generated id value as the Serializable object (either in Wrapper class object or in String class object)
•	It is the direct method of hibernate specification i.e. not kept in hibernate api based on JPA specification.
•	Used very frequently in the projects

session.persist():
•	Give save object persistence instruction to hibernate and does not return either generated or gathered id value.
•	Does not support Id generators, so always take the assigned value as the Id value.
•	Return type is void i.e. does not return any id value.
•	It is given in hibernate api based on JPA specification rules and guidelines that should be followed by hibernate.
•	Not too much used






*/