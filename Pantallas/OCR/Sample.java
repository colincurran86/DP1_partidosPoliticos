package OCR;

import java.awt.*;
/* This class displays a character in a "downsampled" form.*/
public class Sample extends Panel
{
 	SampleData data;	//The image data.
 	/* The constructor.width=The width of the downsampled image,height=The height of the downsampled image*/
  	Sample(int width, int height)
  	{
    	data = new SampleData(' ', width, height);
  	}
  	/* The image data object. return The image data object.*/
  	SampleData getData()
  	{
    	return data;
  	}
  	/* Assign a new image data object. data=The image data object.*/
  	void setData(SampleData data)
  	{
    	this.data = data;
  	}
  	/* g=Display the downsampled image.*/
  	public void paint(Graphics g)
  	{
    	if (data == null)
      		return;
    	int x, y;
    	int vcell = getBounds().height / data.getHeight();
    	int hcell = getBounds().width / data.getWidth();

   		g.setColor(Color.white);
    	g.fillRect(0, 0, getBounds().width, getBounds().height);
    	g.setColor(Color.black);

    	for (y = 0; y < data.getHeight(); y++)
      		g.drawLine(0, y * vcell, getBounds().width, y * vcell);
    	for (x = 0; x < data.getWidth(); x++)
      		g.drawLine(x * hcell, 0, x * hcell, getBounds().height);
    	for (y = 0; y < data.getHeight(); y++)
    	{
      		for (x = 0; x < data.getWidth(); x++)
      		{
        		if (data.getData(x, y))
          			g.fillRect(x * hcell, y * vcell, hcell, vcell);
      		}
    	}

    	g.setColor(Color.black);
    	g.drawRect(0, 0, getBounds().width - 1, getBounds().height - 1);
  }
}