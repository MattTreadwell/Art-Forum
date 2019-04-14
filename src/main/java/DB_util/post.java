package DB_util;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;


public class post {
    /****the following variables are for displaying use****/
    public String Title;
    public String OwnerName = null;
    public String postContent;
    public int mPostScore;
    public Date postDate;
    public int mPostType;
    public String link;
    public int mStatus;
    public ArrayList<comment> mComments;

    // Post status final variables
    public static final int PROCESSING = 0;
    public static final int ERROR = 1;
    public static final int UNSURE = 2;
    public static final int NSFW = 3;
    public static final int SAFE = 4;
    /*******the following variables are for internal use****/
    public ObjectId _postId;
    public ObjectId ownerId;
    public ArrayList<ObjectId> mCommentIds;

    /*****for front-end Use Only******/
    // Note: if there is no link, please pass in an empty _link String.
    public post(String _Title, String _OwnerName, String _postContent, String _link,
                int postType)
    {
        mPostScore = 1;
        Title = _Title;
        OwnerName = _OwnerName;
        _postId = null;
        ownerId = null;
        mCommentIds = null;
        mComments = null;
        mStatus = 0;
        postContent = _postContent;
        if (_link.trim().isEmpty())
        {
            link = null;
        }
        else
        {
            link = _link;
        }
        mPostType = postType;

    }
    /******for internal use***************/
    public post()
    {
        mComments = new ArrayList<comment>();
        mCommentIds = new ArrayList<ObjectId>();
        _postId = null;
        ownerId = null;
    }

}
