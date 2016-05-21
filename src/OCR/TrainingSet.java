package OCR;

import java.io.*;
import java.util.*;

/* -------------------------------------------------
 *
 * This class holds the training set for the neural network.
 *
 * -------------------------------------------------
 */

public class TrainingSet
{

  protected int inputCount;
  protected int outputCount;
  protected double input[][];
  protected double output[][];
  protected double classify[];
  protected int trainingSetCount;

  /* The constructor.
   * inputCount=Number of input neurons
   * outputCount=Number of output neurons
   */
  TrainingSet(int inputCount, int outputCount)
  {
    this.inputCount = inputCount;
    this.outputCount = outputCount;
    trainingSetCount = 0;
  }

  /* Get the input neuron count
   * return The input neuron count
   */
  public int getInputCount()
  {
	  
    return inputCount;
  }

  /* Get the output neuron count
   * return The output neuron count
   */
  public int getOutputCount()
  {
    return outputCount;
  }

  /* Set the number of entries in the training set. This method also allocates
   * space for them.
   * trainingSetCount=How many entries in the training set.
   */
  public void setTrainingSetCount(int trainingSetCount)
  {
    this.trainingSetCount = trainingSetCount;
    input = new double[trainingSetCount][inputCount];
    output = new double[trainingSetCount][outputCount];
    classify = new double[trainingSetCount];
  }

  /* Get the training set data.
   * return Training set data.
   */
  public int getTrainingSetCount()
  {
    return trainingSetCount;
  }

  /* Set one of the training set's inputs.
   * param set=The entry number
   * index=The index(which item in that set)
   * value=The value
   * exception java.lang.RuntimeException
   */
  void setInput(int set, int index, double value) throws RuntimeException
  {
    if ((set < 0) || (set >= trainingSetCount))
      throw (new RuntimeException("Training set out of range:" + set));
    if ((index < 0) || (index >= inputCount))
      throw (new RuntimeException("Training input index out of range:" + index));
    
    
    input[set][index] = value;
  }

  /* Set one of the training set's outputs.
   * set=The entry number
   * index=The index(which item in that set)
   * value=The value
   * exception java.lang.RuntimeException
   */
  void setOutput(int set, int index, double value) throws RuntimeException
  {
    if ((set < 0) || (set >= trainingSetCount))
      throw (new RuntimeException("Training set out of range:" + set));
    if ((index < 0) || (set >= outputCount))
      throw (new RuntimeException("Training input index out of range:" + index));
    output[set][index] = value;
  }

  /* Set one of the training set's classifications.
   * set=The entry number
   * value=The value
   * exception java.lang.RuntimeException
   */
  void setClassify(int set, double value) throws RuntimeException
  {
    if ((set < 0) || (set >= trainingSetCount))
      throw (new RuntimeException("Training set out of range:" + set));
    classify[set] = value;
  }

  /* Get a specified input value.
   * set=The input entry.
   * index=The index
   * return An individual input
   * exception java.lang.RuntimeException
   */
  double getInput(int set, int index) throws RuntimeException
  {
    if ((set < 0) || (set >= trainingSetCount))
      throw (new RuntimeException("Training set out of range:" + set));
    if ((index < 0) || (index >= inputCount))
      throw (new RuntimeException("Training input index out of range:" + index));
    
    return input[set][index];
  }

  /* Get one of the output values.
   * set=The entry
   * index=Which value in the entry
   * return The output value.
   * exception java.lang.RuntimeException
   */
  double getOutput(int set, int index) throws RuntimeException
  {
    if ((set < 0) || (set >= trainingSetCount))
      throw (new RuntimeException("Training set out of range:" + set));
    if ((index < 0) || (set >= outputCount))
      throw (new RuntimeException("Training input index out of range:" + index));
   return output[set][index];
  }

  /* Get the classification.
   * set=Which entry.
   * return The classification for the specified entry.
   * exception java.lang.RuntimeException
   */
  double getClassify(int set) throws RuntimeException
  {
    if ((set < 0) || (set >= trainingSetCount))
      throw (new RuntimeException("Training set out of range:" + set));
   return classify[set];
  }

  /* Get an output set.
   * set=The entry requested.
   * return The complete output set as an array.
   * exception java.lang.RuntimeException
   */

  double[] getOutputSet(int set) throws RuntimeException
  {
    if ((set < 0) || (set >= trainingSetCount))
      throw (new RuntimeException("Training set out of range:" + set));
    return output[set];
  }

  /* Get an input set.
   * set=The entry requested.
   * return The complete input set as an array.
   * exception java.lang.RuntimeException
   */

  double[] getInputSet(int set) throws RuntimeException
  {
    if ((set < 0) || (set >= trainingSetCount))
      throw (new RuntimeException("Training set out of range:" + set));
   return input[set];
  }

}
