package example.controllers.Course;

import example.dao.CourseDao;
import example.models.Course;
import example.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/getCourseList")
public class getCourseList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        // 获取 SqlSession 实例
        SqlSession sqlSession = MyBatisUtil.getSqlSession();

        // 创建 CourseDao 对象
        CourseDao courseDao = new CourseDao(sqlSession);

        // 返回数据
        List<Course> courseList = courseDao.getAllCourses();


        // 构建 JSON 数组
        JsonArrayBuilder courseArrayBuilder = Json.createArrayBuilder();
        for (Course course : courseList) {
            JsonObjectBuilder courseJsonBuilder = Json.createObjectBuilder()
                    .add("id", course.getId())
                    .add("title", course.getTitle());

            if (course.getInstrument() != null) {
                courseJsonBuilder.add("instrument", course.getInstrument());
            }

            if (course.getDifficulty() != null) {
                courseJsonBuilder.add("difficulty", course.getDifficulty());
            }

            if (course.getTeacher() != null) {
                courseJsonBuilder.add("teacher", course.getTeacher());
            }

            if (course.getDescription() != null) {
                courseJsonBuilder.add("description", course.getDescription());
            }

            if (course.getStatus() != null) {
                courseJsonBuilder.add("status", course.getStatus());
            }

            if (course.getImg_url() != null) {
                courseJsonBuilder.add("imgUrl", course.getImg_url());
            }

            JsonObject courseJson = courseJsonBuilder.build();
            courseArrayBuilder.add(courseJson);
        }

        JsonArray courseArray = courseArrayBuilder.build();

        // 构建最终的 JSON 响应
        JsonObject responseJson = Json.createObjectBuilder()
                .add("CourseList", courseArray)
                .build();

        // 设置响应类型为 JSON
        resp.setContentType("application/json");

        // 发送响应
        resp.getWriter().println(responseJson.toString());

        // 提交事务
        sqlSession.commit();
        sqlSession.close();
    }

}
