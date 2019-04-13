package ML;

import java.io.IOException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ImageProcess extends Thread {
    // This class links up with the servlet and will run the computer vision on a post when given an image
    // If the post is an image and it doesn't exist, set to error status
    // This should start running once a user logs in so they can avoid waiting for the model to load (15-20s wait time)
    DB_util.post p;
    private Lock processLock;
    private Condition cond;

    public ImageProcess() {
        processLock = new ReentrantLock();
        cond = processLock.newCondition();

        this.start();
    }

    public void queuePost(DB_util.post p) {

    }

    public void run() {
        // Initialize the CNN
        try {
            VG16ForNSFW vg16 = new VG16ForNSFW();


        } catch (IOException ioe) {
            System.err.println("ioe: " + ioe.getMessage());
        }
    }
}
