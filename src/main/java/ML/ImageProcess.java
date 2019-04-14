package ML;

import DB_util.post;

import java.io.IOException;
import java.util.ArrayList;

import static DB_util.Database.IMAGE;

public class ImageProcess {
    // This class links up with the servlet and will run the computer vision on a post when given an image
    // If the post is an image and it doesn't exist, set to error status
    // This should start running once a user logs in so they can avoid waiting for the model to load (15-20s wait time)
    private ML.VG16ForNSFW vg16;

    private ArrayList<ProcessQueue> pqs;

    // Select number of threads (each thread is a process queue)
    // Just use the amount of threads/cores on the cpu
    private static final int J = Runtime.getRuntime().availableProcessors();


    public ImageProcess(String modelPath) {
        // Initialize the CNN
        System.out.println("Created new ImageProcess");
        System.out.println("Attempting to build/load VGG16 Neural Net");
        try {
            this.vg16 = new VG16ForNSFW(modelPath);
            System.out.println("Finish build/load VGG16 Neural Net");
            System.out.println("Start loading ProcessQueues");
            pqs = new ArrayList<ProcessQueue>();
            for(int i = 0; i < J; i++) {
                pqs.add(new ProcessQueue(vg16));
                //pqs.get(i).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Finish build/load VGG16 Neural Net");
        System.out.println("Start loading ProcessQueues");
        pqs = new ArrayList<ProcessQueue>();
        for(int i = 0; i < J; i++) {
            pqs.add(new ProcessQueue(vg16));
        }
    }

    public ImageProcess() {
        // Initialize the CNN
        System.out.println("Created new ImageProcess");
        System.out.println("Attempting to build/load VGG16 Neural Net");
        try {
            this.vg16 = new VG16ForNSFW();
            System.out.println("Finish build/load VGG16 Neural Net");
            System.out.println("Start loading ProcessQueues");
            pqs = new ArrayList<ProcessQueue>();
            for(int i = 0; i < J; i++) {
                pqs.add(new ProcessQueue(vg16));
                //pqs.get(i).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Finish build/load VGG16 Neural Net");
        System.out.println("Start loading ProcessQueues");
        pqs = new ArrayList<ProcessQueue>();
        for(int i = 0; i < J; i++) {
            pqs.add(new ProcessQueue(vg16));
        }
    }

    public synchronized void queuePost(DB_util.post p) {
        System.out.println("ImageProcess adding post to least busy processQueue");

        int size = pqs.get(0).queueSize();
        ProcessQueue best = pqs.get(0);
        for(ProcessQueue pq : pqs) {
            if(pq.queueSize() < size) {
                size = pq.queueSize();
                best = pq;
            }
        }

        System.out.println("ImageProcess adding post to processQueue of size " + size);
        best.addPostToQueue(p);
        best.WakeUpProcess();
    }

    // Driver code
    public static void main(String[] args) {
        ImageProcess ip = new ImageProcess();
        post p4 = new post("test image...12","Lisa","dummy content","https://i.imgur.com/0lkxCY2.jpg",IMAGE);
        ip.queuePost(p4);

        System.out.println(Runtime.getRuntime().availableProcessors());
    }


}
