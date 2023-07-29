//
//
//import example.dao.CommentDao;
//import example.models.Comment;
//import example.utils.MyBatisUtil;
//import org.apache.ibatis.session.SqlSession;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.apache.ibatis.session.SqlSessionFactoryBuilder;
//
//import java.io.InputStream;
//import java.util.Date;
//import java.util.List;
//
//public class CommentDaoTest {
//    public static void main(String[] args) {
//        SqlSession sqlSession = MyBatisUtil.getSqlSession();
//
//        // 创建 CommentDao
//        CommentDao commentDao = new CommentDao(sqlSession);
//
//        // 添加评论
//        Comment comment = new Comment(5,3,7,"test1",5,new Date());
//        commentDao.addComment(comment);
//
//
//        // 更新评论
//        comment.setContent("这是更新后的评论");
//        commentDao.updateComment(comment);
//
//        // 根据评论ID获取评论
//        Comment retrievedComment = commentDao.getCommentById(comment.getId());
//        System.out.println("Retrieved Comment: " + retrievedComment.getContent());
//
//        // 获取所有评论
//        List<Comment> allComments = commentDao.getAllComments();
//        System.out.println("All Comments:");
//        for (Comment c : allComments) {
//            System.out.println(c.getId() + " - " + c.getContent());
//        }
//
//        // 删除评论
////        commentDao.deleteComment(comment.getId());
//        sqlSession.commit();
//        commentDao.deleteComment(comment.getId());
//        sqlSession.commit();
//        // 关闭 SqlSession
//        sqlSession.close();
//    }
//}
