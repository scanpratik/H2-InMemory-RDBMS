package com.pp;

import com.pp.entity.Student;
import com.pp.util.DBUtil;
import com.pp.util.H2Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class App {

    public static void main(String[] args) {

        List<Student> studentList = new ArrayList<>();

        for(int i=0 ; i<5 ; i++){

            studentList.add(new Student("Ramesh - "+i, "Fadatare", "rameshfadatare@javaguides.com - "+i));
        }

        Transaction transaction = null;
        try (Session session = DBUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the student objects
            for (Student student : studentList){
                session.saveOrUpdate(student);
            }
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        try (Session session = H2Util.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the student objects
            for(Student student : studentList){
                session.save(student);
            }
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        Date date1 = new Date();
        System.out.println("Start -----MYSQL ---  > "+ date1.getTime());

        try (Session session = DBUtil.getSessionFactory().openSession()) {
            studentList = session.createQuery("from Student", Student.class).list();
            studentList.forEach(s -> System.out.println("----> "+s.getFirstName()));

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        Date date2 = new Date();
        long diff1 = date2.getTime() - date1.getTime();
        System.out.println("End -----MYSQL ---  > "+ date2.getTime());
        System.out.println("End -----MYSQL - diff1 ---  > "+ diff1);


        Date date3 = new Date();
        System.out.println("START -----H2 ---  > "+ date3.getTime());

        try (Session session = H2Util.getSessionFactory().openSession()) {
            studentList = session.createQuery("from Student", Student.class).list();
            studentList.forEach(s -> System.out.println(s.getFirstName()));
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        Date date4 = new Date();
        long diff2 = date4.getTime() - date3.getTime();
        System.out.println("End  -----H2 ---  > "+ date4.getTime());
        System.out.println("End -----H2 - diff2 ---  > "+ diff2);
    }
}
