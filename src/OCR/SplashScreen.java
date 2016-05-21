
package OCR;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SplashScreen extends JFrame
{
	JLabel l1,l2;
	JProgressBar jp;
	JPanel p1;
	javax.swing.Timer timer;
	RecogChar rc=new RecogChar();

	public SplashScreen()
	{
	    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	    try
	    {
			setLocation(200,150);
		 	this.setSize(new Dimension(450, 350));

			Container c=this.getContentPane();
			l1=new JLabel("Loading....");
	 		l1.setFont(new java.awt.Font("TimesNewRoman", 1, 22));
			//l1.setForeground(new Color(223, 39, 7));
			l1.setHorizontalAlignment(SwingConstants.CENTER);
			l1.setBounds(new Rectangle(16, 231, 398, 70));

			l2=new JLabel("A project On Pattern recognition");
 			l2.setFont(new java.awt.Font("TimesNewRoman", 1, 22));
			//l2.setForeground(new Color(200, 39, 7));
			l2.setHorizontalAlignment(SwingConstants.CENTER);
			l2.setBounds(new Rectangle(30, 4, 348, 203));

			p1=new JPanel();
		    //p1.setBackground(Color.black);
   			//p1.setForeground(Color.white);
			p1.setVisible(true);

			jp = new JProgressBar(0,100);
			//jp.setBackground(Color.white);
			jp.setForeground(Color.BLACK);
			//jp.setForeground(new Color(140, 184, 151));
    		jp.setStringPainted(true);
   			jp.setBounds(new Rectangle(71, 220, 297, 24));

			p1.setLayout(null);
			p1.add(l2, null);
			p1.add(l1, null);
			p1.add(jp, null);


			c.add(p1);
			setUndecorated(true);
			setVisible(true);
		
			try
			{

   				timer = new javax.swing.Timer(50, createLoadAction());
				timer.setRepeats(false);
			    while(true)
				{
					  timer.start();
				}
			 }
			catch(Exception e1)
			{
				e1.printStackTrace();  
				System.out.println("TIME OUT");
			}
     	}
	    catch(Exception e) {}
	}
	public Action createLoadAction()
	{
		return new AbstractAction("loading...")
		{
			public void actionPerformed (ActionEvent e)
			{
				try
				{
					if(jp.getValue() < jp.getMaximum())
					{
						jp.setValue(jp.getValue() + 1);
					}
                	else
					{
						timer.stop();
						setVisible(false);
						RecogChar recogChar = new RecogChar();
				  		recogChar.setVisible(true);
				  		recogChar.init();
				  		recogChar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				  		recogChar.setSize(720, 640);
				  		

						timer=null;
						dispose();
					}
	    		}catch(Exception a){}
			}
         };
	}
	public static void main(String[] args)
	{
		SplashScreen c1=new SplashScreen();
	}
}