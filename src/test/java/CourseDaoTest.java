

import example.dao.CourseDao;
import example.models.Course;
import org.apache.ibatis.session.SqlSession;
import example.utils.MyBatisUtil;
import java.util.List;

public class CourseDaoTest {

    public static void main(String[] args) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            // 创建 CourseDao 实例
            CourseDao courseDao = new CourseDao(sqlSession);

            // 测试添加课程
            Course newCourse = new Course();
            newCourse.setId(1);
            newCourse.setTitle("Java Programming");
            newCourse.setInstrument("Computer");
            newCourse.setDifficulty("Intermediate");
            newCourse.setTeacher("John Doe");
            newCourse.setDescription("Learn Java programming language.");
            newCourse.setStatus("Active");
            newCourse.setImg_url("https://example.com/java.jpg");
            courseDao.addCourse(newCourse);
            System.out.println("Course added successfully.");

            // 测试更新课程
            Course existingCourse = courseDao.getCourseById(1);
            existingCourse.setTitle("Updated Course");
            existingCourse.setDescription("Updated description");
            courseDao.updateCourse(existingCourse);
            System.out.println("Course updated successfully.");

            // 测试删除课程
            int courseIdToDelete = 1;
            courseDao.deleteCourse(courseIdToDelete);
            System.out.println("Course deleted successfully.");

            // 测试获取课程
            int courseIdToGet = 2;
            Course retrievedCourse = courseDao.getCourseById(courseIdToGet);
            System.out.println("Retrieved Course: " + retrievedCourse);

            // 测试获取所有课程
            List<Course> allCourses = courseDao.getAllCourses();
            System.out.println("All Courses:");
            for (Course course : allCourses) {
                System.out.println(course);
            }

            // 测试根据条件查询课程
            Course searchCriteria = new Course();
            searchCriteria.setTitle("Java");
            List<Course> searchedCourses = courseDao.getCoursesByCondition(searchCriteria);
            System.out.println("Searched Courses:");
            for (Course course : searchedCourses) {
                System.out.println(course);
            }

            sqlSession.commit();
        }
    }
}
