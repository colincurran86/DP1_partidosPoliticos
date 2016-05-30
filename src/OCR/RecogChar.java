/*<APPLET code="RecogChar"
                width="1270"
                height="680">
        </APPLET>
*/

package OCR;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;

/* This class implements an applet that allows the user to test out
   basic character recognition functions.*/

public class RecogChar extends JFrame
{
	//5*7 matrix
  	static final int DOWNSAMPLE_WIDTH = 6;
  	static final int DOWNSAMPLE_HEIGHT = 8;
  	Vector sampleList = new Vector();
  	DrawLetters entry;
  	Sample sample;
  	Kalgo net;
  	private UIManager.LookAndFeelInfo looks[]; 

  	public static final String HANDWRITING[] = {
  	  "1:001100011100110100100100000100000100000100111111",
  	  "2:111100100100100100000100000100001100011000111111",
  	  "2:111100111100000100001100011100111000110000111111",
  	  "3:011110000100000100111000011100000100000101111110",
  	  "4:001000011000010000110100100100100101111111000100",
  	  "4:110001100000110000111111111111100000100000100001",
  	  "4:110110110110111110111111011111000011000011000011",  	
  	  "4:011001011001111011110011111111111110000110001110",
  	  "5:111111100000100000111111100001000001000001111111",
  	  "5:111111100000111111111111000001000001110011111111",
  	  "6:011111110000100000111110110011100001110001011111",
  	  "6:000011001111011110111000111110111111110011111111",
  	  "7:111111000001000011000010000110000100001100001000",
  	  "7:111111000001000001011111011111000011000011000011",
  	  "8:111111100001110011011110011110110011100001111111",
  	  "9:111111100001100001111111000001000001100001111111",
  	  "9:111111110011111111111111000110000110000110000110",
"2:111100111100000100001100011100111000110000111111",
"4:110001100000110000111111111111100000100000100001",
"5:111111100000111111111111000001000001110011111111",
"6:000011001111011110111000111110111111110011111111",
"7:111111000001000001011111011111000011000011000011",
"9:111111110011111111111111000110000110000110000110",
"1:000111000111111111111111000011000011000011000011",
"1:001100001100111100111100001100001100001100111111",
"3:111111000011000001111111111111000001000001111111",
"8:011111011111011001010001111111111011100001111111",
"0:111111111111110001110001100001100001100001111111",
"1:000111000111000111001111111110111110011110011110",
"1:000011000111011111111011100011000011000110000110",
"1:000111001111011111111111110111001110001100011100",
"1:001111111111111011000011000011000111000111000111",
"1:000011000011000111001111001111011110111100111100",
"2:011110011110000110001110001100011000111111111111",
"2:001111011101000011000111001110011100110011111111",
"2:011110111111110111001110011110111100111011111111",
"2:111111110111000011000111011110111100111000111111",
"2:111100111100100110001100001100011000110000111111",
"3:111111111011001111111110111110000111111011111111",
"3:001111000001000111011110011111000011001111111111",
"3:111110110111000110011110011111000011000111111110",
"3:011111011011001111011110011110001111000111111111",
"3:111111111011000111011110011111000011000011011111",
"4:011001011001111011110011111111111110000110001110",
"4:001101001101011011111011111111111110000110001110",
"4:111011110011110011110111111111111111000111000110",
"4:111000111011110011110011111111001111000011000011",
"4:001101011101111001110111111111000111000110000110",
"5:111111111011111000011000011100001100001100111100",
"5:011111111111110000110000011000011000001100111000",
"5:111111111111111000011100001110000110001110111100",
"5:011111111100110000111100011110000110011110111100",
"5:001111111111111000111000011100001110001110111100",
"6:111100111000110000110000111111111111111111111111",
"6:001111001110011100111000111111111111111111111111",
"6:000111001110001100011100111111111111111111111110",
"6:000111001110011000111000111111111111111111111110",
"6:011111111000110000110000111110111111111001111111",
"7:111110111110000110001111111111111110001100011100",
"7:111111111011000011000111111111001110011100011100",
"7:111100000110000110000110111111111110000110000110",
"7:111100001110000110000110011111011100001100011000",
"7:111100001110000110000110011111011100001100011000",
"8:011111111101111111011111011110111111110111111110",
"8:011111111101110011111111011110111111110011111111",
"8:001111011111011111001111001111111110111110111110",
"8:000111011111011111011111111100111100110110111100",
"8:000111011111011111011111111100111100110110111100",
"9:111110111111111111111111000011000011110111111110",
"9:111111111111110111111111111111000110001110011100",
"9:111111111111110111111111111111001110001110011100",
"9:111110111111111111111111111111000011000011011111",
"9:111111111111111111111111000011000111001110111100",
"5:011111110000110000111111111111000001000011111111"

  	
  	  //"A:001100001100011110011110010010010011100001100001",
      //"B:111110100011100011101110111111100001100011111110",
      //"C:001111011000110000100000100000100000100000111111",
      //"D:111110110011010001010001010001010001110011111110",
      //"D:111111100001100001100001100001100001100010111100",
      // "E:111111100000100000111111100000100000100000111111",
      //"F:111111100000100000100000111110100000100000100000",
      //"G:011110110000100000111111100101100101100101111101",
      //"H:100001100001100001111111100001100001100001100001",
      //"I:111111000100000100000100000100000100000100111111",
      //"J:111111000100000100000100000100000100101100111000",
      //"K:100011100110111100111100100010100011100001100001",
      //"L:100000100000100000100000100000100000100000111111",
      //"M:110011111111101101100101100001100001100001100001",
      //"N:100001110001111001101101100101100111100011100001",
      //"O:011110110011100001100001100011100011110010011110",
      //"P:111111100001100001100111111100100000100000100000",
      //"Q:011110110011100001100001100001100001111111111111",
      //"R:111111100001100011100110111111100001100001100001",
      //"S:011111110000100000110000011111000001000001111111",
      //"T:111111000100000100000100000100000100000100000100",
      //"U:100001100001100001100001100001100001100001111111",
      //"V:100001110001010011010010011010001110001100000100",
      //"W:100001100001110001110001010101011111011011011011",
      //"X:100001110011010110001100001100010110110011100001",
      //"Y:100001110010011110001000001000001000001000001000",//FOR KEYBOARD Y
      //"Y:100001110010011110001100001100001000010000100000",
      //"Z:111111000011000110001100001000011000110000111111"
      
  	  

      };
  	
