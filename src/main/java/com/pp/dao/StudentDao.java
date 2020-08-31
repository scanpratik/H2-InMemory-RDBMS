package com.pp.dao;


import com.pp.entity.Student;
import com.pp.util.H2Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class StudentDao {
    public void saveStudent(Student student) {
        Transaction transaction = null;
        try (Session session = H2Util.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the student object
            session.save(student);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateStudent(Student student) {
        Transaction transaction = null;
        try (Session session = H2Util.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the student object
            session.update(student);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void getStudent(int id) {
        Transaction transaction = null;
        try (Session session = H2Util.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();

            // get Student entity using get() method
            Student student = session.get(Student.class, id);
            System.out.println(student.getFirstName());
            System.out.println(student.getEmail());

            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void deleteStudent(int id) {
        Transaction transaction = null;
        try (Session session = H2Util.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();

            // Delete a persistent object
            Student student = session.get(Student.class, id);
            if (student != null) {
                session.delete(student);
                System.out.println("student 1 is deleted");
            }

            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List< Student > getStudents() {
        try (Session session = H2Util.getSessionFactory().openSession()) {
            String query = "from Student";
            return session.createQuery(query , Student.class).list();
        }
    }
}
