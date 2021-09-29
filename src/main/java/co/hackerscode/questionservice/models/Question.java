package co.hackerscode.questionservice.models;

public class Question {

    private int id=0;
    private String title=null;
    private String category=null;
    private String subcategory=null;
    private String questionurl=null;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    private int score = 0;

    private String exampleinputurl1=null;
    private String exampleoutputurl1=null;
    private String imageurl=null;

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    private String difficulty=null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuestionurl() {
        return questionurl;
    }

    public void setQuestionurl(String questionurl) {
        this.questionurl = questionurl;
    }

    public String getExampleinputurl1() {
        return exampleinputurl1;
    }

    public void setExampleinputurl1(String exampleinputurl1) {
        this.exampleinputurl1 = exampleinputurl1;
    }

    public String getExampleoutputurl1() {
        return exampleoutputurl1;
    }

    public void setExampleoutputurl1(String exampleoutputurl1) {
        this.exampleoutputurl1 = exampleoutputurl1;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
