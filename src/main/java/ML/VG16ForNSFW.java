package ML;

import org.datavec.image.loader.NativeImageLoader;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.VGG16ImagePreProcessor;
import org.slf4j.Logger;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class VG16ForNSFW {
    // Implementation of a VGG16 CNN for NSFW image recognition
    // Originally from https://github.com/klevis/CatAndDogRecognizer
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(TrainImageNetVG16.class);
    private static final String TRAINED_PATH_MODEL = TrainImageNetVG16.DATA_PATH + "/model.zip";
    private static ComputationGraph computationGraph;

    public VG16ForNSFW() throws IOException {
        computationGraph = loadModel();
    }

    public ImageType detectNSFW(String link, Double threshold) throws IOException {
        URL url = new URL(link);

        computationGraph.init();
        log.info(computationGraph.summary());
        NativeImageLoader loader = new NativeImageLoader(224, 224, 3);
        INDArray image = loader.asMatrix(url.openConnection().getInputStream());
        DataNormalization scaler = new VGG16ImagePreProcessor();
        scaler.transform(image);
        INDArray output = computationGraph.outputSingle(false, image);
        if (output.getDouble(0) > threshold) {
            return ImageType.NSFW;
        }else if(output.getDouble(1) > threshold){
            return ImageType.SAFE;
        }else{
            return ImageType.NOT_KNOWN;
        }
    }

    private ComputationGraph loadModel() throws IOException {
        computationGraph = ModelSerializer.restoreComputationGraph(new File(TRAINED_PATH_MODEL));
        return computationGraph;
    }

    // Driver code
    public static void main(String[] args) throws IOException {
        VG16ForNSFW test = new VG16ForNSFW();
        System.out.println(test.detectNSFW("https://i.imgur.com/0lkxCY2.jpg", 0.7));
    }
}

