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
import static com.mongodb.client.model.Filters.regex;



public class Database {
    // for DEBUG use ONLY:
    private static ObjectId lastPost = null;
    // success/errorMsg constants
    public static final String Success = "Success!";
    public static final String Register_unameTaken = "The user name has been taken. Try a new one.";
    public static final String Signin_nouname = "The username doesn't exist";
    public static final String Signin_pwerror = "Password is not correct.";
    public static final int TEXT = 1;
    public static final int LINK = 2;
    public static final int IMAGE = 3;
    public static final int displayNum = 8;


    // connection required variables
    public MongoClientURI uri;
    public MongoClient mongoClient;

    public MongoDatabase database;
    public MongoCollection userCollection;
    public MongoCollection postCollection;
    public MongoCollection commentCollection;
    public Database()
    {

        uri = new MongoClientURI(
                "mongodb://localhost:27017/CSCI201");
        mongoClient = new MongoClient(uri);
        database = mongoClient.getDatabase("CSCI201");

        userCollection = database.getCollection("users");
        postCollection = database.getCollection("posts");
        commentCollection = database.getCollection("comments");
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

        String uname = u.username;
        FindIterable<Document> findIterable = userCollection.find(eq("Username", uname));
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
        userCollection.insertOne(mUser);
        return Success;
    }
    public void addPost(post p)
    {

        //initialize and then push this post into posts Collection.
        Document newPost = new Document();

        Document mappedUser = (Document)userCollection.find(eq("Username", p.OwnerName)).first();
        ObjectId thisuserId = mappedUser.getObjectId("_id");
        Date pdate = new Date();
        newPost.append("PostDate",pdate);
        newPost.append("PostScore",1);
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
    public ArrayList<comment> getUserComment(String uname)
    {
        Document User;
        try {
            User = (Document) userCollection.find(eq("Username", uname)).first();
        } catch (Exception e)
        {
            return null;
        }

        ArrayList<ObjectId> CommentIds = (ArrayList<ObjectId>)User.get("CommentIDs");
        ArrayList<comment> mComment = new ArrayList<comment>();
        for(ObjectId id : CommentIds)
        {
            Document Comment = (Document)commentCollection.find(eq("_id",id)).first();
            comment nc = new comment();
            nc.commentContent = Comment.getString("CommentContent");
            nc.uname = uname;
            nc._commentId = Comment.getObjectId("_id");
            nc._userId = Comment.getObjectId("UserId");
            nc.postId = Comment.getObjectId("PostId");
            nc.commentDate = Comment.getDate("CommentDate");
            mComment.add(nc);
        }

        return mComment;

    }
    public ArrayList<String> getImageLinks(String uname, String link)
    {
        Document User = (Document) userCollection.find(eq("Username", uname)).first();
        ArrayList<ObjectId> PostIDs = (ArrayList<ObjectId>)User.get("PostIDs");
        System.out.println("size of post id array: "+PostIDs.size());
        ArrayList<String> ImageLinks = new ArrayList<String>();
        for (ObjectId id : PostIDs)
        {
            Document Post = (Document)postCollection.find(eq("_id",id)).first();
            Document PostBody = (Document)Post.get("PostBody");
            int PostType = PostBody.getInteger("PostType");
            if (PostType != IMAGE)
            {
                continue;
            }
            String imageLink = PostBody.getString("Link");
            if (imageLink.contentEquals(link)){continue;}
            ImageLinks.add(imageLink);

        }
        return ImageLinks;

    }
    public user getUser(String uname)
    {

        Document User;
        try {
            User = (Document) userCollection.find(eq("Username", uname)).first();
        } catch (Exception e)
        {
            return null;
        }
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
            np.mPostScore = Post.getInteger("PostScore");
            u.userPosts.add(np);
        }

        return u;



    }




    public void addComment(comment cm)
    {
        /****retrieve collections from MongoDB******/
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
        Document findQuery = new Document("Username",username);
        Document updateQuery = new Document("$set", new Document("Profile.Country", newCountry));
        userCollection.updateOne(findQuery,updateQuery);
    }
    public void changeUserBanState(String username, boolean newBanState)
    {
        Document findQuery = new Document("Username",username);
        Document updateQuery = new Document("$set", new Document("IsBanned", newBanState));
        userCollection.updateOne(findQuery,updateQuery);
    }

