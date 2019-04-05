package DB_util;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;

import static com.mongodb.client.model.Filters.eq;


public class Database {
    // for DEBUG use ONLY:
    private static ObjectId lastPost = null;
    // success/errorMsg constants
    public static final String Success = "Success!";
    public static final String Register_unameTaken = "The user name has been taken. Try an new one.";
    public static final String Signin_nouname = "The username doesn't exist";
    public static final String Signin_pwerror = "Password is not correct.";
    public static final int TEXT = 1;
    public static final int LINK = 2;
    public static final int IMAGE = 3;



    // connection required variables
    public MongoClientURI uri;
    public MongoClient mongoClient;

    public MongoDatabase database;
    public Database()
    {
        uri = new MongoClientURI(
                "mongodb+srv://csci201project:csci201project@cluster0-tprgw.mongodb.net/CSCI201?retryWrites=true");
        mongoClient = new MongoClient(uri);
        database = mongoClient.getDatabase("CSCI201");
    }

    public String Validate(String Username, int Password)
    {
        MongoCollection collection = database.getCollection("users");
        FindIterable<Document> findIterable = collection.find(eq("Username", Username));
        ArrayList<Document> checker = new ArrayList<Document>();
        for (Document doc : findIterable)
        {
            checker.add(doc);
        }
        if (checker.size() == 0)
        {
            return Signin_nouname;
        }
        Document ve = checker.get(0);
        int correctPass = ve.getInteger("Password");
        if (correctPass != Password)
        {
            return Signin_pwerror;
        }
        return Success;
    }
    public String addUser(user u)
    {
        MongoCollection collection = database.getCollection("users");
        String uname = u.username;
        FindIterable<Document> findIterable = collection.find(eq("Username", uname));
        ArrayList<Document> checker = new ArrayList<Document>();
        for (Document doc : findIterable)
        {
            checker.add(doc);
        }
        if (checker.size() != 0)
        {
            return Register_unameTaken;
        }
        Document mUser = new Document("Username",u.username);
        mUser.append("IsBanned",false);
        mUser.append("Password", u.password);
        ArrayList<ObjectId> postIdList = u.mPostIds;
        ArrayList<ObjectId> commentIdList = new ArrayList<ObjectId>();
        mUser.append("PostIDs",postIdList);
        mUser.append("CommentIDs",commentIdList);
        Document uProfile = new Document("Country",u.profile.country);
        uProfile.append("Age",u.profile.age);
        uProfile.append("RegisterDate",u.profile.registerDate);
        uProfile.append("PostScore",u.profile.postScore);
        mUser.append("Profile",uProfile);
        collection.insertOne(mUser);
        return Success;
    }
    public void addPost(post p)
    {
        MongoCollection userCollection = database.getCollection("users");
        MongoCollection postCollection = database.getCollection("posts");

        //initialize and then push this post into posts Collection.
        Document newPost = new Document();

        Document mappedUser = (Document)userCollection.find(eq("Username", p.OwnerName)).first();
        ObjectId thisuserId = mappedUser.getObjectId("_id");
        Date pdate = new Date();
        newPost.append("PostDate",pdate);
        Document postBody = new Document();
        postBody.append("Title",p.Title);
        postBody.append("UserId",thisuserId);
        postBody.append("PostContent",p.postContent);
        postBody.append("Link",p.link);
        postBody.append("PostType",p.mPostType);

        newPost.append("PostBody",postBody);

        ArrayList<comment> mComments = new ArrayList<comment>();
        newPost.append("CommentIDs",mComments);
        postCollection.insertOne(newPost);

        //now, add this post into owning user's post history.
        ObjectId thisPostId = (ObjectId)newPost.get( "_id" );
        System.out.println(thisPostId);
        Document listItem = new Document("PostIDs", thisPostId);
        Document updateQuery = new Document("$push",listItem);
        Document findQuery = new Document("Username",p.OwnerName);
        userCollection.updateOne(findQuery,updateQuery);

        //DEBUG:
        lastPost = thisPostId;
    }
    public post getPostById(ObjectId mPostId)
    {
        // first ,get all the needed collections/document.
        MongoCollection userCollection = database.getCollection("users");
        MongoCollection postCollection = database.getCollection("posts");
        MongoCollection commentCollection = database.getCollection("comments");
        Document Post = (Document)postCollection.find(eq("_id",mPostId)).first();
        Document PostBody = (Document)Post.get("PostBody");
        ObjectId PostUserId = PostBody.getObjectId("UserId");
        Document PostUser = (Document)userCollection.find(eq("_id",PostUserId)).first();
        // now, begin retrieving data.
        post np = new post();
        np.Title = PostBody.getString("Title");
        np.postDate = Post.getDate("PostDate");
        np._postId = Post.getObjectId("_id");
        np.ownerId = PostBody.getObjectId("UserId");
        np.OwnerName = PostUser.getString("Username");
        np.postContent = PostBody.getString("PostContent");
        np.link = PostBody.getString("Link");
        np.mPostType = PostBody.getInteger("PostType");
        //now, begin handling comments.
        np.mCommentIds = (ArrayList<ObjectId>)Post.get("CommentIDs");
        for( int i=0; i<np.mCommentIds.size(); i++)
        {
            Document Comment = (Document)commentCollection.find(eq("_id",np.mCommentIds.get(i))).first();
            comment nc = new comment();
            nc.commentContent = Comment.getString("CommentContent");
            nc._commentId = Comment.getObjectId("_id");
            nc._userId = Comment.getObjectId("UserId");
            Document cUser = (Document)userCollection.find(eq("_id",nc._userId)).first();
            nc.uname = cUser.getString("Username");
            nc.postId = Comment.getObjectId("PostId");
            nc.commentDate = Comment.getDate("CommentDate");
            np.mComments.add(nc);
        }
        return np;

    }
    public user getUser(String uname)
    {
        MongoCollection userCollection = database.getCollection("users");
        MongoCollection postCollection = database.getCollection("posts");
        MongoCollection commentCollection = database.getCollection("comments");

        Document User = (Document)userCollection.find(eq("Username",uname)).first();
        user u = new user();
        // first, fill out the basic information excluding arrays;
        u.username = uname;
        u.password = User.getInteger("Password");
        System.out.println(u.password);
        u.profile = new userProfile();
        Document UserProfile = (Document)User.get("Profile");
        u.profile.country = UserProfile.getString("Country");
        System.out.println(u.profile.country);
        u.profile.age = UserProfile.getInteger("Age");
        System.out.println(u.profile.age);
        u.profile.postScore = UserProfile.getInteger("PostScore");
        u.profile.registerDate = UserProfile.getDate("RegisterDate");
        u.IsBanned = User.getBoolean("IsBanned");
        u.mPostIds = (ArrayList<ObjectId>)User.get("PostIDs");
        u.mCommentIds = (ArrayList<ObjectId>)User.get("CommentIDs");
        u.userPosts = new ArrayList<post>();
        u.userComments = new ArrayList<comment>();
        /*
            public String Title;
            public String OwnerName = null;
            public String postContent;
            public Date postDate;
            boolean mLinkImage;
            public String link;
         */
        for (int i=0; i<u.mCommentIds.size(); i++)
        {
            Document Comment = (Document)commentCollection.find(eq("_id",u.mCommentIds.get(i))).first();
            comment nc = new comment();
            nc.commentContent = Comment.getString("CommentContent");
            nc.uname = uname;
            nc._commentId = Comment.getObjectId("_id");
            nc._userId = Comment.getObjectId("UserId");
            nc.postId = Comment.getObjectId("PostId");
            nc.commentDate = Comment.getDate("CommentDate");
            u.userComments.add(nc);
        }
        for (int i=0; i<u.mPostIds.size(); i++)
        {
            Document Post = (Document)postCollection.find(eq("_id",u.mPostIds.get(i))).first();
            Document PostBody = (Document)Post.get("PostBody");
            post np = new post();
            np.Title = PostBody.getString("Title");
            np.postDate = Post.getDate("PostDate");
            np._postId = Post.getObjectId("_id");
            np.ownerId = PostBody.getObjectId("UserId");
            np.OwnerName = uname;
            np.postContent = PostBody.getString("PostContent");
            np.link = PostBody.getString("Link");
            np.mPostType = PostBody.getInteger("PostType");
            u.userPosts.add(np);
        }

        return u;



    }

