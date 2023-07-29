package example.models;

import java.util.Date;

public class CourseLearn {
    private int id;
    private int userId;
    private int courseId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public Date getLearnDate() {
        return learnDate;
    }

    public void setLearnDate(Date learnDate) {
        this.learnDate = learnDate;
    }

    private Date learnDate;

    // Getters and Setters
    // ...
}