  	public RecogChar(){
  		super("Kohenon Neuran Network Implementation");
  	}
	String messag;
  	public void init()	// Setup the GUI
  	{
  		looks = UIManager.getInstalledLookAndFeels();
  		
  		changeTheLookAndFeel( 2 ); 
  		preload();
    	updateList();

    	setLayout(new GridLayout(2, 1));

    	JPanel topPanel = new JPanel();
    	JPanel bottomPanel = new JPanel();

    	// create top button panel
    	JPanel topButtonPanel = new JPanel();
    	JPanel bottomButtonPanel = new JPanel();

    	//topButtonPanel.setLayout(new GridLayout(3, 1));

    	topButtonPanel.add(recognize = new JButton("Recognize"), BorderLayout.SOUTH);
    	//	topButtonPanel.add(clear = new JButton("Clear"));

    	JPanel addPanel = new JPanel();
    	addPanel.setLayout(new GridLayout(1, 2));
    	//addPanel.add(add = new JButton("Add:"));
    	addPanel.add(letterToAdd);
    	topButtonPanel.add(addPanel);
    	letterToAdd.setText("");

    	// create the bottom button pannel
    	bottomButtonPanel.setLayout(new GridLayout(3,1));
    	bottomButtonPanel.add(del = new JButton("Delete"),BorderLayout.NORTH);
    	bottomButtonPanel.add(delAll = new JButton("Delete All"),BorderLayout.SOUTH);
    	bottomButtonPanel.add(train = new JButton("Train"),BorderLayout.SOUTH);

    	// create top panel
    	entry = new DrawLetters();
    	topPanel.setLayout(new BorderLayout());
    	topPanel.add(message = new JLabel("Draw a letter, click Recognize"),BorderLayout.NORTH);
    	topPanel.add(entry, BorderLayout.CENTER);
    	topPanel.add(topButtonPanel, BorderLayout.EAST);
		message.setForeground(Color.BLUE);
		
		/*JPanel bottomButtonSidePanel = new JPanel();
		 bottomButtonSidePanel.setLayout(new GridLayout(1, 2));*/


		
    	// create bottom panel
    	bottomPanel.setLayout(new BorderLayout());
    	bottomPanel.add(message1=new JLabel("***************************************Known Letter Database**********************************************************************************"), BorderLayout.NORTH);
		message1.setForeground(Color.white);

    	JPanel bottomContent = new JPanel();
    	bottomContent.setLayout(new GridLayout(1, 2));
    	bottomPanel.add(bottomContent, BorderLayout.CENTER);
    	bottomPanel.add(bottomButtonPanel, BorderLayout.WEST); 

    	// create the letters panel
    	JPanel lettersPanel = new JPanel();
    	//scrollPane1.add(letters);
    	lettersPanel.setLayout(new BorderLayout());
    	lettersPanel.add(letters, BorderLayout.CENTER);

    	// create the downsample panel or the train set matrix of 5*7
    	JPanel downSamplePanel = new JPanel();
    	downSamplePanel.setLayout(new BorderLayout());
    	sample = new Sample(DOWNSAMPLE_WIDTH, DOWNSAMPLE_HEIGHT);
    	entry.setSample(sample);
    	downSamplePanel.add(sample, BorderLayout.CENTER);
    	
    	
    	JPanel panel1=new JPanel();
    	panel1.setLayout(new GridLayout(1,2));
    	panel1.add(bottomButtonPanel);
    	panel1.add(lettersPanel);

    	//bottomContent.add(lettersPanel);
    	bottomContent.add(panel1);
    	bottomContent.add(downSamplePanel);

    	add(topPanel);
    	//add(bottomPanel);
    	
        	

    	Font dialogFont = new Font("TimesNewRoman", Font.BOLD, 12);
    	clear.setFont(dialogFont);
    	add.setFont(dialogFont);
    	del.setFont(dialogFont);
    	delAll.setFont(dialogFont);
    	recognize.setFont(dialogFont);
    	train.setFont(dialogFont);

    	SymAction lSymAction = new SymAction();
    	clear.addActionListener(lSymAction);
    	add.addActionListener(lSymAction);
    	del.addActionListener(lSymAction);
    	delAll.addActionListener(lSymAction);
    	SymListSelection lSymListSelection = new SymListSelection();
    	letters.addItemListener(lSymListSelection);

    	train.addActionListener(lSymAction);
    	recognize.addActionListener(lSymAction);

    	//message1.setForeground(Color.BLUE);

    	entry.requestFocus();
    	//setSize(500,500);
    	resize(1270,680);
  	}
  	JButton add = new JButton();
  	JButton clear = new JButton();
  	JButton recognize = new JButton();

