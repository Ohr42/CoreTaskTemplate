package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.util.List;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserDaoHibernateImpl extends UserDaoJDBCImpl implements UserDao {
    private SessionFactory sessionFactory = Util.getSessionFactory();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
            String creatTable = "CREATE TABLE IF NOT EXISTS user_table"
                + "(id INTEGER not NULL AUTO_INCREMENT PRIMARY KEY ,"
                + "name VARCHAR(50),"
                + "lastName VARCHAR(50),"
                + "age INT(3))";
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(creatTable)
                .executeUpdate();
            transaction.commit();
            session.close();
    }

    @Override
    public void dropUsersTable() {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS user_table")
                .executeUpdate();
            transaction.commit();
            session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.save(new User(name,lastName,age));
            transaction.commit();
            String addUser = new String("User с именем – " + name +" добавлен в базу данных");
            System.out.println(addUser);
            session.close();
    }

    @Override
    public void removeUserById(long id) {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            User user = (User) session.load(User.class, id);
            session.delete(user);
            transaction.commit();
            session.close();
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = null;
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            list = session.createQuery("from User").list();
            transaction.commit();
            session.close();
        return list;
    }

    @Override
    public void cleanUsersTable() {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            transaction.commit();
            session.close();
    }
}
