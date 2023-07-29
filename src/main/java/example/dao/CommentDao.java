package example.dao;

import example.models.Comment;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class CommentDao {
    private final SqlSession sqlSession;

    public CommentDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public void addComment(Comment comment) {
        sqlSession.insert("example.dao.CommentDao.addComment", comment);
    }

    public void updateComment(Comment comment) {
        sqlSession.update("example.dao.CommentDao.updateComment", comment);
    }

    public void deleteComment(int commentId) {
        sqlSession.delete("example.dao.CommentDao.deleteComment", commentId);
    }

    public Comment getCommentById(int commentId) {
        return sqlSession.selectOne("example.dao.CommentDao.getCommentById", commentId);
    }

    public List<Comment> getAllComments() {
        return sqlSession.selectList("example.dao.CommentDao.getAllComments");
    }
}
