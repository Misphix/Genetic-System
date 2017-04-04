package GeneticSystem;

import java.util.ArrayList;
import java.util.Comparator;

import RBF.RBF;
import Constants.Constants;

public class GeneticSystem {

    private String trainninDatas[];
    private int iterationCount;
    private int populationSize;
    private double crossProbability;
    private double mutProbability;
    private RBF  rbf;
    private DNA solutionDNA;
    private ArrayList<DNA> dnaList;
    private int dimensions;
    private double result1 = Double.MAX_VALUE;
    private double fitValueThreshold ;


    //設定疊代次數、族群大小、突變機率、交配機率
    public GeneticSystem(int _dimensions,GeneticParameter geneticParameter, RBF _rbf) {
        this.crossProbability = geneticParameter.getCrossOverProbability();
        this.mutProbability = geneticParameter.getMutationProbability();
        this.iterationCount = geneticParameter.getIterationCount();
        this.populationSize = geneticParameter.getPopulationSize();
        this.dimensions = _dimensions;
        this.fitValueThreshold = geneticParameter.getFitValueThreshold();
        rbf = _rbf;
        dnaList = new ArrayList();

    }

    public void loadTrainingData(String _trainningDatas[]) {
        this.trainninDatas = _trainningDatas;
    }

    public void run() {
        for(int i=0; i < this.populationSize;i++) {
            this.dnaList.add(generateDNA(dimensions,this.rbf.getNeuronCount()));
        }
        this.solutionDNA = copyDNA(this.dnaList.get(0));
        int t=0;
        while (t < iterationCount) {
            reproduction();
            crossOver();
            mutate();
            if(this.solutionDNA.getFitnessVaule() <  this.fitValueThreshold) {
                break;
            }
            t++;
        }
        //this.solutionDNA.setFitnessValue(calculateDiffError(this.solutionDNA));
    }

    public DNA getSolutionDNA() {
        return this.solutionDNA;
    }

    private DNA generateDNA(int dimensions, int neuronNumber) {
        double weights[] = new double[neuronNumber];
        double distances[] = new double[neuronNumber*dimensions];
        double sigma[] = new double[neuronNumber];
        double theta = Math.random();

        for(int i=0;i<neuronNumber;i++) {
            weights[i] = (Math.random()*Constants.DNA_MAX_WEIGHT *2)-Constants.DNA_MAX_WEIGHT;
            sigma[i] = (Math.random()*10);
            for(int j = 0 ; j<dimensions ;j++) {
                distances[i*dimensions+j] = (Math.random()*30);
            }
        }
        DNA dna = new DNA(weights,distances,sigma,theta);
        dna.setFitnessValue(calculateDiffError(dna));
        return dna;
    }

    private DNA[] generateDNAs(int dimensions,int neuronNumber) {
        DNA DNAs[] = new DNA[this.populationSize];
        for(int i=0;i<this.populationSize;i++) {
            DNAs[i] = generateDNA(dimensions,neuronNumber);
        }
        return DNAs;
    }


    private double calculateDiffError(DNA dna) {
        this.rbf.setParameter(dna.getTheta(),dna.getWeights(),dna.getDistances(),dna.getSigma());
        double error = 0;
        for(int j=0;j<this.trainninDatas.length;j++) {
            String datas[] = this.trainninDatas[j].split(" ");
            double input[] = {Double.valueOf(datas[0]) ,Double.valueOf(datas[1]),Double.valueOf(datas[2])};
            error+=  Math.abs(Double.valueOf(datas[3]) -  this.rbf.getOutput(input));
        }
        return error/this.trainninDatas.length;

    }

    private void reproduction() {

        ArrayList<DNA> poolsDNAs = new ArrayList<>();
        double totalValue = 0;
        for(int d = 0 ; d < this.dnaList.size();d++) {
            this.dnaList.get(d).setFitnessValue(calculateDiffError(this.dnaList.get(d)));
            if(this.dnaList.get(d).getFitnessVaule() < this.solutionDNA.getFitnessVaule()) {
                this.solutionDNA = copyDNA(this.dnaList.get(d));
            }
            totalValue += this.dnaList.get(d).getFitnessVaule();
        }
        this.dnaList.sort(DNAComparator);
        System.out.println(calculateDiffError(this.dnaList.get(0)));
        int greatDNACount =(int) ((double)this.populationSize * Constants.DNA_FIRST_PERCENT);
        int secondDNACount = (int) ((double)this.populationSize * Constants.DNA_SECOND_PERCENT);

        DNA greatDNA = this.dnaList.get(0);
        while(greatDNACount > 0) {
            poolsDNAs.add(copyDNA(greatDNA));
            greatDNACount--;
        }
        int i = 0;

        while (secondDNACount > 0) {
            double result = (  totalValue / this.dnaList.get(i).getFitnessVaule());
            int reproduceCount =(int) Math.round( result / this.populationSize) ;
            if(reproduceCount >0 ) {
                for(int j=0;j<reproduceCount;j++) {
                    poolsDNAs.add(copyDNA(this.dnaList.get(i)));
                    secondDNACount--;
                }
            } else {
                secondDNACount--;
            }
            i++;
        }

        while (poolsDNAs.size() <  this.dnaList.size()) {
            DNA newDNA = generateDNA(this.dimensions,this.rbf.getNeuronCount());
            poolsDNAs.add(newDNA);
            if(newDNA.getFitnessVaule() <  this.solutionDNA.getFitnessVaule()) {
                this.solutionDNA = copyDNA(newDNA);
            }
        }


        this.dnaList.clear();
        this.dnaList = poolsDNAs;

        assert this.dnaList.size() == this.populationSize;
    }

    private void crossOver() {
        int persevCount =(int) (this.populationSize * Constants.DNA_RESERVE_PERCENT);
        for(int i= persevCount ;i < populationSize/2;i++) {
            double probability = Math.random();
            if(probability < this.crossProbability) {
                this.dnaList.get(i).crossOver(this.dnaList.get(populationSize/2+i));
            }
        }
    }

    private void mutate() {
        int persevCount =(int) (this.populationSize * Constants.DNA_RESERVE_PERCENT);
        for(int i = persevCount ;i < this.populationSize ;i++) {
            double probability= Math.random();
            if(probability < this.mutProbability) {
                if(this.dnaList.get(i).getFitnessVaule() > this.solutionDNA.getFitnessVaule()) {
                    this.dnaList.get(i).mutate();
                }
            }
        }

    }
    private Comparator<DNA> DNAComparator = new Comparator<DNA>() {
        @Override
        public int compare(DNA o1, DNA o2) {
            if(o1.getFitnessVaule() > o2.getFitnessVaule()) {
                return 1;
            }
            else if(o1.getFitnessVaule() < o2.getFitnessVaule()){
                return -1;
            } else {
                return 0;
            }
        }
    };

    private DNA copyDNA(DNA copyDna) {
        DNA dna = new DNA(copyValue(copyDna.getWeights()),copyValue(copyDna.getDistances()),copyValue(copyDna.getSigma()),copyDna.getTheta());
        dna.setFitnessValue(copyDna.getFitnessVaule());
        return dna;
    }

    private double[] copyValue(double copyValue[]) {
        double value[] = new double[copyValue.length];
        for (int i=0 ; i < copyValue.length;i++) {
            value[i] = copyValue[i];
        }
        return value;

    }


}