    public void addComment(comment cm)
    {
        /****retrieve collections from MongoDB******/
        MongoCollection userCollection = database.getCollection("users");
        MongoCollection postCollection = database.getCollection("posts");
        MongoCollection commentCollection = database.getCollection("comments");

        Document mappedUser = (Document)userCollection.find(eq("Username", cm.uname)).first();
        Document newComment = new Document();
        newComment.append("PostId",cm.postId);
        newComment.append("UserId",mappedUser.getObjectId("_id"));
        newComment.append("CommentContent",cm.commentContent);
        newComment.append("CommentDate", new Date());
        commentCollection.insertOne(newComment);
        ObjectId mId = newComment.getObjectId("_id");
        //now, add this comment to user's profile.

        Document listItem = new Document("CommentIDs", mId);
        Document updateQuery = new Document("$push",listItem);
        Document findQuery = new Document("Username",cm.uname);
        userCollection.updateOne(findQuery,updateQuery);
        // now, add this comment to post's comment array.
        Document listItem2 = new Document("CommentIDs", mId);
        Document updateQuery2 = new Document("$push",listItem2);
        Document findQuery2 = new Document("_id",cm.postId);
        postCollection.updateOne(findQuery2,updateQuery2);


    }
    public void deleteCommentById(ObjectId commentId)
    {
        //prerequisite: get all collections.
        MongoCollection userCollection = database.getCollection("users");
        MongoCollection postCollection = database.getCollection("posts");
        MongoCollection commentCollection = database.getCollection("comments");

        Document comment = (Document)commentCollection.find(eq("_id", commentId)).first();

        ObjectId userId = comment.getObjectId("UserId");
        ObjectId postId = comment.getObjectId("PostId");
        // now, pull this comment from user's comment data.

        Document findQuery1 = new Document("_id",userId);
        Document updateQuery1 = new Document ("$pull",new Document("CommentIDs",commentId));
        userCollection.updateOne(findQuery1,updateQuery1);

        // then, pull this comment from post's comment data.

        Document findQuery2 = new Document("_id",postId);
        Document updateQuery2 = new Document("$pull", new Document("CommentIDs",commentId));
        postCollection.updateOne(findQuery2,updateQuery2);

        Document deleteQuery = new Document("_id",commentId);
        commentCollection.deleteOne(deleteQuery);

    }
    public void deletePostById(ObjectId postId)
    {
        MongoCollection userCollection = database.getCollection("users");
        MongoCollection postCollection = database.getCollection("posts");
        MongoCollection commentCollection = database.getCollection("comments");

        Document thisPost = (Document)postCollection.find(eq("_id", postId)).first();
        ArrayList<ObjectId> CommIds = (ArrayList<ObjectId>)thisPost.get("CommentIDs");
        for (int i=0; i<CommIds.size(); i++)
        {
            deleteCommentById(CommIds.get(i));
        }
        Document PostBody = (Document)thisPost.get("PostBody");
        ObjectId userId = PostBody.getObjectId("UserId");
        // now, pull this post from user's PostId array.
        Document findQuery1 = new Document("_id",userId);
        Document updateQuery1 = new Document ("$pull",new Document("PostIDs", postId));
        userCollection.updateOne(findQuery1,updateQuery1);

        Document deleteQuery2 = new Document("_id",postId);
        postCollection.deleteOne(deleteQuery2);

    }
    public void changeUserCountry(String username, String newCountry)
    {
        MongoCollection userCollection = database.getCollection("users");
        Document findQuery = new Document("Username",username);
        Document updateQuery = new Document("$set", new Document("Profile.Country", newCountry));
        userCollection.updateOne(findQuery,updateQuery);
    }
    public void changeUserBanState(String username, boolean newBanState)
    {
        MongoCollection userCollection = database.getCollection("users");
        Document findQuery = new Document("Username",username);
        Document updateQuery = new Document("$set", new Document("IsBanned", newBanState));
        userCollection.updateOne(findQuery,updateQuery);
    }

