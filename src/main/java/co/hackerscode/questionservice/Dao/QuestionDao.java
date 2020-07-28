package co.hackerscode.questionservice.Dao;

import co.hackerscode.questionservice.models.Question;
import org.json.JSONObject;

import java.util.List;

public interface QuestionDao {
    public List<Question> getQuestionByCategory(String category, int page);
    public Question getQuestionById(int id);

    public List<Question> getQuestionByCategoryAndSubCategory(String category, String subCategory, int page);
    public boolean addQuestion(Question question);
    public boolean updateQuestion(JSONObject jsonObject , int id);
    public boolean deleteQuestion(int id);
    public List<Question> getAll();
}
