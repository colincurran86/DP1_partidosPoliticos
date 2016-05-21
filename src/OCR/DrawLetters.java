package OCR;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

/* This class allows the user to draw letters for character recognition.*/
public class DrawLetters extends Panel
{
	protected Image entryImage;		// The image that the user is drawing into.
	protected Graphics entryGraphics;		// A graphics handle to the image that the user is drawing into.
	protected int lastX = -1;		//The last x that the user was drawing at.
	protected int lastY = -1;		//The last y that the user was drawing at.
	protected Sample sample;		//The down sample component used with this component.
	protected int rectLeft;			//Specifies the left boundary of the cropping rectangle.
	protected int rectRight;		//Specifies the right boundary of the cropping rectangle.
	protected int rectTop;			//Specifies the top boundary of the cropping rectangle.
	protected int rectBottom;		//Specifies the bottom boundary of the cropping rectangle.
	protected double ratioX;		//The downsample ratio for x.
	protected double ratioY;		//The downsample ratio for y
	protected int pixelMap[];	//The pixel map of what the user has drawn. Used to downsample it.
	protected int HEIGHT=145;
	protected int WIDTH=130;
	DrawLetters()					//constructor.
	{
		
		enableEvents(AWTEvent.MOUSE_MOTION_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK | AWTEvent.COMPONENT_EVENT_MASK);
	}
	protected void initImage()		//Setup the internal image that the user draws onto.
	{
		//System.out.println("getBounds().width"+WIDTH);
		//System.out.println("HEIGHT"+HEIGHT);
		entryImage = createImage(WIDTH, HEIGHT);
		entryGraphics = entryImage.getGraphics();
		entryGraphics.setColor(Color.blue);
		entryGraphics.fillRect(0, 0, getBounds().width, HEIGHT);
		//entryGraphics.setColor(Color.red);
		//entryGraphics.drawRect(0, 0, WIDTH, HEIGHT);
	}
	public void paint (Graphics g)	//Paint the drawn image and cropping box (if active).
	{
		
		if (entryImage == null)
			initImage();
		g.drawImage(entryImage, 0, 0, this);
		g.setColor(Color.black);//for screen
		g.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);//for screen while writing gets change to blue color,
												//again when repaint function is called in downsample color changes to black.
		/*entryGraphics.setColor(Color.red);
		entryGraphics.drawRect(0, 0, WIDTH, HEIGHT);*/
		g.setColor(Color.blue);// for downsampled image
		g.drawRect(rectLeft, rectTop, rectRight - rectLeft,rectBottom - rectTop);//downsampled rectangle
	}
	protected void processMouseEvent(MouseEvent e)		//Process messages.
	{
		
		if (e.getID() != MouseEvent.MOUSE_PRESSED)
			return;
		lastX = e.getX();
		lastY = e.getY();
	}
	protected void processMouseMotionEvent(MouseEvent e)
	{
		if (e.getID() != MouseEvent.MOUSE_DRAGGED)
			return;
		entryGraphics.setColor(Color.black);
		entryGraphics.drawLine(lastX, lastY, e.getX(), e.getY());
		Graphics g = getGraphics();
		g.drawImage(entryImage, 0, 0, this);
		lastX = e.getX();
		lastY = e.getY();
		g.setColor(Color.BLUE);//while writing blue color for screen 
		g.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);
	}
	public void setSample(Sample s)		// Set the sample control to use. The sample control displays a downsampled version of the character.
	{
		sample = s;
	}
	
	public Sample getSample()			// Get the down sample component to be used with this component.
	{
		return sample;
	}
	
	/* This method is called internally to see if there are any pixels in the given scan line. This method is used to perform autocropping.
	The horizontal line to scan , return True if there were any pixels in this horizontal line.
	*/
	protected boolean hLineClear(int y)
	{
		int w = entryImage.getWidth(this);
		for (int i = 0; i < w; i++)
		{
			if (pixelMap[(y * w) + i] != -1)
				return false;
		}
		return true;
	}
	
	/*This method is called to determine ..The vertical line to scan.return True if there are any pixels in the specified vertical line.*/
	protected boolean vLineClear(int x)
	{
		int w = entryImage.getWidth(this);
		int h = entryImage.getHeight(this);
		for (int i = 0; i < h; i++)
		{
			if (pixelMap[(i * w) + x] != -1)
				return false;
		}
		
		return true;
	}
	
	/*This method is called to automatically crop the image so that whitespace is removed.The width of the image.The height of the image  */
	protected void findBounds(int w, int h)
	{
		for (int y = 0; y < h; y++)			// top line
		{
			if (!hLineClear(y))
			{
				rectTop = y;
				break;
			}
		}
		for (int y = h - 1; y >= 0; y--)			// bottom line
		{
			if (!hLineClear(y))
			{
				rectBottom = y;
				break;
			}
			
		}
		for (int x = 0; x < w; x++)			 // left line
		{
			if (!vLineClear(x))
			{
				rectLeft = x;
				break;
			}
			
		}
		for (int x = w - 1; x >= 0; x--)			// right line
		{
			if (!vLineClear(x))
			{
				rectRight = x;
				break;
			}
			
		}
	}
	
	/*Called to downsample a quadrant of the image.The x coordinate of the resulting downsample.
	The y coordinate of the resulting downsample.Returns true if there were ANY pixels in the specified quadrant.*/
	protected boolean downSampleQuadrant(int x, int y)
	{
		int w = entryImage.getWidth(this);
		int startX = (int) (rectLeft + (x * ratioX));
		int startY = (int) (rectTop + (y * ratioY));
		int endX = (int) (startX + ratioX);
		int endY = (int) (startY + ratioY);
		for (int yy = startY; yy <= endY; yy++)
		{
			for (int xx = startX; xx <= endX; xx++)
			{
				int loc = xx + (yy * w);
				if (pixelMap[loc] != -1)
					return true;
			}
		}
		return false;
	}
	
	/*
	* Called to downsample the image and store it in the down sample component.
	*/
	public void downSample()
	{
		//System.out.println("WIDTH"+WIDTH);
		//System.out.println("HEIGHT"+HEIGHT);
		int w = entryImage.getWidth(this);
		int h = entryImage.getHeight(this);
		PixelGrabber grabber = new PixelGrabber(entryImage, 0, 0, w, h, true);
		try
		{
			grabber.grabPixels();
			pixelMap = (int[]) grabber.getPixels();
			findBounds(w, h);
			// now downsample
			SampleData data = sample.getData();
			ratioX = (double) (rectRight - rectLeft) / (double) data.getWidth();
			ratioY = (double) (rectBottom - rectTop) / (double) data.getHeight();
			for (int y = 0; y < data.getHeight(); y++)
			{
				for (int x = 0; x < data.getWidth(); x++)
				{
					if (downSampleQuadrant(x, y))
						data.setData(x, y, true);
					else
						data.setData(x, y, false);
				}
			}
			sample.repaint();
			repaint();
		}
		catch (InterruptedException e)
		{}
	}
	
	/*Called to clear the image.*/
	public void clear()
	{
		this.entryGraphics.setColor(Color.white);
		this.entryGraphics.fillRect(0, 0, WIDTH, HEIGHT);
		/*entryGraphics.setColor(Color.red);
		entryGraphics.drawRect(0, 0, WIDTH, HEIGHT);*/
		this.rectBottom = this.rectTop = this.rectLeft = this.rectRight = 0;
		repaint();
	}
}