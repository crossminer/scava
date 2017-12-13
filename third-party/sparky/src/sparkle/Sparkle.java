package sparkle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import sparkle.dimensions.DateDimension;
import sparkle.dimensions.LinearDimension;
import sparkle.dimensions.SparkDimension;

public class Sparkle {
	
	protected final int imgHeight;
	protected final int imgWidth;
	protected final int padding;
	
	protected Color lineColor;
	protected Color pointColor;
	
	public Sparkle(int imgHeight, int imgWidth, int padding) {
		this.imgHeight = imgHeight;
		this.imgWidth= imgWidth;
		this.padding = padding;
		
		this.lineColor = new Color(0, 115, 180);
		this.pointColor = new Color(255, 148, 0);
	}
	
	public void setLineColor(Color c) {
		this.lineColor = c;
	}
	
	public void setPointColor(Color c) {
		this.pointColor = c;
	}
	
	public void setHeadless(boolean headless) {
		System.setProperty("java.awt.headless", String.valueOf(headless));
	}

	
	public void renderToFile(SparkDimension xdim, SparkDimension ydim, File file) throws IOException {
		BufferedImage im = renderAsBufferedImage(xdim, ydim);
		ImageIO.write(im, "png", file);
	}
	
	public byte[] renderToByteArray(SparkDimension xdim, SparkDimension ydim) throws IOException {
		BufferedImage im = renderAsBufferedImage(xdim, ydim);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(im, "png", baos);
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		
		return imageInByte;
	}
	
	
	public BufferedImage renderAsBufferedImage(SparkDimension xdim, SparkDimension ydim) {
		BufferedImage im = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g = (Graphics2D) im.getGraphics();
		
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g.setRenderingHints(rh);
		
	    g.setColor(Color.white);
	    g.fillRect(0, 0, imgWidth, imgHeight);

	    Color c = new Color(0, 115, 180);
	    g.setColor(c);
	    BasicStroke stroke = new BasicStroke(2);
	    g.setStroke(stroke);
	    
	    // Draw lines
	    int prevX = xdim.get(0);
	    int prevY = imgHeight - ydim.get(0);
	    
	    for (int i = 1; i< xdim.size(); i++) {
	    	int x = xdim.get(i);
	    	int y = imgHeight - ydim.get(i);
	    	
	    	g.drawLine(prevX, prevY, x, y);
	    	prevX = x;
	    	prevY = y;
	    }
	    
	    // Draw high and low
	    Color orange = new Color(255, 148, 0);
	    g.setColor(orange);
	    
	    g.fillOval(xdim.get(ydim.indexOf(ydim.getMinValue()))-5, imgHeight - ydim.scale(ydim.getMinValue())-5, 10, 10);
	    g.fillOval(xdim.get(ydim.indexOf(ydim.getMaxValue()))-5, imgHeight - ydim.scale(ydim.getMaxValue())-5, 10, 10);
	    g.dispose();
		
	    return im;
	}
	
	public static void main(String[] args) throws Exception{
		int imgWidth = 300;
		int imgHeight = 60;
		int padding = 6;
		
	    Random random = new Random();
	    List<Date> xData = new ArrayList<>();
	    List<Double> yData = new ArrayList<>();
	    
	    Calendar cal = new GregorianCalendar(2014, 0, 0);
	    for (int i = 0; i < 100; i++) {
	    	Date d = cal.getTime();
	    	xData.add(d);
	    	cal.add(Calendar.DATE, 1);
	    	
	    	yData.add(random.nextDouble());
	    }
	    
		DateDimension xdim = new DateDimension(xData, imgWidth-padding, padding);
	    LinearDimension ydim = new LinearDimension(yData, imgHeight-padding, padding);
	    
		new Sparkle(imgHeight, imgWidth, padding).renderToFile(xdim, ydim, new File("test.png"));;
	}
}
