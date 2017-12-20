var metvis = { 
	version : "0.0.1"
};

(function() {
	"use strict";
	metvis.Annotation = function(axis, intersect, label) {
		this.axis = axis;
		this.intersect = intersect;
		this.label = label;
	}
	metvis.Table = function(container, vis) {
		self = this;

		self.container = container;

		self.vis = vis;

		this.draw = function() {
			"use strict";

			// Validate vis
			if (self.vis.type != "Table") {
				console.log("Invalid visualisation type. Should be 'Table'. For non-tabular data use metvis.Chart.draw().");
				return;
			}

			// Now draw
			$(self.container).append("<table></table>")
			var table = $(self.container).children();

			// Probably really inefficient...
			var hdr = "<thead>"
			$.each(self.vis.datatable[0], function(key, val) {
				hdr = hdr + "<th>" + key + "</th>";
			});
			hdr = hdr + "</thead>";
			table.append(hdr);

			var bdy = "<tbody>";

			$.each(self.vis.datatable, function(index, value) {
				bdy = bdy + "<tr>";
				$.each(value, function(key, val) {
					bdy = bdy + "<td>" + val + "</td>";
				});
				bdy = bdy + "</tr>";
			});

			bdy = bdy + "</tbody>";
			table.append(bdy);
		}
		return this;
	}
	metvis.Chart = function(container, vis) {
		var self = this;
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
		// Ticks
		self.axis = {};
		self.axis.y = {};
		self.axis.y.ticks = 10;
		self.axis.y.tickLabels = true;
		self.axis.x = {};
		self.axis.x.ticks = 10;
		self.axis.x.tickLabels = true;
		// The legend
		self.legend = {};
		// The margin
		self.margin = {top: 60, right: 20, bottom: 30, left: 50};
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
		// Show hover - set to false if you want to turn off interactivity
		self.interactive = false;
		this.draw = function() {
			"use strict";
			if (self._drawn) {
				console.err("draw() invoked multiple times. Only call once, then use redraw()");
				return;
			}
			self._drawn = true;

			// Add margins to the dimensions
			self.width = self.width - self.margin.left - self.margin.right,
			self.height = self.height - self.margin.top - self.margin.bottom;

			// Format the data
			self._formatData(self.vis);

	        // Define the scales for the data
	        self._updateScales();

			// Setup the axes: TODO infer ticks?
			var xAxis = self._createAxis(self.xScale, "bottom", 10); 	// TODO infer #ticks?
			var yAxis = self._createAxis(self.yScale, "left", 5); 	// TODO infer #ticks
			
			self.axes.push(xAxis);
			self.axes.push(yAxis);

        	// Extract series
        	var ss = self._extractSeries(self.vis);

        	for (var s in ss) {
        		self.series.push(ss[s]);
			}

			self.zoom = d3.behavior.zoom()
			    .x(self.xScale)
			    .y(self.yScale)
			    .scaleExtent([1, 10])
			    .on("zoom", function() {
			    	$(self.container).empty();
				  	self.svg.select(".x.axis").call(self.axes[0]);
				  	self.svg.select(".y.axis").call(self.axes[1]);
				  	self._draw();
			    });

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

			var xAxis = self._createAxis(self.xScale, "bottom", self.axis.x.ticks); 	// TODO infer #ticks?
			var yAxis = self._createAxis(self.yScale, "left", self.axis.y.ticks); 	// TODO infer #ticks

			if (self.vis.timeSeries) {
				xAxis.tickFormat(self.dateAxisFormat);
			}

			// Remove the labels if requested. FIXME: Must be a more elegant solution
			// if (self.axis.x.tickLabels) {
			// 	xAxis.tickFormat('');	
			// }
			// if (self.axis.y.tickLabels) {
			// 	yAxis.tickFormat('');	
			// }

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
		        				return self.yScale(d.intersect);
		        			}
		        		})
	        		.attr("y2", function(d) { 
		        		if (d.axis == "X") {
		        				return self.axes[1].scale().range()[1];
		        			} else {
		        				return self.yScale(d.intersect);
		        			}
		        		});
	        d3.select("#ossplots-annotations").selectAll("text")
	        	.data(self.annotations)
	        	.enter()
	        	.append("text")
		        	.text(function(d) {
		        		return d.label;
		        	})
		        	.attr("class", "metvis-annotation-text")
		        	.attr("x", function (d) {
		        		if (d.axis == "X") {
	        				return self.xScale(d3.time.format(self.dateFormat).parse(d.intersect));
	        			} else {
	        				return self.axes[0].scale().range()[0];
	        			}
		        	})
		        	.attr("y", function (d) {
		        		if (d.axis == "X") {
		        				return self.axes[1].scale().range()[1];
		        			} else {
		        				return self.yScale(d.intersect);
		        			}
		        		})


			if (self.interactive) {
				self.svg.call(self.zoom);
				
				if (self.vis.type === "BarChart") {

				} else {
				    // // To do: multiple series???
				    // var focusPoint = self.svg .append("g").style("display", "none");
				    // focusPoint.append("circle")
				    // 	.style("fill", self.colors)
				    // 	.style("stroke", "lightsteelblue")
				    // 	.attr("r", 4);

				    // var rect = self.svg.append("rect")
				    // 	.attr("width", self.width)
				    // 	.attr("height", self.height)
				    // 	.style("fill", "none")
				    // 	.style("pointer-events", "all")
				    // 	.on("mouseover", function() { focusPoint.style("display",null); })
				    // 	.on("mouseout", function() { focusPoint.style("display","none"); })
				    // 	.on("mousemove", function() {
				    // 		var date = self.xScale.invert(d3.mouse(this)[0]);

				    // 		var _x = d3.mouse(this)[0];
				    // 		var _y = d3.mouse(this)[1];

				    // 		var bisect = d3.bisector(function(d) { return d[self.vis.x]; }).right;
				    // 		var index = bisect(self.vis.datatable, date);
				    // 		if (index != 0) index--;




				    // 		// var x0 = self.xScale.invert(d3.mouse(this)[0]),
				    // 		// 	i = bisect(self.vis.datatable, date, 1)
				    // 		// 	d0 = self.vis.datatable[i-1],
				    // 		// 	d1 = self.vis.datatable[i],
				    // 		// 	d = x0 - d0.date > d1.date - x0 ? d1 : d0;

				    // 		focusPoint.select("circle")
				    // 			.attr("cx", self.xScale(self.vis.datatable[index][self.vis.x]))
				    // 			.attr("cy", self.yScale(self.vis.datatable[index][self.vis.y]))

				    // 	});
			    }
		    }
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

			$(self.container).empty();
			self._draw();
		}

		self.removeAnnotation = function(annotation) {
			"use strict";
			var ind = self.annotations.indexOf(annotation);
			if (ind >= 0) {
				self.annotations.splice(ind, 1);
				$(self.container).empty();
				self._draw();
			}
		}

		self._drawSeries = function(s, col) {
			"use strict";
			if (s.vis.type === "Table") {
				console.err("Attempted to draw a 'Table' series. Use metvis.Table.draw() for tabular data.");
			} else if (s.vis.type === "LineChart") {
				self.svg.append("g").append("path")
					.attr("class", "line")
					.attr("d", s.line(s.vis.datatable))
					.style("stroke-width", 1)
					.style("stroke", col);
			} else if (s.vis.type === "BarChart") {
				var barWidth = 0;

				if (self.vis.timeSeries) {
					var d1 = self.xScale.domain()[0];
					var d2 = self.xScale.domain()[1];
					var days = Math.ceil(d2.getTime() - d1.getTime()) / (1000 * 3600 * 24);

					barWidth = (self.width - self.margin.left - self.margin.right) / (days+1);
				} else { // Ordinal or linear
					barWidth = (self.width - self.margin.left - self.margin.right) / (self.vis.datatable.length);
				}
				

				var bar = self.svg.selectAll("rect")
		            .data(s.vis.datatable)
		            .enter()
		            .append("rect")
			            .attr("x", function(d){
			            	if (vis.timeSeries) return self.xScale(d[s.vis.x]) - (barWidth/2);
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
			            		return barWidth;
			        		}
			        	})	
			            .attr("height", function (d) {
			            	if (d[s.vis.y] < 0) {
			            		return Math.abs(self.yScale(0) - self.yScale(d[s.vis.y]));
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

	            if (self.interactive) {
	            	bar.on("mouseover", function (e) { 
	            		var msg = e[vis.x]+ ": " + e[vis.y];
	            		var txt = self.svg.append("text")
	            			.text(msg)
	            			.attr("x", self.width/2)
	            			.attr("y", self.height+self.margin.bottom-10)
	            			.attr("class", "metvis data-hover-label")
	            			.style("text-anchor", "middle");
	            	}).on("mouseout", function(e) {
	            		self.svg.select(".metvis.data-hover-label").remove();
	            	})
	            }

			} else if (s.vis.type === "ScatterChart") {
				self.svg.append("g")
					.selectAll("circle")
					.data(s.vis.datatable)
					.enter()
					.append("circle")
						.attr("r", 2)
						.attr("cx", function (d) {
							return self.xScale(d[s.vis.x]);
						})
						.attr("cy", function (d){
							return self.yScale(d[s.name]);
						})	
						.style("fill", col)
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
				var xExtent = d3.extent(self.vis.datatable, function(d) {return d[vis.x]; });
				var nxExtent = [d3.time.day.offset(xExtent[0], -1), d3.time.day.offset(xExtent[1], 1)]

				self.xScale = d3.time.scale().rangeRound([0, self.width]);
				self.xScale.domain(nxExtent);
				// self.xScale.nice();
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

				// if "series" is defined. Filter the data into separate vis specs
				if (vis.series) {

					var vises = {};
					vis.datatable.forEach(function(d) { 
						var s = d[vis.series];
						if (!vises[s]) {
							vises[s] = [];
							var newVis = {};

							for (var key in vis) { // Clone the original vis (except for the datatable)
								if (key != "datatable") {
									newVis[key] = vis[key];
								}
							}
							newVis.parentVis = vis; // Keep track so it can be removed when parent is removed
							newVis.datatable = [];
							vises[s] = newVis;
						}
						vises[s].datatable.push(d); 
					});

					var series = [];
					for (var key in vises) {
						var v = vises[key];

						var l = (function(vi, value) {
			                    return d3.svg.line()
			                        .x(function(d) { return self.xScale(d[vi.x]) })
			                        .y(function(d) { return self.yScale(d[vi.y]) });
			                })(v, ys);

		        		var ser = {
		        			line : l,
		        			vis : v,
		        			name : key
		        		}
		        		series.push(ser);
					}
					return series;
				} else {
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
	        	}
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

			$(self.container).empty();

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

				var toRemove = [];
				for (var s in self.series) {
					if (self.series[s].vis === vis || self.series[s].vis.parentVis === vis) {
						toRemove.push(self.series[s]);
					}
				}
				if (toRemove.length != 0) {
					for (var r in toRemove) {
						var ind = self.series.indexOf(toRemove[r]);
						self.series.splice(ind, 1);
					}
				}

			}
			self._draw();
		}

		// Iterates through data to ensure plot-able type
		self._formatData = function(vis) {
			"use strict";

			var parseDate = d3.time.format(self.dateFormat).parse;
			vis.datatable.forEach(function(d) { 
	            if (vis.timeSeries === true && typeof d[vis.x] == 'string') { // The type check avoids re-parsing data (as the assignment is destructive)
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
