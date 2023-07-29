package example.dao;

import example.models.Course;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class CourseDao {
    private final SqlSession sqlSession;

    public CourseDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public void addCourse(Course course) {
        sqlSession.insert("example.dao.CourseDao.addCourse", course);
    }

    public void updateCourse(Course course) {
        sqlSession.update("example.dao.CourseDao.updateCourse", course);
    }

    public void deleteCourse(int courseId) {
        sqlSession.delete("example.dao.CourseDao.deleteCourse", courseId);
    }

    public Course getCourseById(int courseId) {
        return sqlSession.selectOne("example.dao.CourseDao.getCourseById", courseId);
    }

    public List<Course> getAllCourses() {
        return sqlSession.selectList("example.dao.CourseDao.getAllCourses");
    }

    public List<Course> getCoursesByCondition(Course course) {
        return sqlSession.selectList("example.dao.CourseDao.getCoursesByCondition", course);
    }
}
