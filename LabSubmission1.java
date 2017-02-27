import java.util.*;

/**
 * Created by jesse on 2017/02/27.
 */
public class LabSubmission1 {
    public static int Err = 0;

    public static void main(String[] args){

        Scanner in = new Scanner(System.in);
        String line;
        List<double[]> arr = new ArrayList<>();
        int rowlength = 0;

        //Separate input into List of double[]:
        System.out.println("Please provide input separated by spaces (leave last line blank and press enter to end input stream): ");
        while (!(line = in.nextLine()).isEmpty()){
            String[] inputS = line.split(" ");
            double[] inputX = new double[inputS.length];

            for (int i = 0; i < inputS.length; i++){
                inputX[i] = Double.parseDouble(inputS[i]);
            }

            arr.add(inputX);
            rowlength = inputX.length;
        }

        //Target separated from original input List, and List is augmented with -1:
        int[] Target = new int[arr.size()];

        for (int j = 0; j < arr.size(); j++){

            double[] t = arr.get(j);
            Target[j] = (int)t[rowlength-1];
            t[rowlength-1] = -1;
            arr.set(j, t);

        }

        //Weights initialised with random doubles between -1 and 1:
        double min = -1;
        double max = 1;
        double[] weight = new double[rowlength];
        Random r = new Random();

        for (int k = 0; k < weight.length; k++){

            double result = min + (r.nextDouble()*(max - min));
            weight[k] = (double)Math.round(result*10000d)/10000d;

        }

        //Learning Rate initialised:
        double rate = 0.25;


        double[] learntWeight = PerceptLearn(arr, Target, weight, rate);

        for (int m = 0; m < learntWeight.length; m++){
            System.out.println("Final weight " + (m+1) + ": " + learntWeight[m]);
        }
        System.out.println("Total Error of the dataset is: " + Err);

        in.close();
    }

    public static int percept(double[] W, double[] X){

        int out;
        double[] r = new double[W.length];
        double result = 0;

        for (int i = 0; i < W.length; i++){
            r[i] = W[i]*X[i];
            result = result + r[i];
        }

        if (result <= 0){
            out = 0;
        } else {
            out = 1;
        }

        return out;
    }

    public static double[] PerceptLearn(List<double[]> X, int[] T, double[] W, double n){

        List<Integer> Order = new ArrayList<>();

        for (int o = 0; o < X.size(); o++){
            Order.add(o);
        }

        Collections.shuffle(Order);

        for (int j = 0; j < X.size(); j++){
            int O = Order.get(j);
            double[] row = X.get(O);
            int Y = percept(W, row);
            Err = Err + Math.abs(T[O]-Y);

            for (int k = 0; k < row.length; k++){
                W[k] = W[k] + (n*(T[O] - Y)*row[k]);
            }

        }

        return W;
    }

}
