package com.ab.test;

import com.ab.entity.Customer;
import com.ab.entity.Person;
import com.ab.entity.Student;

public class InheritanceTest {
    public static void main(String[] args) {
        Student st = Person.showDetails(Student.class, 1);
        Customer ct = Person.showDetails(Customer.class,101);

    }
}
