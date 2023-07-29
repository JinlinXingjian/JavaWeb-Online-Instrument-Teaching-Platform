package example.dao;

import example.models.Chapter;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ChapterDao {
    private final SqlSession sqlSession;

    public ChapterDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public void addChapter(Chapter chapter) {
        sqlSession.insert("example.dao.ChapterDao.addChapter", chapter);
    }

    public void updateChapter(Chapter chapter) {
        sqlSession.update("example.dao.ChapterDao.updateChapter", chapter);
    }

    public void deleteChapter(int chapterId) {
        sqlSession.delete("example.dao.ChapterDao.deleteChapter", chapterId);
    }

    public Chapter getChapterById(int chapterId) {
        return sqlSession.selectOne("example.dao.ChapterDao.getChapterById", chapterId);
    }

    public List<Chapter> getChapterByCourseId(int courseId) {
        return sqlSession.selectList("example.dao.ChapterDao.getChapterByCourseId", courseId);
    }

    public List<Chapter> getAllChapters() {
        return sqlSession.selectList("example.dao.ChapterDao.getAllChapters");
    }

    public List<Chapter> getChaptersByCondition(Chapter chapter) {
        return sqlSession.selectList("example.dao.ChapterDao.getChaptersByCondition", chapter);
    }
}
