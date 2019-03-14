package DB_util;
import com.mongodb.*;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;




public class user {
    private ObjectId _userId;
    public String username;
    public int password;
    public userProfile profile;
    public ArrayList<ObjectId> mCommentIds;
    public ArrayList<ObjectId> mPostIds;
    public ArrayList<post> userPosts;
    public ArrayList<comment> userComments;
    /********for front-end use********************/
    public user(String _username, int _password,
                String _country, int _age, int _postscore)
    {
        userComments = null;
        userPosts = null;
        _userId = null;
        username = _username;
        password = _password;
        Date _registerDate = new Date();
        profile = new userProfile(_country,_age,_registerDate,_postscore);
        mPostIds = new ArrayList<ObjectId>();
    }
    public user()
    {

    }
    /*********for internal use*******************/
    /*
    public user (ObjectId uId, String _username, int _password, ArrayList<ObjectId> PostIds,
                 String _country, int _age, Date _registerdate, int _postscore)
    {
        _userId = uId;
        username = _username;
        password = _password;
        mPostIds = PostIds;
        profile = new userProfile(_country,_age,_registerdate,_postscore);
    }
    */
}
