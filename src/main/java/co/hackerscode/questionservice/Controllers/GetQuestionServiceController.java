package co.hackerscode.questionservice.Controllers;

import co.hackerscode.questionservice.Dao.QuestionDaoImpl;
import co.hackerscode.questionservice.models.Question;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static co.hackerscode.questionservice.Dao.QuestionDaoConstants.*;

@RestController
public class GetQuestionServiceController {

    @Autowired
    private QuestionDaoImpl questionDao;

    @GetMapping("/getQuestion")
    @ResponseBody
    public ResponseEntity getQuestion(@RequestParam(required = false) String category,@RequestParam(required = false) String subCategory,@RequestParam(required = false) String id, @RequestParam(required = false) String page)
    {

        JSONObject jsonResponse = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<Question> resultList = null;
        if((category==null)&&(id==null))
        {
            jsonResponse.put("message","no id and category specified");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse.toString());
        }
        if((category!=null)&&(subCategory==null) && (id==null))
        {
            resultList = questionDao.getQuestionByCategory(category, Integer.parseInt(page));
        }
        else if(subCategory!=null)
        {
            resultList = questionDao.getQuestionByCategoryAndSubCategory(category, subCategory, Integer.parseInt(page));
        }
        else if(id!=null)
        {
            System.out.println(Integer.parseInt(id));
            Question question = null;
            question = questionDao.getQuestionById(Integer.parseInt(id));
            if(question!=null){
                resultList = new ArrayList<>();
                resultList.add(question);
            }
        }
        if(resultList==null || resultList.size()==0)
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
        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Access-Control-Allow-Methods", "POST, OPTIONS, GET");
        resHeaders.add("Access-Control-Allow-Origin", "*");
        resHeaders.add("Access-Control-Allow-Headers", "*");
        return ResponseEntity.ok().headers(resHeaders).body(jsonResponse.toString());
    }

    @RequestMapping(value = "/updateQuestion",method = RequestMethod.POST , produces = "application/json")
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
            HttpHeaders resHeaders = new HttpHeaders();
            resHeaders.add("Access-Control-Allow-Methods", "POST, OPTIONS, GET");
            resHeaders.add("Access-Control-Allow-Origin", "*");
            resHeaders.add("Access-Control-Allow-Headers", "*");
            return ResponseEntity.ok().headers(resHeaders).body(jsonResponse);
        }
        else {
            jsonResponse =  new JSONObject();
            jsonResponse.put("message" , "unown error");
            HttpHeaders resHeaders = new HttpHeaders();
            resHeaders.add("Access-Control-Allow-Methods", "POST, OPTIONS, GET");
            resHeaders.add("Access-Control-Allow-Origin", "*");
            resHeaders.add("Access-Control-Allow-Headers", "*");
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).headers(resHeaders).body(jsonResponse);
        }
    }
    @RequestMapping(value = "/deleteQuestion" , method = RequestMethod.POST ,  produces = "application/json")
    public ResponseEntity deleteQuestion(@RequestBody String jsonString)
    {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject jsonResponse = new JSONObject();

        if(!jsonObject.has(COLUMN_ID))
        {
            jsonResponse.put("message" , "bad request");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse.toString());
        }
        if(questionDao.deleteQuestion(jsonObject.getInt(COLUMN_ID)))
        {
            jsonResponse.put("deleted question", jsonObject);
            return ResponseEntity.ok().body(jsonResponse.toString());
        }
        else
        {
            jsonResponse.put("message", "internal server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(jsonResponse.toString());
        }
    }
    @RequestMapping(value = "/getAllQuestions",method = RequestMethod.GET )
    public ResponseEntity getAllQuestions(@RequestBody(required = false) String jsonString)
    {
        JSONObject jsonResponse = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<Question> resultList = null;

        resultList = questionDao.getAll();
        if(resultList == null || resultList.size()==0)
        {
            jsonResponse.put("message","no question found");
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
        System.out.println(jsonArray.length()+" Questions Returned");
        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Access-Control-Allow-Methods", "POST, OPTIONS, GET");
        resHeaders.add("Access-Control-Allow-Origin", "*");
        resHeaders.add("Access-Control-Allow-Headers", "*");
        return ResponseEntity.ok().headers(resHeaders).body(jsonResponse.toString());
    }

}