    public void changeUserPassword(String username, int newPassWord)
    {
        Document findQuery = new Document("Username",username);
        Document updateQuery = new Document("$set", new Document("Password", newPassWord));
        userCollection.updateOne(findQuery,updateQuery);
    }
    public void changeUserAge(String username, int newAge)
    {
        Document findQuery = new Document("Username",username);
        Document updateQuery = new Document("$set", new Document("Profile.Age", newAge));
        userCollection.updateOne(findQuery,updateQuery);
    }
    public void changeUserScore(String username, int newScore)
    {
        Document findQuery = new Document("Username",username);
        Document updateQuery = new Document("$set", new Document("Profile.PostScore", newScore));
        userCollection.updateOne(findQuery,updateQuery);
    }
    public void IncPostScore(ObjectId postId, int amount)
    {
        Document findQuery = new Document("_id",postId);
        Document updateQuery = new Document("$inc", new Document("PostScore", amount));
        postCollection.updateOne(findQuery,updateQuery);
    }
    public void changePostById(ObjectId postId, String newContent)
    {
        Document findQuery = new Document("_id",postId);
        Document updateQuery = new Document("$set", new Document("PostBody.PostContent", newContent));
        postCollection.updateOne(findQuery,updateQuery);
    }
    public post getLatestPost()
    {
        Document SortingDoc = new Document("_id",-1);
        FindIterable<Document> findIterable = (FindIterable<Document>)postCollection.find().sort(SortingDoc)
                .limit(1);
        ArrayList<post> onlyOne = new ArrayList<post>();
        for (Document doc : findIterable)
        {
            ObjectId mId= doc.getObjectId("_id");
            post p = getPostById(mId);
            onlyOne.add(p);
        }

        return onlyOne.get(0);
    }

