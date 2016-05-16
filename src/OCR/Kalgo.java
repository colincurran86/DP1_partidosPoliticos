package OCR;

/* This class implements a Kohonen neural network.*/
public class Kalgo extends BaseClassKnetwork
{
	double outputWeights[][];		// The weights of the output neurons base on the input from the input neurons.
	protected double learnRate = 0.3;		//The learning rate.
	protected double quitError = 0.01;		//Abort if error is beyond this
	protected int retries = 10000;		//How many retries before quit.
	protected double reduction = .99;		// Reduction factor.
	protected RecogChar owner;		//The owner object, to report to.
	public boolean halt = false;		//Set to true to abort learning.
	protected TrainingSet train;		//The training set.
	
	// The constructor.inputCount=Number of input neurons,outputCount=Number of output neurons,owner= The owner object, for updates.
	public Kalgo(int inputCount, int outputCount, RecogChar owner)	
	{
		totalError = 1.0;
		this.inputNeuronCount = inputCount;
		this.outputNeuronCount = outputCount;
		this.outputWeights = new double[outputNeuronCount][inputNeuronCount + 1];
		this.output = new double[outputNeuronCount];
		this.owner = owner;
	}
	public void setTrainingSet(TrainingSet set)	// Set the training set to use.set= The training set to use.
	{
		train = set;
	}
	//Used to replace the old weight matrix
	public static void copyWeights(Kalgo dest, Kalgo source)	//Copy the weights from this network to another.dest= The destination for the weights.
	{
		for (int i = 0; i < source.outputWeights.length; i++)
		{
			System.arraycopy(source.outputWeights[i], 0, dest.outputWeights[i], 0,source.outputWeights[i].length);
		}
	}
	public void clearWeights()		// Clear the weights.
	{
		totalError = 1.0;
		for (int y = 0; y < outputWeights.length; y++)
			for (int x = 0; x < outputWeights[0].length; x++)
				outputWeights[y][x] = 0;
	}
	//Calculates the normalization factor
	void normalizeInput(final double input[], double normfac[])
	{
		double length;
		length = vectorLength(input);
		// just in case it gets too small
		if (length < 1.E-30)
			length = 1.E-30;
		normfac[0] = 1.0 / Math.sqrt(length);//normfac[0]=0.3380617018914066
		
	}
	/* Normalize weights,w=Input weights*/
	void normalizeWeight(double w[])
	{
		int i;
		double len;
		len = vectorLength(w);
		// just in case it gets too small
		if (len < 1.E-30)
			len = 1.E-30;
		len = 1.0 / Math.sqrt(len);
		for (i = 0; i < inputNeuronCount; i++)
			w[i] *= len;
		w[inputNeuronCount] = 0;
		
		/*for(int k=0;i<w.length;i++){
			 System.out.print("weight[]="+w[k]+"\t");}
		System.out.println();*/
	}
	/* This function is to normalize the input.
	 * 
	 * Try an input pattern. This can be used to present an input pattern to the network.
	 *  Usually its best to call winner to get the winning neuron though.
	input= Input pattern.*/
	void trial(double input[])
	{
		int i;
		double normfac[] = new double[1], optr[];
		normalizeInput(input, normfac);
		for (i = 0; i < outputNeuronCount; i++)
		{
			optr = outputWeights[i];
			//the normalize output of first neuron 
			output[i] = dotProduct(input, optr) * normfac[0];
			// Remap to bipolar (-1,1 to 0,1)
			output[i] = 0.5 * (output[i] + 1.0);
			// account for rounding
			if (output[i] > 1.0)
				output[i] = 1.0;
			if (output[i] < 0.0)
				output[i] = 0.0;
		}
	}
	/*Present an input pattern and get the winning neuron.
	 * input=input pattern,normfac=the result
	synth=synthetic last input ,return The winning neuron number.*/
	public int winner(double input[], double normfac[])
	{
		
		int i, win = 0;
		double biggest, optr[];
		normalizeInput(input, normfac); // Normalize input
		biggest = -1.E30;
		for (i = 0; i < outputNeuronCount; i++)
		{
			optr = outputWeights[i];
			output[i] = dotProduct(input, optr) * normfac[0];
			// Remap to bipolar(-1,1 to 0,1)
			output[i] = 0.5 * (output[i] + 1.0);
			if (output[i] > biggest)
			{
				biggest = output[i];
				win = i;
			}
			// account for rounding
			if (output[i] > 1.0)
				output[i] = 1.0;
			if (output[i] < 0.0)
				output[i] = 0.0;
		}
		return win;
	}

