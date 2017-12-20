var ossplots = { 
	version : "0.0.1"
};

(function() {
	"use strict";

	ossplots.Annotation = function(axis, intersect, label) {
		this.axis = axis;
		this.intersect = intersect;
		this.label = label;
	}

	ossplots.Chart = function(container, vis) {
		self = this;
		// The HTML element to contain the plot
		self.container = container;
		// The base visualisation
		self.vis = vis;
		// List of visualisations in the chart
		self.vises = [vis];
		// List of all plotted series
		self.series = [];
		// The number of visualisations that can be included on the same chart
		self.maximumVisualisations = 3;
		// The axes
		self.axes = [];
		// The scales
		self.xScale = null;
		self.yScale = null;
		// The legend
		self.legend = {};
		// The margin
		self.margin = {top: 30, right: 20, bottom: 30, left: 50};
		// String format of date fields
		self.dateFormat = "%Y%m%d";
		// Colours for series
		self.colors = d3.scale.category20();
		// The group that contains all of the chart's objects
		self._g;
		// The SVG
		self.svg;
		// List of annotations to include on the plot
		self.annotations = [];
		// To stop people calling draw() multiple times
		self._drawn = false;
		// The default dimensions
		self.width = $(container).width(); 
		self.height = $(container).height(); 
		// The format for date labels: http://bl.ocks.org/mbostock/4149176
		// self.dateAxisFormat = d3.time.format.multi([
		// 	[".%L", function(d) { return d.getMilliseconds(); }],
		// 	[":%S", function(d) { return d.getSeconds(); }],
		// 	["%I:%M", function(d) { return d.getMinutes(); }],
		// 	["%I %p", function(d) { return d.getHours(); }],
		// 	["%a %d", function(d) { return d.getDay() && d.getDate() != 1; }],
		// 	["%b %d", function(d) { return d.getDate() != 1; }],
		// 	["%B", function(d) { return d.getMonth(); }],
		// 	["%Y", function() { return true; }]
		// ]);

		// Draws the base visualisation only
		// FIXME: NO! It should draw all!
		this.draw = function() {
			"use strict";
			if (self._drawn) {
				console.err("draw() invoked multiple times. Only call once, then use redraw()");
				return;
			}
			self._drawn = true;

			// Add margins to the dimensions
			self.width = $(self.container).width() - self.margin.left - self.margin.right,
			self.height = self.height - self.margin.top - self.margin.bottom;

			// Format the data
			self._formatData(self.vis);

	        // Define the scales for the data
	        self._updateScales();

			// Setup the axes: TODO infer ticks?
			var xAxis = self._createAxis(self.xScale, "bottom", 10); 	// TODO infer #ticks?
			var yAxis = self._createAxis(self.yScale, "left", 5); 	// TODO infer #ticks
			
			if (self.vis.timeSeries) {
				// xAxis.tickFormat(self.dateAxisFormat); // FIXME
			}

			self.axes.push(xAxis);
			self.axes.push(yAxis);

        	// Extract series
        	var ss = self._extractSeries(self.vis);

        	for (var s in ss) {
        		self.series.push(ss[s]);
			}

			// Now draw it :)
        	self._draw();

		} // end ossplots.chart.draw

		// The internal drawing function. Actually performs the draw
		self._draw = function() {
			"use strict";

			// Create the SVG
			self.svg = d3.select(container)
				.append("svg")
					.attr("width", self.width + self.margin.left + self.margin.right)
					.attr("height", self.height + self.margin.top + self.margin.bottom)
				.append("g")
					.attr("transform", "translate(" + self.margin.left + "," + self.margin.top + ")");
			
        	// Draw gridlines
        	self._drawGridlines(self.xScale, self.yScale);

        	// Draw the series
        	for (var s in self.series) {
	        	self._drawSeries(self.series[s], self.colors(s));
			}

			var xAxis = self._createAxis(self.xScale, "bottom", 10); 	// TODO infer #ticks?
			var yAxis = self._createAxis(self.yScale, "left", 5); 	// TODO infer #ticks
			
			if (self.vis.timeSeries) {
				xAxis.tickFormat(self.dateAxisFormat);
			}

			// Add the X Axis: TODO generalise
	        self.svg.append("g")
	            .attr("class", "x axis")
	            .attr("transform", "translate(0," + self.height + ")")
	            .call(xAxis);//self.axes[0]);  

	        // Add the Y Axis: TODO generalise
	        self.svg.append("g")
	            .attr("class", "y axis")
	            .call(yAxis);//self.axes[1]);

	        // Draw annotations
	        self.svg.append("g")
				.attr("id", "ossplots-annotations")
				.attr("class", "threshold-line");
			// d3.select("#ossplots-annotations").selectAll("line").remove();
        	d3.select("#ossplots-annotations").selectAll("line")
	        	.data(self.annotations)
	        	.enter()
	        	.append("line")
	        		.attr("x1", function(d) { 
	        			if (d.axis == "X") {
	        				return self.xScale(d3.time.format(self.dateFormat).parse(d.intersect));
	        			} else {
	        				return self.axes[0].scale().range()[0];
	        			}
	        		})
	        		.attr("x2", function(d) { 
	        			if (d.axis == "X") {
	        				return self.xScale(d3.time.format(self.dateFormat).parse(d.intersect));
	        			} else {
	        				return self.axes[0].scale().range()[1];
	        			}
	        		})
	        		.attr("y1", function(d) { 
	        		if (d.axis == "X") {
	        				return self.axes[1].scale().range()[0];
	        			} else {
	        				return 0;
	        			}
	        		})
	        		.attr("y2", function(d) { 
	        		if (d.axis == "X") {
	        				return self.axes[1].scale().range()[1];
	        			} else {
	        				return 0;
	        			}
	        		});
		} // end ossplots.chart._draw

		self._createAxis = function(scale, orient, ticks) {
			"use strict";
			return  d3.svg.axis().scale(scale).orient(orient).ticks(ticks);
		} // end ossplots.chart._createAxis

		self.addAnnotation = function(annotation) {
			"use strict";
			// TODO: Validate the annotation
			if (typeof annotation.axis === 'undefined' || 
				typeof annotation.intersect === 'undefined' ||
				typeof annotation.label === 'undefined') {
				throw "Invalid annotation";
			}

			self.annotations.push(annotation);

			self.redraw();
		}

		self._drawSeries = function(s, col) {
			"use strict";
			if (s.vis.type === "LineChart") {
				self.svg.append("g").append("path")
					.attr("class", "line")
					.attr("d", s.line(s.vis.datatable))
					.style("stroke-width", 2)
					.style("stroke", col);
			} else if (s.vis.type === "BarChart") {
				self.svg.selectAll("rect")
		            .data(s.vis.datatable)
		            .enter()
		            .append("rect")
		            .attr("x", function(d){
		                return self.xScale(d[s.vis.x]);
		            })
		            .attr("y", function (d) {
		            	if (d[s.vis.y] < 0) {
							return self.yScale(0);// Negative values need drawing from 0
		            	} else {
			                return self.yScale(d[s.vis.y]);
			            }
		            })
		            .attr("width", function (d) {
		            	if (s.vis.categorical) {
		            		return self.xScale.rangeBand();
		            	} else {
		            		return self.width / s.vis.datatable.length - 1;
		        		}
		        	})	
		            .attr("height", function (d) {
		            	if (d[s.vis.y] < 0) {
		            		return Math.abs(self.yScale(0) - self.yScale(d[s.vis.y]));//self.height - self.yScale(d[s.vis.y]);	
		            	} else {
							return self.height - self.yScale(d[s.vis.y]);
		            	}		                
		            })
		            .attr("fill", function (d) {
		            	if (d[s.vis.y] < 0) {
		            		return "red";
		            	}
		            	return col;
		            })
		            .attr("opacity", 0.7)
		            .attr("title", function(d) {
		                return d[s.vis.x];
		            })
			}	
		} // end ossplots.chart._drawSeries

		self._updateScales = function() {
			"use strict";
			// Validate the vis
			for (var v in self.vises) {
				if (self.vises[v].timeSeries && self.vises[v].categorical) {
					throw "Data cannot be both categorical and time series."
				}
			}

			// X Scale
			var xs = [];
			for (var v in self.vises) {
				var vis = self.vises[v];
				vis.datatable.forEach(function(d){
					xs.push(d[vis.x]);
				})
			}

			if (self.vis.timeSeries) {
				self.xScale = d3.time.scale().range([0, self.width]);
				self.xScale.domain(d3.extent(xs));
			} else if (self.vis.categorical) {
				self.xScale = d3.scale.ordinal().rangeRoundBands([0, self.width], .1);
				self.xScale.domain(xs);
			} else {
				self.xScale = d3.scale.linear().range([0, self.width]);
				self.xScale.domain(d3.extent(xs));
			}

			// Y Scale
			var ys = [];
			for (var v in self.vises) {
				var vis = self.vises[v];
				if (typeof vis.y === 'string') {
					vis.datatable.forEach(function(d){
						ys.push(d[vis.y]);
					});
	            } else {
	            	vis.datatable.forEach(function(d){
						for (var y in vis.y) {
	                		ys.push(d[vis.y[y]]);
	                	}
					})
	            }
			}

			self.yScale = d3.scale.linear().range([self.height, 0]);
			if (self.vis.timeSeries) {
				self.yScale.domain(d3.extent(ys)).nice();
			} else if (self.vis.categorical) {
				self.yScale.domain([Math.min(0, d3.min(ys)), d3.max(ys)]);
			} else {
				self.yScale.domain(d3.extent(ys)).nice();
			}
		} // end ossplots.chart._createScales

		self._extractSeries = function(vis) {
			"use strict";
			if (typeof vis.y === 'string') {
        		var l = (function(vi, value) {
	                    return d3.svg.line()
	                        .x(function(d) { return self.xScale(d[vi.x]) })
	                        .y(function(d) { return self.yScale(d[vi.y]) });
	                })(vis, ys);

        		var series = {
        			line : l,
        			vis : vis,
        			name : vis.y
        		}

        		return [series];
        	} else {
        		var ss = [];
        		for (var ys in vis.y) {
        			var l = (function(vi, value) {
	                    return d3.svg.line()
	                        .x(function(d) { return self.xScale(d[vi.x]) })
	                        .y(function(d) { return self.yScale(d[vi.y[value]]) });
	                })(vis, ys);

        			var series = {
	        			line : l,
	        			vis : vis,
	        			name : vis.y[ys]
	        		}
	        		ss.push(series);
        		}
        		return ss;
        	}
		} // end ossplots.chart._extractSeries

		// Adds another dataset to the chart. Checks if the X-axes are
		// compatible. Will create a new Y axis if necessary.
		self.addVis = function(vis) {
			"use strict";
			// TODO Check axes compatibility
			// TODO Recalculate domain of scales based on ALL data points
			if (self.vis.timeSeries != vis.timeSeries ||
				self.vis.categorical != vis.categorical) {
				throw "Incompatible data types, cannot overlay data."
			}

			$(container).empty();

			if (self.vises.indexOf(vis) === -1) {
				self.vises.push(vis);
				self._formatData(vis);
				self._updateScales();

				var ss = self._extractSeries(vis);
				for (var s in ss) {
					self.series.push(ss[s]);
					self._drawSeries(ss[s], "red");
				}
				
			}
			self._draw();
		} // end ossplots.chart.addData

		self.removeVis = function(vis) {	
			"use strict";
			$(container).empty();

			var ind = self.vises.indexOf(vis);

			if (ind != -1) {
				self.vises.splice(ind, 1);
				self._updateScales();

				var toRemove = -1;
				for (var s in self.series) {
					if (self.series[s].vis === vis) {
						toRemove = s;
						break;
					}
				}

				if (toRemove != -1) {
					self.series.splice(toRemove, 1);
				}

			}
			self._draw();
		}

		// Iterates through data to ensure plot-able type
		self._formatData = function(vis) {
			"use strict";

			var parseDate = d3.time.format(self.dateFormat).parse;
			vis.datatable.forEach(function(d) { 
	            if (vis.timeSeries === true && typeof d[vis.x] != 'object') {
	                d[vis.x] = parseDate(d[vis.x]);
	            } else if (!vis.categorical === true) {
	                d[vis.x] = +d[vis.x];            
	            }
	            if (typeof vis.y === 'string') {
	                d[vis.y] = +d[vis.y];
	            } else {
	                for (var ys in vis.y) {
	                    d[vis.y[ys]] = +d[vis.y[ys]];
	                }
	            }
	        }); 
		} //end ossplots.chart._formatData

		self._drawGridlines = function(xScale, yScale) {
			"use strict";
			self.svg.selectAll("line.y")
	          .data(yScale.ticks(5))
		          .enter().append("line")
		          .attr("class", "y grid")
		          .attr("x1", 0)
		          .attr("x2", self.width)
		          .attr("y1", yScale)
		          .attr("y2", yScale);
		} // end ossplots.chart._drawGridlines


		return this;
	} // end ossplots.chart
})();
