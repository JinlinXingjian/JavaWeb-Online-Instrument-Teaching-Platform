import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import example.dao.CommentDao;
import example.dao.UserDao;
import example.models.Comment;
import example.models.User;
import example.utils.MemoryCodeStorage;
import example.utils.MyBatisUtil;
import example.utils.VerificationCodeGenerator;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        // 获取 SqlSession 实例
        SqlSession sqlSession = MyBatisUtil.getSqlSession();

        UserDao userDao=new UserDao(sqlSession);

        System.out.println(JSON.toJSON(userDao.getUserByUsername("admin3")));
    }

}