  	JScrollPane scrollPane1 = new JScrollPane();
  	java.awt.List letters = new java.awt.List();	//The letters list box
  	//JList letters=new JList();
  	JButton del = new JButton();
  	JButton delAll = new JButton();
  	JButton train = new JButton();

  	JLabel message = new JLabel();
	JLabel message1 = new JLabel();

  	JTextField letterToAdd = new JTextField("", 1);

  	class SymAction implements java.awt.event.ActionListener
  	{
		public void actionPerformed(java.awt.event.ActionEvent event)
  	  	{
    		Object object = event.getSource();
      		if (object == clear)
      		  	clear_actionPerformed(event);
      		else if (object == add)
      			add_actionPerformed(event);
      		else if (object == del)
        		del_actionPerformed(event);
      		else if (object == delAll)
        		deleteAll_actionPerformed(event);
      		else if (object == train)
        		train_actionPerformed(event);
      		else if (object == recognize)
        		recognize_actionPerformedDNI(event);
    	}
  	}

  	/* Called to clear the image.*/
  	void clear_actionPerformed(java.awt.event.ActionEvent event)
  	{
  	  	entry.clear();
  	  	sample.getData().clear();
    	sample.repaint();
  	}

  	/* Called to clear the image.*/
  	void deleteAll_actionPerformed(java.awt.event.ActionEvent event)
  	{
  	  	sampleList.removeAllElements();
  	  	net = null;
    	updateList();
    	entry.clear();
    	sample.getData().clear();
    	sample.repaint();
  	}

