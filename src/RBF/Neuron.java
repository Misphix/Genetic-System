package RBF;

public class Neuron {

    public double getAngle(double _input[],double _distance[],double _sigma) {
        assert _input.length == _distance.length;
        double vectorValue = 0;
        for(int i =0;i<_input.length;i++) {
            vectorValue += Math.pow(_input[i]-_distance[i],2);
        }

        return Math.exp((-1) * (vectorValue/ (2 * Math.pow(_sigma,2))));
    }

}
