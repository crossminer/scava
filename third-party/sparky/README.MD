#Sparkle

Sparkle is a lightweight Java library for creating Tufte's sparklines. 

##Motivation
Sparklines are a great way to compactly show a lot of data. There exist one or two JavaScript libraries that can render sparklines (e.g. http://prag.ma/code/sparky/, http://omnipotent.net/jquery.sparkline/), but if you wish to show numerous sparklines on the same web page you need to send all of the data to the client. Which is costly.

Sparkle, therefore, renders the data to PNG format on the server, thereby reducing the amount of data transmitted. 


##Usage
Please note: Sparkle is still in its very early stages and could be optimised in many, many ways.


###Scales
Scales map data to pixels on the canvas. A scale's domain is the data; its range is the canvas. Sparkle comes with two built-in scale types: ```LinearScale``` and ```DateScale```. You can define new scales by extending ```Scale``` and implementing the ```scale()```. The constructor takes the maximum and minimum values of the domain and range. 

```
LinearScale scale = new LinearScale(maxDomain, minDomain, maxRange, minRange);
``` 

###Dimensions
Scales are combined with data into a ```SparkDimension``` which exposes utility methods for querying and scaling the data. The two built in dimensions mirror the scales: ```LinearDimension``` and ```DateDimension```.  The constructor takes the data, and the maximum and minimum values of the range. It creates the scale object internally. [This is ugly and needs fixing.]

```
LinearDimension xdim = new LinearDimension(data, maxRange, minRange);
```

###Customisation
As well as defining the size of the sparkline, you can also customise the colour of the line and of the high and low point markers.

```
Sparkle sparkle = new Sparkle(...);
sparkle.setLineColor(Color.red);
sparkle.setPointColor(Color.blue);
```


###Complete Example
An example of generating an image on disk.

```
// Define the size of the sparkline
int imgWidth = 300;
int imgHeight = 60;
int padding = 6;

// Util for generating data
Random random = new Random();
List<Date> xData = new ArrayList<>();
List<Double> yData = new ArrayList<>();

// Generate random data
Calendar cal = new GregorianCalendar(2014, 0, 0);
for (int i = 0; i < 100; i++) {
	Date d = cal.getTime();
	xData.add(
	cal.add(Calendar.DATE, 1);
   	
	yData.add(random.nextDouble());
}

// Setup the dimensions of the sparkle. We include some padding around the image here
DateDimension xdim = new DateDimension(xData, imgWidth-padding, padding);
LinearDimension ydim = new LinearDimension(yData, imgHeight-padding, padding);
	    
// And render
Sparkle sparkle = new Sparkle(imgHeight, imgWidth, padding);
sparkle.renderToFile(xdim, ydim, new File("test.png"));;
```

Generated image: 

<img src="https://github.com/jrwilliams/sparkle/blob/master/test.png" alt="example sparkline" style="width:150px;height:30px" />

###Use with a Web Server
If you'd like to generate these on a web server, you can use sparkle to generate a byte array. An example for Restlet:

```
@Get
public Representation represent() {
	
	...
	
	DateDimension xdim = new DateDimension(...);
	LinearDimension ydim = new LinearDimension(...);
	byte[] bytes = sparkle.renderToByteArray(xdim, ydim);
			
	return new ByteArrayRepresentation(bytes, MediaType.IMAGE_PNG);
}

```

##Future Plans
The aim of sparkle is to be lightweight and simple, so I don't envisage it becoming much bigger. That said, there are some things that may be needed:

* More scales/dimensions
* Make SparkDimension constructor less ugly
* Remove dimensions all together, and merge functionality into scales?

Suggestions, comments, and contributions are very welcome :)

