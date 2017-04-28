import ParticleSystem.ParticleParameter;
import ParticleSystem.ParticleSystem;
import ParticleSystem.Dna;
import RBF.RBF;
import Util.FileUtility;
import Constants.Constants;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class Main extends Application {
    @FXML
    TextField population, crossover, mutation, neuronSize, maxIteration, accept;
    @FXML
    TextArea result;
    @FXML
    Button start;

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Ui.fxml"));
            Scene scene = new Scene(root, 600, 500);
            primaryStage.setTitle("RBFN");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void compute() {
        result.clear();
        start.setDisable(true);

        new Thread(() -> {
            int neuronNumber = Integer.valueOf(neuronSize.getText());

            ParticleParameter particleParameter = getParticleParameter();
            ParticleSystem particleSystem = new ParticleSystem(Constants.RBF_DEFAULT_DIMENSION, particleParameter, new RBF(neuronNumber));

            loadTrainingData(particleSystem);

            String output = "Oops! There are some error. Please try again";
            try {
                particleSystem.run();

                Dna solution = particleSystem.getBestDna();
                output = String.format("Fitness value: %.10f\n", solution.getFitnessValue());
                output += solution.toString();
            } finally {
                result.setText(output);
                start.setDisable(false);
            }
        }).start();
    }

    private void loadTrainingData(ParticleSystem particleSystem) {
        try {
            String[] files = FileUtility.getFilesName("data");
            particleSystem.loadTrainingData(FileUtility.getLines(files));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private ParticleParameter getParticleParameter() {
        ParticleParameter particleParameter = new ParticleParameter();
        particleParameter.setFitValueThreshold(accept.getText());
        particleParameter.setPopulationSize(population.getText());
        particleParameter.setIterationCount(maxIteration.getText());

        return particleParameter;
    }
}
