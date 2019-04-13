package DB_util;

import org.bson.types.ObjectId;


import java.util.Date;


public class comment {
    /*** the following member variables are for database internal use:***/
    public ObjectId _commentId;
    public ObjectId _userId;
    public ObjectId postId;
    /*** the following member variables are for displaying use:***/
    public String uname;
    public String commentContent;
    public Date commentDate;
    /*******Constructor for Front-end use**********/
    public comment(String _commentContent, String _uname, ObjectId _postId)
    {
        commentContent = _commentContent;
        uname = _uname;
        postId = _postId;
        _userId = null;
        _commentId = null;
        commentDate = null;

    }
    /**** Constructor for internal use****/
    public comment(String _commentContent, ObjectId cId, ObjectId uId, Date _date)
    {
        commentContent = _commentContent;
        _commentId = cId;
        _userId = uId;
        commentDate = _date;

    }
    /*****for internal use*********/
    public comment()
    {

    }
}
