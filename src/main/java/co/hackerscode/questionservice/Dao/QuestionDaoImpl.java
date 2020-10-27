package co.hackerscode.questionservice.Dao;

import co.hackerscode.questionservice.models.Question;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static co.hackerscode.questionservice.Dao.QuestionDaoConstants.*;

@Repository
public class QuestionDaoImpl implements QuestionDao {

    @Autowired
    DataSource dataSource;
    @Override
    public List<Question> getQuestionByCategory(String category, int page) {
        String query = "select * from " + TABLE_NAME + " where category = ? limit ? offset ?;";
        Question question = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Question> questionList = new ArrayList<>();
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, category);
            ps.setInt(2, 10);
            ps.setInt(3, page * 10);
            rs = ps.executeQuery();
            while(rs.next()){
                question = new Question();

                question.setCategory(category);
                question.setTitle(rs.getString(COLUMN_TITLE));
                question.setSubcategory(rs.getString(COLUMN_SUBCATEGORY));
                question.setExampleinputurl1(rs.getString(COLUMN_EXAMPLEINPUTURL1));
                question.setExampleoutputurl1(rs.getString(COLUMN_EXAMPLEOUTPUTURL1));
                question.setExampleinputurl2(rs.getString(COLUMN_EXAMPLEINPUTURL2));
                question.setExampleoutputurl2(rs.getString(COLUMN_EXAMPLEOUTPUTURL2));
                question.setId(rs.getInt(COLUMN_ID));
                question.setQuestionurl(COLUMN_QUESTIONURL);
                question.setImageurl(rs.getString(COLUMN_IMAGEURL));
                question.setDifficulty(rs.getString(COLUMN_DIFFICULTY));
                questionList.add(question);
                System.out.println("Question found = " + question.getId());

            }
           if(questionList.size()==0) {
                System.out.println("No Question found with category=" + category);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return questionList;
    }

