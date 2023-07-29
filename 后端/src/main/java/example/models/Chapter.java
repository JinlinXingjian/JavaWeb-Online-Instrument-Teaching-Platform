package example.models;

public class Chapter {
    private int id;
    private String chapter_name;
    private int course_id;
    private int chapter_id;
    private String video;
    private boolean is_chapter;
    private int chapter_small_id;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChapter_name() {
        return chapter_name;
    }

    public void setChapter_name(String chapter_name) {
        this.chapter_name = chapter_name;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(int chapter_id) {
        this.chapter_id = chapter_id;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public boolean isIs_chapter() {
        return is_chapter;
    }

    public void setIs_chapter(boolean is_chapter) {
        this.is_chapter = is_chapter;
    }

    public int getChapter_small_id() {
        return chapter_small_id;
    }

    public void setChapter_small_id(int chapter_small_id) {
        this.chapter_small_id = chapter_small_id;
    }
}
