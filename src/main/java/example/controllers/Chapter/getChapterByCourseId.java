package example.controllers.Chapter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import example.dao.ChapterDao;
import example.models.Chapter;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebServlet("/getChapterByCourseId")
public class getChapterByCourseId extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        // 从请求体中获取输入流
        InputStream inputStream = req.getInputStream();

        // 使用 JsonReader 解析输入流中的 JSON 数据
        JsonReader jsonReader = Json.createReader(new InputStreamReader(inputStream));
        JsonObject jsonObject_req = jsonReader.readObject();
        jsonReader.close();

        // 获取 JSON 数据中的 username 和 password 字段值
        String courseId = jsonObject_req.getString("courseId");
        System.out.println("courseId: " + courseId);
        // 获取 SqlSession 实例
        SqlSession sqlSession = MyBatisUtil.getSqlSession();

        ChapterDao chapterDao=new ChapterDao(sqlSession);

        // 创建一个用于存储 JSON 对象的列表
        List<JSONObject> jsonList = new ArrayList<>();

        for (Chapter chapter : chapterDao.getChapterByCourseId(Integer.parseInt(courseId))) {
            System.out.println("name: "+ chapter.isIs_chapter());
            //思路，对遇到的每个章节节点，在数组中遍历出子节点内容并构建成为数据
            //遇到章节节点
            if(chapter.isIs_chapter()){
                //新建数组准备接收小节内容
                JSONArray children = new JSONArray();
                for (Chapter chapter_in : chapterDao.getChapterByCourseId(Integer.parseInt(courseId))) {
                        //自身就跳过
                        if(chapter_in==chapter){
                            continue;
                        }
                        //查找到小节内容，开始插入
                        if(chapter_in.getChapter_id()==chapter.getChapter_id()){
                            // 创建 JSON 对象
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("id", chapter_in.getId());
                            jsonObject.put("chapter_name", chapter_in.getChapter_name());
                            jsonObject.put("video", chapter_in.getVideo());
                            jsonObject.put("is_chapter", chapter_in.isIs_chapter());
                            //添加到章节节点的children中
                            children.add(jsonObject);
                    }
                }
                //构建章节节点json
                // 创建 JSON 对象
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", chapter.getId());
                jsonObject.put("is_chapter", chapter.isIs_chapter());
                jsonObject.put("chapter_name", chapter.getChapter_name());
                jsonObject.put("children",children);
                //添加到res的数组中
                jsonList.add(jsonObject);
            }
        }
        // 将 JSON 对象列表转换为 JSON 数组
        JSONArray jsonArray = new JSONArray(Collections.singletonList(jsonList));

        // 输出 JSON 字符串
        String jsonString = jsonArray.toJSONString();

        // 设置响应类型为 JSON
        resp.setContentType("application/json");

        // 发送响应
        resp.getWriter().println(jsonString);


    }
}