    @Override
    public Question getQuestionById(int id) {
        String query = "select * from " + TABLE_NAME + " where id = ? ;";
        Question question = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            if(rs.next()){
                question = new Question();

                question.setCategory(rs.getString(COLUMN_CATEGORY));
                question.setTitle(rs.getString(COLUMN_TITLE));
                question.setSubcategory(rs.getString(COLUMN_SUBCATEGORY));
                question.setExampleinputurl1(rs.getString(COLUMN_EXAMPLEINPUTURL1));
                question.setExampleoutputurl1(rs.getString(COLUMN_EXAMPLEOUTPUTURL1));
                question.setExampleinputurl2(rs.getString(COLUMN_EXAMPLEINPUTURL2));
                question.setExampleoutputurl2(rs.getString(COLUMN_EXAMPLEOUTPUTURL2));
                question.setId(id);
                question.setQuestionurl(COLUMN_QUESTIONURL);
                question.setImageurl(rs.getString(COLUMN_IMAGEURL));
                question.setDifficulty(rs.getString(COLUMN_DIFFICULTY));
                System.out.println("Question found " + question);
            }else{
                System.out.println("No Question found with id=" + id);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return question;
    }

    @Override
    public List<Question> getQuestionByCategoryAndSubCategory(String category, String subCategory, int page) {
        String query = "select * from " + TABLE_NAME + " where category = ? and subcategory = ? limit ? offset ?;";
        Question question = null;
        List<Question> questionList = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, category);
            ps.setString(2 , subCategory);
            ps.setInt(3, 10);
            ps.setInt(4, page * 10);
            rs = ps.executeQuery();
            while(rs.next()){
                question = new Question();

                question.setCategory(category);
                question.setTitle(rs.getString(COLUMN_TITLE));
                question.setSubcategory(subCategory);
                question.setExampleinputurl1(rs.getString(COLUMN_EXAMPLEINPUTURL1));
                question.setExampleoutputurl1(rs.getString(COLUMN_EXAMPLEOUTPUTURL1));
                question.setExampleinputurl2(rs.getString(COLUMN_EXAMPLEINPUTURL2));
                question.setExampleoutputurl2(rs.getString(COLUMN_EXAMPLEOUTPUTURL2));
                question.setId(rs.getInt(COLUMN_ID));
                question.setQuestionurl(COLUMN_QUESTIONURL);
                question.setImageurl(rs.getString(COLUMN_IMAGEURL));
                question.setDifficulty(rs.getString(COLUMN_DIFFICULTY));
                System.out.println("Question found " + question);
                questionList.add(question);
            }
            if(questionList.size()==0){
                System.out.println("No Question found with category and subcategory=" + category + " " + subCategory);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return questionList;
    }

    @Override
    public boolean addQuestion(Question question) {
        String query = "insert into " + TABLE_NAME+" ( title , category , subcategory , exampleinputurl1 , exampleoutputurl1 , exampleinputurl2 , exampleoutputurl2 , questionurl , imageurl , difficulty) values (?,?,?,?,?,?,?,?,?,?);";
        Connection con = null;
        PreparedStatement ps = null;
        boolean result = false;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1 , question.getTitle());
            ps.setString(2, question.getCategory());
            if(question.getSubcategory()!=null)
             ps.setString(3, question.getSubcategory());
            else
                ps.setNull(3, Types.NULL);
            ps.setString(4 , question.getExampleinputurl1());
            ps.setString(5 , question.getExampleoutputurl1());
            ps.setString(6, question.getExampleinputurl2());
            ps.setString(7 , question.getExampleoutputurl2());
            ps.setString(8 , question.getQuestionurl());
            ps.setString(10, question.getDifficulty());
            if(question.getImageurl()!=null)
                ps.setString(9 , question.getImageurl());
            else
                ps.setNull(9 , Types.NULL);
            int out = ps.executeUpdate();
            if(out !=0){
                result= true;
                System.out.println("Question saved with title " + question.getTitle());
            }else {System.out.println("Question save failed with title = "+ question.getTitle());
                result = false;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public boolean updateQuestion(JSONObject jsonObject , int id) {
        boolean result = false ;
        Connection con = null;
        PreparedStatement ps = null;
        String query = "update Questions set";

        for( String key : jsonObject.keySet())
        {
         query = query + " " + key + "=" + jsonObject.getString(key);
        }

        query = query + " " + "where id=?;";
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, id );
            int out = ps.executeUpdate();
            if(out !=0){
                System.out.println("Question updated with id = " + id);
                result = true;
            }else
            {
                result = false;
                System.out.println("Question Update failed , question id = " + id);
            }
        }catch(SQLException e){
            e.printStackTrace();
            result = false;
        }finally{
            try {
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public boolean deleteQuestion(int id) {
        boolean result= false;
        Connection con = null;
        PreparedStatement ps = null;
        String query = "delete from " + TABLE_NAME + " where id = ? ;";
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, id );
            int out = ps.executeUpdate();
            if(out !=0){
                System.out.println("Question deleted with id = " + id);
                result = true;
            }else
            {
                result = false;
                System.out.println("Question delete failed , question id = " + id);
            }
        }catch(SQLException e){
            e.printStackTrace();
            result = false;
        }finally{
            try {
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    @Override
    public List<Question> getAll() {
        String query = "select * from " + TABLE_NAME + " ;";
        Question question = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Question> questionList = new ArrayList<>();
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()){
                question = new Question();

                question.setTitle(rs.getString(COLUMN_TITLE));
                question.setSubcategory(rs.getString(COLUMN_SUBCATEGORY));
                question.setExampleinputurl1(rs.getString(COLUMN_EXAMPLEINPUTURL1));
                question.setExampleoutputurl1(rs.getString(COLUMN_EXAMPLEOUTPUTURL1));
                question.setExampleinputurl2(rs.getString(COLUMN_EXAMPLEINPUTURL2));
                question.setExampleoutputurl2(rs.getString(COLUMN_EXAMPLEOUTPUTURL2));
                question.setId(rs.getInt(COLUMN_ID));
                question.setQuestionurl(COLUMN_QUESTIONURL);
                question.setImageurl(rs.getString(COLUMN_IMAGEURL));
                question.setDifficulty(rs.getString(COLUMN_DIFFICULTY));
                questionList.add(question);
                System.out.println("Question found = " + question);

            }
            if(questionList.size()==0) {
                System.out.println("No Question found");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return questionList;
    }
}