  	/* Called to add the current image to the training set.*/
  	void add_actionPerformed(java.awt.event.ActionEvent event)
  	{
  		
  		
  		int i;
    	String letter = letterToAdd.getText().trim();
    	if (letter.length() > 1 || letter.length()==0)
    	{
			messag="You should enter a single character";
			javax.swing.JOptionPane.showMessageDialog(null,messag,"Warning",javax.swing.JOptionPane.WARNING_MESSAGE );
      		//message.setText("Enter within 1 letter");
      		return;
    	}

    	if (letter.length() < 1)
    	{
			messag="Enter a single character";
			javax.swing.JOptionPane.showMessageDialog(null,messag,"Warning",javax.swing.JOptionPane.WARNING_MESSAGE);
      		//message.setText("Enter a letter to add.");
      		return;
    	}

    	entry.downSample();
    	SampleData sampleData = (SampleData) sample.getData().clone();
    	sampleData.setLetter(letter.charAt(0));
    	//sampleData.setLetter(letter.substring(0,1));

    	for (i = 0; i < sampleList.size(); i++)
    	{
      		SampleData str = (SampleData) sampleList.elementAt(i);
      		if (str.equals(sampleData))
      		{
				messag="Letter already defined, delete it first!";
				javax.swing.JOptionPane.showMessageDialog(null,messag,"Warning",javax.swing.JOptionPane.WARNING_MESSAGE);
	      		//message.setText("Letter already defined, delete it first!");
	        	return;
      		}

      		if (str.compareTo(sampleData) > 0)
      		{
      			//System.out.println(i);
        		sampleList.insertElementAt(sampleData, i);
        		updateList();
        		return;
      		}
    	}
    	sampleList.insertElementAt(sampleData, sampleList.size());
    	updateList();
    	letters.select(i);
    	entry.clear();
    	sample.repaint();

  	}

  	/* Called when the del button is pressed.
  	   event=The event.
   	*/
  	void del_actionPerformed(java.awt.event.ActionEvent event)
  	{
    	int i = letters.getSelectedIndex();

    	if (i == -1)
    	{
			messag="Please select a letter to delete.";
			javax.swing.JOptionPane.showMessageDialog(null,messag,"Warning",javax.swing.JOptionPane.WARNING_MESSAGE);
      		//message.setText("Please select a letter to delete.");

      		return;
    	}

    	sampleList.removeElementAt(i);
    	updateList();
  	}

  	class SymListSelection implements ItemListener
  	{
    	public void itemStateChanged(ItemEvent event)
    	{
      		Object object = event.getSource();
      		if (object == letters)
        		letters_valueChanged(event);

    	}
  	}

  	/* Called when a letter is selected from the list box.
     event=The event
  	 */
  	void letters_valueChanged(ItemEvent event)
  	{
    	if (letters.getSelectedIndex() == -1)
      		return;
    	SampleData selected = (SampleData) sampleList.elementAt(letters.getSelectedIndex());
    	sample.setData((SampleData) selected.clone());
    	//sample.setData( selected);
    	sample.repaint();
    	entry.clear();
  	}

