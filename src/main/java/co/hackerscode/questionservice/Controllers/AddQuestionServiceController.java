package co.hackerscode.questionservice.Controllers;

import co.hackerscode.questionservice.Dao.QuestionDaoImpl;
import co.hackerscode.questionservice.models.Question;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static co.hackerscode.questionservice.Dao.QuestionDaoConstants.*;

@RestController
public class AddQuestionServiceController {
    @Autowired
    private QuestionDaoImpl questionDao;


    @RequestMapping(value = "/addQuestion",method = RequestMethod.POST , consumes = "application/json", produces = "application/json")
    public ResponseEntity add(@RequestBody String jsonString)
    {
        JSONObject jsonObject = new JSONObject(jsonString);
        Question question = new Question();
        if(!(jsonObject.has("password")&&jsonObject.getString("password").equals("Dtbranger")))
        {
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("message", "password didnt match");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jsonResponse.toString());
        }


        question.setTitle(jsonObject.getString(COLUMN_TITLE));
        question.setQuestionurl(jsonObject.getString(COLUMN_QUESTIONURL));
        question.setCategory(jsonObject.getString(COLUMN_CATEGORY));
        if(jsonObject.has(COLUMN_SUBCATEGORY))
        {
            question.setSubcategory(jsonObject.getString(COLUMN_SUBCATEGORY));
        }
        question.setExampleinputurl1(jsonObject.getString(COLUMN_EXAMPLEINPUTURL1));
        question.setExampleoutputurl1(jsonObject.getString(COLUMN_EXAMPLEOUTPUTURL1));
        question.setExampleoutputurl2(jsonObject.getString(COLUMN_EXAMPLEOUTPUTURL2));
        question.setExampleinputurl2(jsonObject.getString(COLUMN_EXAMPLEINPUTURL2));
        question.setDifficulty(jsonObject.getString(COLUMN_DIFFICULTY));
        if(jsonObject.has(COLUMN_IMAGEURL))
        {
            question.setImageurl(jsonObject.getString(COLUMN_IMAGEURL));
        }
        if(questionDao.addQuestion(question))
        {
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("status" , "success");
            jsonResponse.put("added-question" , jsonObject);
            return ResponseEntity.ok().body(jsonResponse.toString());
        }
        else
        {
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("status" , "failure");
            jsonResponse.put("question-failed-to-add" , jsonObject);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(jsonResponse.toString());
        }
    }


}
