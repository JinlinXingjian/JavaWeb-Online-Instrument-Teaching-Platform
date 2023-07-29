

import example.dao.ChapterDao;
import example.models.Chapter;
import org.apache.ibatis.session.SqlSession;
import example.utils.MyBatisUtil;
import java.util.List;

public class ChapterDaoTest {

    public static void main(String[] args) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            // 创建 ChapterDao 实例
            ChapterDao chapterDao = new ChapterDao(sqlSession);

            // 测试添加章节
            Chapter newChapter = new Chapter();
            newChapter.setId(6);
            newChapter.setChapter_name("Introduction");
            newChapter.setCourse_id(2);
            newChapter.setChapter_id(1);
            newChapter.setVideo("https://example.com/intro.mp4");
            newChapter.setIs_chapter(true);
            newChapter.setChapter_small_id(3);
            chapterDao.addChapter(newChapter);
            System.out.println("Chapter added successfully.");

            // 测试更新章节
            Chapter existingChapter = chapterDao.getChapterById(1);
            existingChapter.setChapter_name("Updated Chapter");
            existingChapter.setVideo("https://example.com/updated.mp4");
            chapterDao.updateChapter(existingChapter);
            System.out.println("Chapter updated successfully.");

            // 测试删除章节
            int chapterIdToDelete = 6;
            chapterDao.deleteChapter(chapterIdToDelete);
            System.out.println("Chapter deleted successfully.");

            // 测试获取章节
            int chapterIdToGet = 2;
            Chapter retrievedChapter = chapterDao.getChapterById(chapterIdToGet);
            System.out.println("Retrieved Chapter: " + retrievedChapter);

            // 测试获取所有章节
            List<Chapter> allChapters = chapterDao.getAllChapters();
            System.out.println("All Chapters:");
            for (Chapter chapter : allChapters) {
                System.out.println(chapter);
            }

            // 测试根据条件查询章节
            Chapter searchCriteria = new Chapter();
            searchCriteria.setChapter_name("Introduction");
            searchCriteria.setCourse_id(1);
            searchCriteria.setChapter_id(1);
            searchCriteria.setIs_chapter(true);
            List<Chapter> searchedChapters = chapterDao.getChaptersByCondition(searchCriteria);
            System.out.println("Searched Chapters:");
            for (Chapter chapter : searchedChapters) {
                System.out.println(chapter);
            }

            sqlSession.commit();
        }
    }
}