    public void changeUserPassword(String username, int newPassWord)
    {
        MongoCollection userCollection = database.getCollection("users");
        Document findQuery = new Document("Username",username);
        Document updateQuery = new Document("$set", new Document("Password", newPassWord));
        userCollection.updateOne(findQuery,updateQuery);
    }
    public void changeUserAge(String username, int newAge)
    {
        MongoCollection userCollection = database.getCollection("users");
        Document findQuery = new Document("Username",username);
        Document updateQuery = new Document("$set", new Document("Profile.Age", newAge));
        userCollection.updateOne(findQuery,updateQuery);
    }
    public void changeUserScore(String username, int newScore)
    {
        MongoCollection userCollection = database.getCollection("users");
        Document findQuery = new Document("Username",username);
        Document updateQuery = new Document("$set", new Document("Profile.PostScore", newScore));
        userCollection.updateOne(findQuery,updateQuery);
    }
    public void changePostById(ObjectId postId, String newContent)
    {
        MongoCollection postCollection = database.getCollection("posts");
        Document findQuery = new Document("_id",postId);
        Document updateQuery = new Document("$set", new Document("PostBody.PostContent", newContent));
        postCollection.updateOne(findQuery,updateQuery);
    }
    public void updateUserScore(String username, int amount)
    {
        MongoCollection userCollection = database.getCollection("users");
        Document findQuery = new Document("Username",username);
        Document updateQuery = new Document("$inc", new Document("Profile.PostScore",amount ));
        userCollection.updateOne(findQuery,updateQuery);
    }
    public ArrayList<post> getPostChunk(int postIndex)
    {

        int displayNum = 20;
        int postSize = getPostSize();
        int lastEndPlace = (postIndex-1)*displayNum;
        if (lastEndPlace >= postSize)
        {
            return null;
        }
        MongoCollection postCollection = database.getCollection("posts");
        Document SortingDoc = new Document("_id",1);
        FindIterable<Document> findIterable = (FindIterable<Document>)postCollection.find().sort(SortingDoc)
                .skip(lastEndPlace).limit(displayNum);
        ArrayList<post> PostChunk = new ArrayList<post>();
        for (Document doc : findIterable)
        {
            ObjectId mId= doc.getObjectId("_id");
            post p = getPostById(mId);
            PostChunk.add(p);
        }

        return PostChunk;
    }
    public int getPostSize()
    {
        Document stats = database.runCommand(new Document("collStats", "posts"));
        return stats.getInteger("count");
    }

