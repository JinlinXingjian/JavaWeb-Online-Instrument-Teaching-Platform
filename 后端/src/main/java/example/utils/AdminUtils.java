package example.utils;

import example.dao.UserDao;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

public class AdminUtils {
    public static boolean isAdmin (HttpServletRequest request){
        // 从请求头中获取 Token
        String token = request.getHeader("Authorization");
        //如果token为空就返回false
        if(token==null||token==""){
            return  false;
        }
        //获取username
        String username=JwtUtils.getUsernameFromJwt(token);
        //获取sqlSession
        SqlSession sqlSession=MyBatisUtil.getSqlSession();
        UserDao userDao=new UserDao(sqlSession);
        //判断
        if(userDao.getUserByUsername(username).getRole().equals("admin")){
            return true;
        }
        return false;
    }
}
