package DB_util;

import com.mongodb.CommandResult;
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
    public static final String Signin_pwerrorr = "Password is not correct.";

    // connection required variables
    public MongoClientURI uri;
    public MongoClient mongoClient;

    public MongoDatabase database;
    public Database()
    {
        uri = new MongoClientURI(
                "mongodb+srv://csci201project:csci201project@cluster0-tprgw.mongodb.net/dummy?retryWrites=true");
        mongoClient = new MongoClient(uri);
        database = mongoClient.getDatabase("CSCI201");
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
        postBody.append("IsImage",p.mLinkImage);

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
        np.mLinkImage = PostBody.getBoolean("IsImage");
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
            np.mLinkImage = PostBody.getBoolean("IsImage");
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
        FindIterable<Document> findIterable = (FindIterable<Document>)postCollection.find().skip(lastEndPlace).limit(displayNum);
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

    public static void main(String [] args)
    {
        Database db = new Database();
        System.out.println("Hello, World!");

        user u = new user("Lisa",123456,"China",21,0);
        System.out.println(db.addUser(u));
        System.out.println(db.addUser(u));

        post p = new post("CS201 FP" ,"Lisa","This is my first post for CSCI 201 Project"
        , "www.google.com", false);
        db.addPost(p);
        post p1 = new post("CS104 Scrabble" ,"Lisa","Scrabble takes me 40 hours to finish"
                , "www.google.com", false);
        db.addPost(p1);
        comment c = new comment("ve...", "Lisa" , lastPost);
        db.addComment(c);
        comment d = new comment("va...", "Lisa", lastPost);
        db.addComment(d);

        user u1 = db.getUser("Lisa");
        int a = 2;

        post np = db.getPostById(lastPost);
        int b = 3;
        System.out.println("post size is:"+db.getPostSize());

        ArrayList<post> Chunk = db.getPostChunk(0);
        int c1 = 4;

    }


}
