package example.dao;

import example.models.User;
import example.utils.PasswordUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class UserDao {
    private final SqlSession sqlSession;

    public UserDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<User> getAllUsers() {
        return sqlSession.selectList("example.dao.UserDao.getAllUsers");
    }

    public void addUser(User user) {
        sqlSession.insert("example.dao.UserDao.addUser", user);
    }

    public void updateUser(User user) {
        sqlSession.update("example.dao.UserDao.updateUser", user);
    }

    public void deleteUser(int userId) {
        sqlSession.delete("example.dao.UserDao.deleteCourseLearnByUserId", userId);
        sqlSession.update("example.dao.UserDao.deleteUserSetNull", userId);
        sqlSession.delete("example.dao.UserDao.deleteUser", userId);
    }



    public boolean validateLogin(String username, String password) {
        User user = getUserByUsername(username);
        if (user != null) {
            String hashedPassword = user.getPassword();
            return PasswordUtil.verifyPassword(password, hashedPassword);
        }
        return false;
    }

    public User getUserByUsername(String username) {
        return sqlSession.selectOne("example.dao.UserDao.getUserByUsername", username);
    }

    public User getUserByEmail(String Email) {
        return sqlSession.selectOne("example.dao.UserDao.getUserByEmail", Email);
    }
    public User getUserById(int id) {
        return sqlSession.selectOne("example.dao.UserDao.getUserById", id);
    }
}
