package co.hackerscode.questionservice.models;

public class Question {

    private int id=0;
    private String title=null;
    private String category=null;
    private String subcategory=null;
    private String questionurl=null;
    private String exampleinputurl1=null;
    private String exampleoutputurl1=null;
    private String exampleinputurl2=null;
    private String exampleoutputurl2=null;
    private String imageurl=null;

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

    public String getSub_category() {
        return subcategory;
    }

    public void setSub_category(String sub_category) {
        this.subcategory = sub_category;
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

    public String getExampleinputurl2() {
        return exampleinputurl2;
    }

    public void setExampleinputurl2(String exampleinputurl2) {
        this.exampleinputurl2 = exampleinputurl2;
    }

    public String getExampleoutputurl2() {
        return exampleoutputurl2;
    }

    public void setExampleoutputurl2(String exampleoutputurl2) {
        this.exampleoutputurl2 = exampleoutputurl2;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
