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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@WebServlet("/getCourse")
public class getCourse extends HttpServlet {
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

        // 从请求体中获取输入流
        InputStream inputStream = req.getInputStream();

        // 使用 JsonReader 解析输入流中的 JSON 数据
        JsonReader jsonReader = Json.createReader(new InputStreamReader(inputStream));
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();

        // 获取 JSON 数据中的 username 和 password 字段值
        String courseId = jsonObject.getString("courseId");

        // 返回数据
        Course course = courseDao.getCourseById(Integer.parseInt(courseId));


        // 构建 JSON 数组
        JsonArrayBuilder courseArrayBuilder = Json.createArrayBuilder();
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



        // 设置响应类型为 JSON
        resp.setContentType("application/json");

        // 发送响应
        resp.getWriter().println(courseJson.toString());

        // 提交事务
        sqlSession.commit();
        sqlSession.close();
    }

}