  	/* Called when the train button is pressed.
     event=The event.
   	*/
  	void train_actionPerformed(java.awt.event.ActionEvent event)
  	{
  		
  		
		try
    	{
      		int in = RecogChar.DOWNSAMPLE_HEIGHT * RecogChar.DOWNSAMPLE_WIDTH;
      		int out = sampleList.size();

      		TrainingSet set = new TrainingSet(in, out);
      		set.setTrainingSetCount(sampleList.size());

      		for (int t = 0; t < sampleList.size(); t++)
      		{
        		int idx = 0;
        		SampleData ds = (SampleData) sampleList.elementAt(t);
        		for (int y = 0; y < ds.getHeight(); y++)
        		{
          			for (int x = 0; x < ds.getWidth(); x++)
          			{
            			set.setInput(t, idx++, ds.getData(x, y) ? .5 : -.5);
          			}
        		}
      		}

      		net = new Kalgo(in, out, this);
      		net.setTrainingSet(set);
      		net.learn();
      		this.clear_actionPerformed(null);
      		messag="Trained...\n Ready to recognize.";
      		javax.swing.JOptionPane.showMessageDialog(null,messag,"output",javax.swing.JOptionPane.INFORMATION_MESSAGE);
      		//message.setText("Trained. Ready to recognize.");
    	}
    	catch (Exception e)
    	{
     		//messag="Exception:";
			//javax.swing.JOptionPane.showMessageDialog(null,messag,"output",javax.swing.JOptionPane.INFORMATION_MESSAGE);
      		message.setText("Exception:" + e.getMessage());
    	}

  	}

  /* Called when the recognize button is pressed.
     event=The event.
   */
  	
 
  	
