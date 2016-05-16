package OCR;

import java.util.*;

/* -------------------------------------------------
  This class implements a somewhat generic neural network,
  it forms the base class for the Kohonen neural network.
  -------------------------------------------------
 */

abstract public class BaseClassKnetwork
{
  	public final static double NEURON_ON = 0.9;		//The value to consider a neuron on
  	public final static double NEURON_OFF = 0.1;		//The value to consider a neuron off
  	protected double output[];						//Output neuron activations
  	protected double totalError;						//Mean square error of the network
  	protected int inputNeuronCount;					//Number of input neurons
  	protected int outputNeuronCount;					//Number of output neurons
  	protected Random random = new Random(System.currentTimeMillis());	//Random number generator

  	abstract public void learn() throws RuntimeException;	/* Called to learn from training sets.exception java.lang.RuntimeException*/
  	abstract void trial(double[] input);					/* Called to present an input pattern.input=The input pattern*/
  	

  	/* Called to calculate the trial errors.train=The training set.return The trial error.exception java.lang.RuntimeException*/
  	
  	double calculateTrialError(TrainingSet train) throws RuntimeException
  	{
  	  	int i, tclass;
    	double diff;

    	totalError = 0.0; // reset total error to zero

    	// loop through all samples
    	for (int t = 0; t < train.getTrainingSetCount(); t++)
    	{
      		// trial
      		trial(train.getOutputSet(t));
      		tclass = (int) (train.getClassify(train.getInputCount() - 1));
      		
      		System.out.println("tclass="+tclass);
      		
      		for (i = 0; i < train.getOutputCount(); i++)
      		{
        		if (tclass == i)
        	  		diff = NEURON_ON - output[i];
        		else
          			diff = NEURON_OFF - output[i];
        		totalError += diff * diff;
      		}
		    for (i = 0; i < train.getOutputCount(); i++)
      		{
        		diff = train.getOutput(t, i) - output[i];
        		totalError += diff * diff;
			}
    	}
   		totalError /= (double) train.getTrainingSetCount();
   		//System.out.println("totalError="+totalError);
    	return totalError;
  	}
  	
  	
  	static double vectorLength(double v[])	/* Calculate the length of a vector.v=vector,return Vector length.*/
  	{
    	double rtn = 0.0;
    	for (int i = 0; i < v.length; i++)
      		rtn += v[i] * v[i];
    	return rtn;
  	}

  	double dotProduct(double vec1[], double vec2[])	/* Called to calculate a dot product.vec1=one vector,vec2=another vector,return The dot product.*/
  	{
    	int k, v;
    	double rtn;
    	rtn = 0.0;
    	k = vec1.length;  

        v = 0;
        while ( (k--)>0 ) {
          rtn += vec1[v] * vec2[v];
          v++;
        }
        return rtn;
  	}

  	/* Called to randomize weights.weight=A weight matrix.*/
  	void randomizeWeights(double weight[][])
  	{
  		double r;
    	int temp = (int) (3.464101615 / (2. * Math.random())); // SQRT(12)=3.464...
		for (int y = 0; y < weight.length; y++)
    	{
      		for (int x = 0; x < weight[0].length; x++)
      		{
        		r = (double) random.nextInt() + (double) random.nextInt()-(double) random.nextInt() - (double) random.nextInt();
        		weight[y][x] = temp * r;
      		}
    	}
		/*System.out.println("Randomizing weights in BaseKNetwork");
		for(int i=0;i<weight.length;i++){
		 for(int j=0;j<weight[i].length;j++){
			System.out.print("weight[][]="+weight[i][j]+"\t");}
			System.out.println();}*/
  	}
}