    public ArrayList<post> searchPost(String searchString)
    {
        Document regexQuery = new Document();
        regexQuery.append("$regex", searchString);
        regexQuery.append("$options","i");
        Document nameQuery1 = new Document().append("PostBody.Title",regexQuery);
        Document nameQuery2 = new Document().append("PostBody.PostContent",regexQuery);
        ArrayList<Document> allQueries = new ArrayList<Document>();
        allQueries.add(nameQuery1);
        allQueries.add(nameQuery2);
        Document findQuery = new Document("$or",allQueries);
        FindIterable<Document> findIterable = (FindIterable<Document>)postCollection.find(findQuery).limit(8);
        ArrayList<post> MatchedPost = new ArrayList<post>();
        for (Document Post : findIterable)
        {
            Document PostBody = (Document)Post.get("PostBody");
            ObjectId mId= Post.getObjectId("_id");
            ObjectId PostUserId = PostBody.getObjectId("UserId");
            Document PostUser = (Document)userCollection.find(eq("_id",PostUserId)).first();
            post np = new post();

            np.Title = PostBody.getString("Title");
            np.postDate = Post.getDate("PostDate");
            np._postId = Post.getObjectId("_id");
            np.ownerId = PostBody.getObjectId("UserId");

            np.OwnerName = PostUser.getString("Username");
            np.postContent = PostBody.getString("PostContent");
            np.link = PostBody.getString("Link");
            np.mPostType = PostBody.getInteger("PostType");
            np.mPostScore = Post.getInteger("PostScore");
            np.mCommentIds = (ArrayList<ObjectId>)Post.get("CommentIDs");
            MatchedPost.add(np);
        }

        return MatchedPost;
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
        int postSize = getPostSize();
        int lastEndPlace = (postIndex-1)*displayNum;
        if (lastEndPlace >= postSize)
        {
            return null;
        }
        MongoCollection postCollection = database.getCollection("posts");
        Document SortingDoc = new Document("PostDate",-1);
        FindIterable<Document> findIterable = (FindIterable<Document>)postCollection.find().sort(SortingDoc)
                .skip(lastEndPlace).limit(displayNum);
        ArrayList<post> PostChunk = new ArrayList<post>();
        for (Document Post : findIterable)
        {
            Document PostBody = (Document)Post.get("PostBody");
            ObjectId mId= Post.getObjectId("_id");
            ObjectId PostUserId = PostBody.getObjectId("UserId");
            Document PostUser = (Document)userCollection.find(eq("_id",PostUserId)).first();
            post np = new post();

            np.Title = PostBody.getString("Title");
            np.postDate = Post.getDate("PostDate");
            np._postId = Post.getObjectId("_id");
            np.ownerId = PostBody.getObjectId("UserId");

            np.OwnerName = PostUser.getString("Username");
            np.postContent = PostBody.getString("PostContent");
            np.link = PostBody.getString("Link");
            np.mPostType = PostBody.getInteger("PostType");
            np.mPostScore = Post.getInteger("PostScore");
            np.mCommentIds = (ArrayList<ObjectId>)Post.get("CommentIDs");
            PostChunk.add(np);
        }

        return PostChunk;
    }
    public ArrayList<comment> getPostComments(ObjectId mPostId)
    {
        Document Post = (Document)postCollection.find(eq("_id",mPostId)).first();
        ArrayList<ObjectId> mCommentIds = (ArrayList<ObjectId>)Post.get("CommentIDs");
        ArrayList<comment> ans = new ArrayList<comment>();
        for( int i=0; i<mCommentIds.size(); i++)
        {
            Document Comment = (Document)commentCollection.find(eq("_id",mCommentIds.get(i))).first();
            comment nc = new comment();
            nc.commentContent = Comment.getString("CommentContent");
            nc._commentId = Comment.getObjectId("_id");
            nc._userId = Comment.getObjectId("UserId");
            Document cUser = (Document)userCollection.find(eq("_id",nc._userId)).first();
            nc.uname = cUser.getString("Username");
            nc.postId = Comment.getObjectId("PostId");
            nc.commentDate = Comment.getDate("CommentDate");
            ans.add(nc);
        }

        return ans;
    }
    public post getPostById(ObjectId mPostId)
    {
        // first ,get all the needed collections/document.
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
        np.mPostScore = Post.getInteger("PostScore");
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
/*
        post p1 = new post("test image...9","Lisa","dummy content","www.google.com",IMAGE);
        post p2 = new post("test image...10","Lisa","dummy content","www.google.com",IMAGE);
        post p3 = new post("test image...11","Lisa","dummy content","www.google.com",IMAGE);
        post p4 = new post("test image...12","Lisa","dummy content","bytes.usc.edu",IMAGE);

        db.addPost(p1);
        db.addPost(p2);
        db.addPost(p3);
        db.addPost(p4);
*/
       // ArrayList<String> TESTPOSTCHUNK = db.getPostChunk(1);

      //  int va = 1;




        for (int i = 1; i<78; i++)
        {
            String Title = "CSCI201 HW"+Integer.toString(i);
            String OwnerName = "Lisa";
            String postContent = "The following post is the "+Integer.toString(i)+"th post";
            post p = new post(Title,OwnerName,postContent,"",TEXT);
            db.addPost(p);
            comment c = new comment("ve..."+Integer.toString(i), "Lisa" , lastPost);
            db.addComment(c);
            comment d = new comment("va..."+Integer.toString(i), "Lisa", lastPost);
            db.addComment(d);
        }



        ArrayList<post> pp = db.getPostChunk(1);
        int ve = 1;

/*
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

        p = db.getLatestPost();
        b = 5;
*/
        /*
        ArrayList<post> testMatch = db.searchPost("HW");
        //b = 6;
        ArrayList<comment> LisaComment = db.getUserComment("Lisa");
        post lp = db.getLatestPost();
        System.out.println("Post score before updating: "+lp.mPostScore);
        db.IncPostScore(lp._postId,2);
        lp = db.getLatestPost();
        System.out.println("Post score after updating: "+lp.mPostScore);

        ArrayList<comment> LatestPostComment = db.getPostComments(lastPost);

        int cdx = 9;
        user LatestUser = db.getUser("Lisa");
        //Checking for ObjectId:
        String s = lastPost.toString();
        ObjectId Oid = new ObjectId(s);

        post p2 = db.getPostById(Oid);


        cdx = 10;
*/

    }


}
