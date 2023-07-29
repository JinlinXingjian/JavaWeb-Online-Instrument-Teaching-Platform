package example.controllers.Course;

import com.alibaba.fastjson.JSONObject;
import example.dao.CourseDao;
import example.models.Course;
import example.utils.AdminUtils;
import example.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@WebServlet("/changeCourse")
public class changeCourse extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/plain;charset=UTF-8");
        // 获取注册表单参数
        // 从请求体中获取输入流
        InputStream inputStream = req.getInputStream();

        // 使用 JsonReader 解析输入流中的 JSON 数据
        JsonReader jsonReader = Json.createReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();

        // 获取 JSON 数据中的 username 和 newStatus 字段值
        int id = jsonObject.getInt("id");
        String title = jsonObject.getString("title");
        String status = jsonObject.getString("status");
        String instrument = jsonObject.getString("instrument");
        String teacher = jsonObject.getString("teacher");
        String difficulty = jsonObject.getString("difficulty");
        String description = jsonObject.getString("description");


        // 获取 SqlSession 实例
        SqlSession sqlSession = MyBatisUtil.getSqlSession();

        //建立响应json对象
        JSONObject resJsonObject=new JSONObject();

        //验证是否为管理员
        if(AdminUtils.isAdmin(req)){
            CourseDao courseDao=new CourseDao(sqlSession);
            Course courseById = courseDao.getCourseById(id);
            //更新信息
            courseById.setTitle(title);
            courseById.setStatus(status);
            courseById.setDescription(description);
            courseById.setInstrument(instrument);
            courseById.setTeacher(teacher);
            courseById.setDifficulty(difficulty);

            courseDao.updateCourse(courseById);
            sqlSession.commit();
            courseById = courseDao.getCourseById(id);
            resJsonObject.put("message","changeCourse success");
        }
        else{
            resJsonObject.put("message","role error");
        }

        resp.getWriter().print(JSONObject.toJSON(resJsonObject));
        sqlSession.close();
     }
}
