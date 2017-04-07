import GeneticSystem.GeneticParameter;
import RBF.RBF;
import GeneticSystem.GeneticSystem;
import GeneticSystem.Dna;
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

            GeneticParameter geneticParameter = getGeneticParameter();
            GeneticSystem geneticSystem = new GeneticSystem(Constants.RBF_DEFAULT_DIMENSION, geneticParameter, new RBF(neuronNumber));

            loadTrainingData(geneticSystem);

            geneticSystem.run();

            Dna solution = geneticSystem.getSolutionDNA();
            String output = String.format("Fitness value: %.10f\n", solution.getFitnessValue());
            output += solution.toString();
            result.setText(output);
            start.setDisable(false);
        }).start();
    }

    private void loadTrainingData(GeneticSystem geneticSystem) {
        try {
            String[] files = FileUtility.getFilesName("data");
            geneticSystem.loadTrainingData(FileUtility.getLines(files));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private GeneticParameter getGeneticParameter() {
        GeneticParameter geneticParameter = new GeneticParameter();
        geneticParameter.setCrossOverProbability(crossover.getText());
        geneticParameter.setFitValueThreshold(accept.getText());
        geneticParameter.setMutationProbability(mutation.getText());
        geneticParameter.setPopulationSize(population.getText());
        geneticParameter.setIterationCount(maxIteration.getText());

        return geneticParameter;
    }
}
