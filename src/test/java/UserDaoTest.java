import example.dao.UserDao;
import example.models.User;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class UserDaoTest {

    public static void main(String[] args) {
        try {
            // 加载 MyBatis 配置文件
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            // 创建 SqlSessionFactory
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            // 创建 SqlSession
            SqlSession sqlSession = sqlSessionFactory.openSession();

            // 创建 UserDao 实例
            UserDao userDao = new UserDao(sqlSession);


            User user= userDao.getUserByUsername("newuser");
            if(user!=null){
                int userId=user.getId();
                userDao.deleteUser(userId);
            }

            // 调用方法进行测试
            testGetAllUsers(userDao);
            testAddUser(userDao);
            testUpdateUser(userDao);
            testDeleteUser(userDao);
            testValidateLogin(userDao);
            testGetUserByUsername(userDao);

            // 提交事务并关闭资源
            sqlSession.commit();
            sqlSession.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testGetAllUsers(UserDao userDao) {
        System.out.println("Testing getAllUsers:");
        List<User> users = userDao.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
        System.out.println();
    }

    private static void testAddUser(UserDao userDao) {
        System.out.println("Testing addUser:");
        User newUser = new User();
        // 设置新用户的属性
        newUser.setUsername("newuser");
        newUser.setPassword("newpassword");
        newUser.setEmail("newuser@example.com");
        newUser.setRole("learner");
        newUser.setStatus("active");
        newUser.setAvatar("avatar.jpg");

        userDao.addUser(newUser);
        System.out.println("User added successfully.");
        System.out.println();
    }

    private static void testUpdateUser(UserDao userDao) {
        System.out.println("Testing updateUser:");
        User user = userDao.getUserByUsername("newuser");
        if (user != null) {
            // 修改用户的属性
            user.setEmail("newemail@example.com");
            user.setAvatar("new_avatar.jpg");

            userDao.updateUser(user);
            System.out.println("User updated successfully.");
        } else {
            System.out.println("User not found.");
        }
        System.out.println();
    }

    private static void testDeleteUser(UserDao userDao) {
        System.out.println("Testing deleteUser:");
        User user= userDao.getUserByUsername("newuser");
        int userId=user.getId();
        userDao.deleteUser(userId);
        System.out.println("User deleted successfully.");
        System.out.println();
    }

    private static void testValidateLogin(UserDao userDao) {
//        System.out.println("Testing validateLogin:");
//        String username = "newuser"; // 根据实际情况设置要验证的用户名和密码
//        String password = "newpassword";
//        boolean isValid = userDao.validateLogin(username, password);
//        System.out.println("Login validation result: " + isValid);
        System.out.println("vail is null\n");
    }

    private static void testGetUserByUsername(UserDao userDao) {
        System.out.println("Testing getUserByUsername:");
        String username = "newuser"; // 根据实际情况设置要获取的用户名
        User user = userDao.getUserByUsername(username);
        if (user != null) {
            System.out.println("User found: " + user);
        } else {
            System.out.println("User not found.");
        }
        System.out.println();
    }
}