	/* This method does much of the work of the learning process. This method evaluates the weights against the training set.
	rate=learning rate,learn_method=method(0=additive, 1=subtractive).won=a Holds how many times a given neuron won
	bigerr=a returns the error,correc=a returns the correction,work=a work area,*/
	void evaluateErrors(double rate, int won[],double bigerr[], double correc[][])throws RuntimeException
	{
		int best, tset;
		double dptr[], normfac[] = new double[1];
		double cptr[], wptr[], length, diff;
		// reset correction and winner counts
		for (int y = 0; y < correc.length; y++)
		{
			for (int x = 0; x < correc[0].length; x++)
			{
				correc[y][x] = 0;
			}
		}
		for (int i = 0; i < won.length; i++)
			won[i] = 0;
		bigerr[0] = 0.0;
		// loop through all training sets to determine correction
		for (tset = 0; tset < train.getTrainingSetCount(); tset++)
		{
			dptr = train.getInputSet(tset);
			best = winner(dptr, normfac);
			won[best]++;// value of winner neuron becomes 1.
			wptr = outputWeights[best];
			cptr = correc[best];
			length = 0.0;
			for (int i = 0; i < inputNeuronCount; i++)
			{
				diff = dptr[i] * normfac[0] - wptr[i];//diff=normalized input - weight (of winner neuron)
				length += diff * diff;
				cptr[i] += diff;
				correc[tset][i]=cptr[i];
			}
			if (length > bigerr[0])
				bigerr[0] = length;
		}
		bigerr[0] = Math.sqrt(bigerr[0]);
	}
	
	/*This method is called at the end of a training iteration. This method adjusts the weights based on the previous trial.
	rate=learning rate ,learn_method=method(0=additive, 1=subtractive),won=a holds number of times each neuron won
	bigcorr=holds the error,correc=holds the correction,*/
	void adjustWeights(double rate,int won[],double bigcorr[], double correc[][])
	{
		double corr, cptr[], wptr[], length, f;
		bigcorr[0] = 0.0;
		for (int i = 0; i < outputNeuronCount; i++)
		{
			if (won[i] == 0)
				continue;
			wptr = outputWeights[i];
			cptr = correc[i];
			f = 1.0 / (double) won[i];
			//System.out.println("f="+f);
			f *= rate;
			length = 0.0;
			for (int j = 0; j <= inputNeuronCount; j++)
			{
				corr = f * cptr[j];//alpha * (e = normalized input - weight_previous (of winner neuron)
				wptr[j] += corr;//w_previous + (corr = alpha * e)
				length += corr * corr;
			}
			if (length > bigcorr[0])
				bigcorr[0] = length;
		}
		// scale the correction
		bigcorr[0] = Math.sqrt(bigcorr[0]) / rate;
	}
	
	/*1)Finds the training set that has never classified.
	  2)choose an output neuron (that did not ever win and seeing which one has the highest activation)
	   that will be modified to better classify the training set identified in the previous step
	   3)we will modify the weight of this neuron so that it will better classify this pattern next time.*/
	
