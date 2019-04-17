package ML;

import DB_util.Database;
import DB_util.post;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProcessQueue extends Thread {
    private VG16ForNSFW vg16;
    private Lock processLock;
    private Condition cond;
    private Vector<post> postQueue;

    public static final double THRESHOLD = 0.80;

    public ProcessQueue(VG16ForNSFW vg16) {
        this.vg16 = vg16;
        postQueue = new Vector<post>();
        System.out.println("Created new ProcessQueue");
        Database db = new Database();

        processLock = new ReentrantLock();
        cond = processLock.newCondition();
        this.start();
    }

    public void addPostToQueue(post p) {
        postQueue.add(p);
    }

    public int queueSize() {
        return postQueue.size();
    }

    public void WakeUpProcess() {
        try {
            processLock.lock();
            cond.signal();
        } finally {
            processLock.unlock();
        }
    }

    public void run() {
        System.out.println("Run thread");
        while(true) {
            while(!postQueue.isEmpty()) {
                post p = postQueue.remove(0);

                // Update database with status
                Database db = new Database();
                System.out.println("Running Computer Vision on post: " + p.Title);
                try{
                    switch (vg16.detectNSFW(p.link, THRESHOLD)) {
                        case NSFW:
                            System.out.println("Post determined as NSFW");
                            db.changePostStatus(p._postId, post.NSFW);
                            break;
                        case SAFE:
                            System.out.println("Post determined as SAFE");
                            db.changePostStatus(p._postId, post.SAFE);
                            break;
                        case NOT_KNOWN:
                            System.out.println("Post determined as UNSURE");
                            db.changePostStatus(p._postId, post.UNSURE);
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Finish running Computer Vision on post: " + p.Title);
            }
            try {
                processLock.lock();
                System.out.println("ProcessQueue sleeping");
                cond.await();
                System.out.println("ProcessQueue woke up");
            } catch (InterruptedException ie) {
                System.out.println("ie while sleeping: " + ie.getMessage());
            } finally {
                processLock.unlock();
            }
        }
    }
}