  	void recognize_actionPerformedDNI(java.awt.event.ActionEvent event) //funcional
  	{
  		
  		File kappa = new File("C:/temp/4.png");
  		BufferedImage imageFull = null; 
  		
  		try {
			 imageFull = ImageIO.read(kappa);
	  	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  	
  		//La imagen full la corto quedandome unicamente con los primero 100 x 50 pixeles
  		
  		Image imageDigit =  null;
  		
  		for (int i = 0 ; i < 8 ; i++) {
  			
  			imageDigit =  imageFull.getSubimage (50 * i, 0, 50, 100 ); 
  	  		
   	  		//BufferedImage buffered = (BufferedImage) imageFull;
  	  		
  	  		recognize_actionPerformed(imageDigit);
  			
  		}
  		
  	
  	}
  	
  	  	
  	void recognize_actionPerformed(Image image)
  	{
  		
 
//    	File kappa = new File("C:/temp/4.png");
	//	try {
	//		Image image = ImageIO.read(kappa);
			entry.entryImage = image;
	//	} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
	//	}
	
		
    if (net == null)
    	{
			messag="I need to be trained first!";
			javax.swing.JOptionPane.showMessageDialog(null,messag,"output",javax.swing.JOptionPane.WARNING_MESSAGE);
      		message.setText("I need to be trained first!");
      		return;
    	}
    	entry.downSample();

    	double input[] = new double[8 * 6];
    	int idx = 0;
    	SampleData ds = sample.getData();
    	for (int y = 0; y < ds.getHeight(); y++)
    	{
      		for (int x = 0; x < ds.getWidth(); x++)
      		{
        		input[idx++] = ds.getData(x, y) ? .5 : -.5;
      		}
    	}

    	double normfac[] = new double[1];
    	
    	int best = net.winner(input, normfac);
    	//map[best] = ds.getLetter(); //extra code in mapNeurons() starting from input declaration. map[sampleList.size]. 
    	char map[] = mapNeurons();
   // 	String messag;
    	
    	
    	/* if(map[best]>=97 && map[best]<=122)
		{
			messag="English Small letter : "+map[best];
			javax.swing.JOptionPane.showMessageDialog(null,messag,"output",javax.swing.JOptionPane.INFORMATION_MESSAGE);
		}
		else if(map[best]>=33 && map[best]<=47)
		{
			messag="Operands : "+map[best];
			javax.swing.JOptionPane.showMessageDialog(null,messag,"output",javax.swing.JOptionPane.INFORMATION_MESSAGE);
		}
		else if(map[best]>=65 && map[best]<=90)
		{
			messag="English Capital letter : "+map[best];
			javax.swing.JOptionPane.showMessageDialog(null,messag,"output",javax.swing.JOptionPane.INFORMATION_MESSAGE);
		}
		else if(map[best]>=48 && map[best]<=57)
		{
			messag="English Numerical : "+map[best];
			javax.swing.JOptionPane.showMessageDialog(null,messag,"output",javax.swing.JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
			messag="Special Character : "+map[best];
			javax.swing.JOptionPane.showMessageDialog(null,messag,"output",javax.swing.JOptionPane.INFORMATION_MESSAGE);
		}

*/ 
    	
    	System.out.println( map[best]); // redirijo la salida a consola !!
    	
//	clear_actionPerformed(null);
	
	

  	}
  	
  	
  	
  	
  /* Used to map neurons to actual letters.
     return The current mapping between neurons and letters as an array.
   */
  	char[] mapNeurons()
  	{
	    char map[] = new char[sampleList.size()];
    	double normfac[] = new double[1];
    	
    	for (int i = 0; i < map.length; i++)
      		map[i] = '?';
    	for (int i = 0; i < sampleList.size(); i++)
    	{
      		double input[] = new double[8 * 6];
      		int idx = 0;
      		SampleData ds = (SampleData) sampleList.elementAt(i);
      		for (int y = 0; y < ds.getHeight(); y++)
      		{
        		for (int x = 0; x < ds.getWidth(); x++)
        		{
          			input[idx++] = ds.getData(x, y) ? .5 : -.5;
        		}
      		}

      		int best = net.winner(input, normfac);
      		map[best] = ds.getLetter();
    	}
    	return map;
  	}

  	/*this updateList fn gets d char from sampleList (from preload) and displays it in the list onto d screen. also stores chars in 
  	 * letters (java.awt.List)*/
  	public void updateList()
  	{//System.out.println("in updatelist");
    	letters.removeAll();
    	for (int i = 0; i < sampleList.size(); i++)
    	{
      		SampleData sample = (SampleData) sampleList.elementAt(i);
      		//System.out.println("sample="+sample);
      		letters.add("" + sample.letter); 
    	}

  	}
  	
/*this preload fn maps d letter  and d grid values (sampleData) and loads. also stores d char in sampleList (vector)*/
  	public void preload()
  	{
  		//System.out.println("in preload");
    	int index = 0;
    	//System.out.println("RecogChar.HANDWRITING.length="+RecogChar.HANDWRITING.length);
    	for (int i = 0; i < RecogChar.HANDWRITING.length; i++)
    	{
      		String line = HANDWRITING[i].trim();
      		//System.out.println("line="+line);
      		SampleData ds = new SampleData(line.charAt(0),RecogChar.DOWNSAMPLE_WIDTH, RecogChar.DOWNSAMPLE_HEIGHT);
      		sampleList.insertElementAt(ds, index++);
      		int idx = 2;
      		for (int y = 0; y < ds.getHeight(); y++)
      		{
        		for (int x = 0; x < ds.getWidth(); x++)
        		{
        	  		ds.setData(x, y, line.charAt(idx++) == '1');
        		}
      		}
    	}
    	train_actionPerformed(null);
  	}
  	
  	private void changeTheLookAndFeel( int value )
  	      {
  			//System.out.println("in changelook and feel");
  	         try // change look and feel
  	         {
  	           // set look and feel for this application                 
  	            UIManager.setLookAndFeel( looks[ value ].getClassName() );
  	        //  System.out.println("looks[2]="+looks[ value ].getClassName());
  	            // update components in this application     
  	            SwingUtilities.updateComponentTreeUI( this );
  	         } // end try
  	         catch ( Exception exception )
  	         {
  	            exception.printStackTrace();
  	         } // end catch
  	      } //
  	public static void main(String args[]){
  		RecogChar recogChar = new RecogChar();
  		recogChar.setVisible(true);
  		recogChar.init();
  		recogChar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  		recogChar.setSize(820, 580);
  	}


	public void recognize_actionPerformedDNI(String ruta) {
		// TODO Auto-generated method stub

  		File kappa = new File(ruta);
  		BufferedImage imageFull = null; 
  		
  		try {
			 imageFull = ImageIO.read(kappa);
	  	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  	
  		//La imagen full la corto quedandome unicamente con los primero 100 x 50 pixeles
  		
  		Image imageDigit =  null;
  		
  	  	recognize_actionPerformed(imageFull);
  		
 // 		for (int i = 0 ; i < 8 ; i++) {
  			
  	//		imageDigit =  imageFull.getSubimage (50 * i, 0, 50, 100 ); 
  	  		
   	  		//BufferedImage buffered = (BufferedImage) imageFull;
  	  		

  			
  //		}
  		
	}
}