    // Driver/Test code
    public static void main(String [] args)
    {
        Database db = new Database();
        System.out.println("Hello, World!");


        user u = new user("Lisa",123456,"China",21,0);
        System.out.println(db.addUser(u));
        System.out.println(db.addUser(u));

        post p = new post("CS201 FP" ,"Lisa","This is my first post for CSCI 201 Project"
        , "", TEXT);
        db.addPost(p);

        comment c = new comment("ve...", "Lisa" , lastPost);
        db.addComment(c);
        comment d = new comment("va...", "Lisa", lastPost);
        db.addComment(d);

        user u1 = db.getUser("Lisa");
        int a = 2;

        post np = db.getPostById(lastPost);
        int b = 3;
        System.out.println("post size is:"+db.getPostSize());

        ArrayList<post> Chunk = db.getPostChunk(1);
        int c1 = 4;

        db.changeUserPassword("Lisa", 999000);
        int c2 = 5;
        db.changeUserAge("Lisa",19);
       // db.updateUserScore("Lisa",100);
        db.changeUserScore("Lisa",101);
        db.changeUserCountry("Lisa","United States");
        db.changePostById(lastPost,"JEEEEEEEEE");
        db.changeUserBanState("Lisa",true);

        db.getPostChunk(1);
      //  db.deletePostById(lastPost);

        System.out.println("Testing Validation:"+db.Validate("Lisas",999000));



    }


}