	/* If no neuron wins, then force a winner.won=how many times each neuron won*/
	void forceWin(int won[]) throws RuntimeException
	{
		int i, tset, best, which = 0;
		double dptr[], normfac[] = new double[1];
		double dist, optr[];
		dist = 1.E30;
		for (tset = 0; tset < train.getTrainingSetCount(); tset++)
		{
			dptr = train.getInputSet(tset);
			best = winner(dptr, normfac);
			if (output[best] < dist)
			{
				dist = output[best];
				which = tset;
			}
		}
		dptr = train.getInputSet(which);
		best = winner(dptr, normfac);
		dist = -1.e30;
		i = outputNeuronCount;
		while ((i--) > 0)
		{
			if (won[i] != 0)
				continue;
			if (output[i] > dist)
			{
				dist = output[i];
				which = i;
			}
		}
		optr = outputWeights[which];//optr=output weights ranges between -1 and +1.
		
		/*for(int j=0;j<inputNeuronCount;j++)
			System.out.println("optr[j]"+optr[j]);//optr=output weights ranges between -1 and +1.*/
		
		System.arraycopy(dptr, 0, optr, 0, dptr.length);//not able to follow. Removing this goes to infinite loop.
		normalizeWeight(optr);
	}
	
	/*This method is called to train the network. It can run for a very long time and will report progress back to the owner object.*/
	public void learn() throws RuntimeException
	{
		int i, tset, iter, n_retry;
		int won[], winners;
		double  correc[][], rate, best_err, dptr[];
		double bigerr[] = new double[1];
		double bigcorr[] = new double[1];
		Kalgo bestnet; // Preserve best here
		totalError = 1.0;
		for (tset = 0; tset < train.getTrainingSetCount(); tset++)
		{
			dptr = train.getInputSet(tset);
			if (vectorLength(dptr) < 1.E-30)
			{
				throw (new RuntimeException("Multiplicative normalization has null training case"));
			}
		}
		bestnet = new Kalgo(inputNeuronCount, outputNeuronCount, owner);
		won = new int[outputNeuronCount];
		correc = new double[outputNeuronCount][inputNeuronCount + 1];
		rate = learnRate;
		initialize();
		best_err = 1.e30;
		// main loop:
		n_retry = 0;
		for (iter = 0;; iter++)
		{
			evaluateErrors(rate, won, bigerr, correc);// calculates winner for all sets n finds e. bigerr=vector_length(e).
			totalError = bigerr[0];
			if (totalError < best_err)
			{
				best_err = totalError;
				copyWeights(bestnet, this);
			}
			winners = 0;
			for (i = 0; i < won.length; i++)
				if (won[i] != 0)
					winners++;//winners must be equal to outputNeuronCount.
			if (bigerr[0] < quitError)
				break;
			if ((winners < outputNeuronCount) && (winners < train.getTrainingSetCount()))
			{
				forceWin(won);
				continue;
			}

			adjustWeights(rate, won, bigcorr, correc);

			// owner.updateStats(n_retry,totalError,best_err);
			if (halt)
			{
				// owner.updateStats(n_retry,totalError,best_err);
				break;
			}
			Thread.yield();

			if (bigcorr[0] < 1E-5)// dint follow this particular condition, rest(inside this block) is similar to flowchart 10th step.
									//bigcorr is the vector length(weight)/rate from adjustWeight() fn.
			{
				if (++n_retry > retries)
					break;
				initialize();
				iter = -1;
				rate = learnRate;
				continue;
			}

			if (rate > 0.01)
				rate *= reduction;

		}
		
    // done

    copyWeights(this, bestnet);

    for (i = 0; i < outputNeuronCount; i++)
      normalizeWeight(outputWeights[i]);

    halt = true;
    
    
    //n_retry++;// y is this needed over here?
    // owner.updateStats(n_retry,totalError,best_err);
  }
	
	
  /* Called to initialize the Kononen network.*/
  public void initialize()
  {
    int i;
    double optr[];

    clearWeights();
    randomizeWeights(outputWeights);
    for (i = 0; i < outputNeuronCount; i++)
    {
      optr = outputWeights[i];
      normalizeWeight(optr);
    }
    
  }

}