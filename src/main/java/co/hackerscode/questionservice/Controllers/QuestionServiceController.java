package co.hackerscode.questionservice.Controllers;

import co.hackerscode.questionservice.Dao.QuestionDaoImpl;
import co.hackerscode.questionservice.models.Question;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static co.hackerscode.questionservice.Dao.QuestionDaoConstants.*;

@RestController
public class QuestionServiceController {
    @Autowired
    private QuestionDaoImpl questionDao;
    @RequestMapping(value = "/addquestion",method = RequestMethod.POST , consumes = "application/json", produces = "application/json")
    public ResponseEntity addQuestion(@RequestBody String jsonString){
        JSONObject jsonObject = new JSONObject(jsonString);
        Question question = new Question();
        if(!(jsonObject.has("password")&&jsonObject.getString("password")=="Dtbranger"))
        {
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("message", "password didnt match");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jsonResponse.toString());
        }


        question.setTitle(jsonObject.getString(COLUMN_TITLE));
        question.setQuestionurl(jsonObject.getString(COLUMN_QUESTIONURL));
        question.setCategory(jsonObject.getString(COLUMN_CATEGORY));
        question.setSub_category(jsonObject.getString(COLUMN_SUBCATEGORY));
        question.setExampleinputurl1(jsonObject.getString(COLUMN_EXAMPLEINPUTURL1));
        question.setExampleoutputurl1(jsonObject.getString(COLUMN_EXAMPLEOUTPUTURL1));
        question.setExampleoutputurl2(jsonObject.getString(COLUMN_EXAMPLEOUTPUTURL2));
        question.setExampleinputurl2(jsonObject.getString(COLUMN_EXAMPLEINPUTURL2));
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
    @RequestMapping(value = "/getquestion",method = RequestMethod.GET , consumes = "application/json", produces = "application/json")
    public ResponseEntity getQuestion(@RequestBody String jsonString)
    {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject jsonResponse = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<Question> resultList = null;
        if(!(jsonObject.has("category"))&&(!jsonObject.has(COLUMN_ID)))
        {
            jsonResponse.put("message","no id and category specified");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse.toString());
        }
        if((jsonObject.has(COLUMN_CATEGORY))&&(!jsonObject.has(COLUMN_SUBCATEGORY)) && (!jsonObject.has(COLUMN_ID)))
        {
           resultList = questionDao.getQuestionByCategory(jsonObject.getString(COLUMN_CATEGORY));
        }
        else if(jsonObject.has(COLUMN_SUBCATEGORY))
        {
            resultList = questionDao.getQuestionByCategoryAndSubCategory(jsonObject.getString(COLUMN_CATEGORY),jsonObject.getString(COLUMN_SUBCATEGORY));
        }
        else if(jsonObject.has(COLUMN_ID))
        {
            Question question = null;
            question = questionDao.getQuestionById(jsonObject.getInt(COLUMN_ID));
            if(question!=null)
             resultList.add(question);
        }
        if(resultList.size()==0)
        {
            jsonResponse.put("message","no question found for specified category");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(jsonResponse.toString());
        }
        else
        {
            jsonResponse.put("count", resultList.size());
        }
        for(Question question : resultList)
        {
            jsonArray.put(new JSONObject(question));
        }
        jsonResponse.put("list",jsonArray);
        return ResponseEntity.ok().body(jsonResponse.toString());
    }
    @RequestMapping(value = "/updatequestion",method = RequestMethod.POST , consumes = "application/json", produces = "application/json")
    public ResponseEntity updateQuestion(@RequestBody String jsonString)
    {
        JSONObject jsonResponse ;
        JSONObject jsonObject = new JSONObject(jsonString);
        if(!(jsonObject.has(COLUMN_ID)&&jsonObject.has("question")))
        {
            jsonResponse = new JSONObject();
            jsonObject.put("message" , "BAD REQUEST");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse.toString());
        }

        int id = jsonObject.getInt(COLUMN_ID);
        jsonObject = jsonObject.getJSONObject("question");
        if(questionDao.updateQuestion(jsonObject, id))
        {
            jsonResponse = new JSONObject();
            jsonResponse.put("message" , "Success , Question with id = " + id + " updated");
            return ResponseEntity.ok().body(jsonResponse);
        }
        else {
            jsonResponse =  new JSONObject();
            jsonResponse.put("message" , "unown error");
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(jsonResponse);
        }
    }

}
