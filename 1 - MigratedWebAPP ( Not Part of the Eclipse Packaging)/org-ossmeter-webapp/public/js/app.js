var app = {
	loggedIn : false,
	grid : {
		sparks : [],
		notifications : [],
		eventGroups : []
	},
	tooltipOptions : { 
		delay: { "show": 500, "hide": 100 }
	},
	popoverOptions : { 
		delay: { "show": 100, "hide": 1000 },
		template: '<div class="popover" role="tooltip"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>',
		content: function() {
			return $("#notification_popover_content").html();
		}
	},
	compare : {
		projects : [],
		metrics : ["bugs", "averageSentiment", "cumulativeComments"]
	},
	qualityModel : {
		categories : [
			{ 
				name : "Source Code",
				factoids : [],
				metrics : []
			},{ 
				name : "Communication Channels",
				factoids : [],
				metrics : []
			},{ 
				name : "Bug Tracking Systems",
				factoids : ["cocomo"],
				metrics : ["bugOpenTime", "sentimentAtThreadEnd", "averageSentiment", "cumulativeComments", "cumulativeBugs", "invalidBugs", "patches", "bugs", "worksForMeBugs", "resolvedClosedBugs", "nonResolvedClosedBugs", "sentimentAtThreadBeggining", "cumulativePatches", "bugOpenTime-bugs", "wontFixBugs", "duplicateBugs", "comments", "commitsovertimeline", "fixedBugs"]
			}
		]
	}
}

// https://learn.jquery.com/using-jquery-core/faq/how-do-i-select-an-element-by-an-id-that-has-characters-used-in-css-notation/
function jq( myid ) {
    // return "#" + myid.replace( /(:|\.|\[|\]\s)/g, "\\$1" );
    return "#" + jqe(myid);
}

function jqe(myid) {
	return myid.replace( /(:|\.|\[|\])/g, "\\$1" );//replace(/\s[`~!@#$%^&*()_|+\-=?;:'",.<>\{\}\[\]\\\/]/gi, '');
}

$(function() {
	$(".tip").tooltip(app.tooltipOptions);
	$(".pop").popover(app.popoverOptions);
	$.cookie.json = true;
	$.cookie.defaults.path = "/";
	$('.collapse').collapse()
	// TODO: not working correctly
	//applyMoreLessDescription();
});

// Credit: http://shakenandstirredweb.com/240/jquery-moreless-text
function applyMoreLessDescription() {
// The height of the content block when it's not expanded
var adjustheight = 100;
// The "more" link text
var moreText = "+  More";
// The "less" link text
var lessText = "- Less";

// Sets the .more-block div to the specified height and hides any content that overflows
$(".more-less .more-block").css('height', adjustheight).css('overflow', 'hidden');

// The section added to the bottom of the "more-less" div
$(".more-less").append('<p class="continued">[&hellip;]</p><a href="#" class="adjust"></a>');

$("a.adjust").text(moreText);

$(".adjust").toggle(function() {
		$(this).parents("div:first").find(".more-block").css('height', adjustheight).css('overflow', 'hidden');
		$(this).parents("div:first").find("p.continued").css('display', 'block');
		$(this).text(moreText);
		$(this).parents("div:first").find(".more-block").css('height', 'auto').css('overflow', 'visible');
		// Hide the [...] when expanded
		$(this).parents("div:first").find("p.continued").css('display', 'none');
		$(this).text(lessText);
	}, function() {
});
}

function getNotification(elem, projectid, projectname, metricid, metricname) {
	jsRoutes.controllers.Application.profileNotification(projectid, projectname, metricid, metricname).ajax()
		.success(function(result) {
			$('#exampleModal .modal-body').html(result)

			$('<input>').attr({
			    type: 'hidden',
			    id: 'notiform-elem',
			    name: 'elem',
			    value: elem
			}).appendTo('#notiform');

			$('<input>').attr({
			    type: 'hidden',
			    id: 'project.id',
			    name: 'project.id',
			    value: projectid
			}).appendTo('#notiform');

			$("#notiform-project-name").text(projectname);
			$("#notiform-metric-name").text(metricname);

			$('<input>').attr({
			    type: 'hidden',
			    id: 'metric.id',
			    name: 'metric.id',
			    value: metricid
			}).appendTo('#notiform');

			$('<input>').attr({
			    type: 'hidden',
			    id: 'project.name',
			    name: 'project.name',
			    value: projectname
			}).appendTo('#notiform');

			$('<input>').attr({
			    type: 'hidden',
			    id: 'metric.name',
			    name: 'metric.name',
			    value: metricname
			}).appendTo('#notiform');

			$('#exampleModal').modal('show');

		}).error(function(result) {
			console.log(result)
		});
}

function clearNotificationWindow() {
	$('#exampleModal .modal-body').html("")
	$('#exampleModal').modal('hide');
}

function submitUpdateNotification() {

	var toPost = $("#notiform").serialize();

	jsRoutes.controllers.Account.createNotification().ajax({
			data : toPost
		})
		.success(function(result) {
				
			// Set the elem to be active... TODO
			// var id = "noti-" + config.projectid + "-" + data.id;

			var elem = $("#notiform-elem").val();
			$(jq(elem)).addClass("active")


			$('#exampleModal .modal-body').html('<div class="alert alert-success" role="alert">Success!</div>')
			
			setTimeout(function() {$('#exampleModal').modal('hide')}, 1000) ;
		}).error(function(result) {
			console.log(result)
		});
}

function updateNotification(elem, projectid, metricid, value) {
	var id = "#" + elem;
	var value = $(id + "-value");
	var aboveThreshold = $(id + "-aboveThreshold");


	jsRoutes.controllers.Account.updateNotification(projectid, metricid, value, aboveThreshold
		).ajax().success(function(result) {
			console.log("Created notification!")
	}).error(function(result){
		console.log("Error, unable to create notification.");
	});
}

function toggleSpark(elem, projectid, projectname, metricid, metricname) {
	
	jsRoutes.controllers.Account.watchSpark(projectid, metricid, projectname, metricname
		).ajax().success(function(result) {
			var _ind = getIndexOfSpark(projectid, metricid);//$.inArray(metricid, app.grid.sparks);
			if (_ind == -1) {
				var sp = {"project" : projectid, "metric" : metricid}
				app.grid.sparks.push(sp);
				$(jq(elem)).addClass("active");
				console.log("addeding active to " + elem);
			} else {
				app.grid.sparks.splice(_ind, 1);
				$(jq(elem)).removeClass("active");
				console.log("removing active from " + elem);
			}
		}).error(function(result) {
			console.log("Error, unable to toggle spark watch.");
		});
}

function getIndexOfSpark(project, metric) {

	for (var i =0; i < app.grid.sparks.length; i++) {
		var spark = app.grid.sparks[i];
		if (spark.project === project && spark.metric === metric) {
			return i;
		}
	}
	return -1;
}

function getIndexOfNotification(project, metric) {
	for (var i =0; i < app.grid.notifications.length; i++) {
		var noti = app.grid.notifications[i];
		if (noti.project === project && noti.metric === metric) {
			return i;
		}
	}
	return -1;
}

function drawPlot(container, projectid, metricid) {

	var unsparkId = 'unspark-'+projectid+metricid;
	var str = '<div class="col-md-6 box">'
		+ '<div class="row" id="legend-'+unsparkId+'"></div>'
		+ '<div class="row" id="'+unsparkId+'"></div>'
		+'</div>';
	$(jq(container)).append(str);

	(function(cont) {
		$.getJSON(getApi() + "/projects/p/" + projectid + "/m/" + metricid, function(vis) {
			if (vis.datatable && vis.datatable.length > 0) {
				var chart = new metvis.Chart(jq(cont), vis);
				chart.height = 150;
				chart.margin.top = 10;
				chart.axis.x.tickLabels = true;
		        chart.axis.y.tickLabels = true;
		        chart.axis.y.ticks = 3;
		        chart.axis.x.ticks = 5;
				chart.draw();
				//TODO:legend

				var l = $(jq("legend-"+cont));
		        l.empty();

		        l.append('<p>'+vis.name+'</p>');
		        var _st = '<ul class="list-inline">'; 

		        for (var s in chart.series) {
		            _st = _st + '<li><div class="legend-box" style="background-color:' + chart.colors(s) + '"></div>' + chart.series[s].name + '</li>';
		        }
		        _st = _st + "</ul>";
		        l.append(_st);
			} else {
				console.log("metric " + metricid + " has no data");
			}

		}).error(function(error){
			console.log(error);
		});
	})(unsparkId);
	
}

function drawSparkTable(config) {
	"use strict";

	var url = getApi() + "/projects/p/" + config.projectid + "/s/" + config.metriclist;
	if (config.querystring) {
		url = url + "?" + config.querystring;
	}

    $.getJSON(url, function (result) {
        // Convert into an array if only one spark was requested
        if( Object.prototype.toString.call( result ) === '[object Object]' ) {
            result = [result];
        }

        // Draw a new row for each spark
        for (var r in result) {
                var data = result[r];

	            // Check for errors - TODO: handle better
                if (data.status === "error") {
                    // console.log(data);
                    console.log("Unable to load sparky '" + data.request.metricId + "': " + data.msg);
                    
                    if (config.drawNonSparkable) {

                    	// console.log("unsparking")
                    	// var unspark = new Unsparkable(data.request.project, data.request.metricId);
                    	// config.unsparkableViewModel.unsparks.push(unspark);

                    	drawPlot(config.drawNonSparkableContainer, data.request.projectId, data.request.metricId)
                    }
                    continue; 
                }

                // header row
                if ($("#" + config.sparktable + " > thead > tr").length == 0) { 
	                var hdr = "<tr>";
	                if (config.drawName) {
	                	hdr = hdr + "<th>metric</th>";
	                }
	                hdr = hdr + "<th>" + data.firstDate + "</th>" +
		                        "<th style=\"min-width:120px;max-width:120px\">" + data.months + " months</th>" +
		                        "<th>" + data.lastDate + "</th>" +
		                        "<th>low</th>" +
		                        "<th>high</th>"

	                hdr = hdr + "</tr>"
	                $("#" + config.sparktable + " > thead:last").append(hdr);

	                if (config.toolkittable) {
                		$("#" + config.toolkittable + " > thead:last").append("<tr><th>toolkit</th></tr>");
                	}
	            }

                if (config.toolkittable) {
                	var tools = '<a href="javascript:grabMetricData(\''+config.projectid+'\',\''+data.id+'\')"><span class="glyphicon glyphicon-plus tip" data-toggle="tooltip" data-placement="bottom" title="Add metric to plot"></span></a>';
                	tools = tools + ' <a href="javascript:showJustOneMetric(\''+config.projectid+'\',\''+data.id+'\')"><span class="glyphicon glyphicon-stats tip" data-toggle="tooltip" data-placement="bottom" title="View metric"></span></a>';
                	if (app.loggedIn) {
                		var a = ""; 
                        if (getIndexOfSpark(config.projectid, data.id) != -1) {
                            a = "active"; 
                        } 

                        var a2 = "";
                        if (getIndexOfNotification(config.projectid, data.id) != -1) {
                            a2 = "active"; 
                        }

                		var watchId = "watch-spark-" + config.projectid + "-" + data.id;
                		var notiId = "noti-" + config.projectid + "-" + data.id;
                		tools = tools + ' <a href="javascript:toggleSpark(\''+watchId+'\',\''+config.projectid+'\', \''+config.projectname+'\', \''+data.id+'\', \''+data.name+'\')"><span id="'+watchId+'" class="glyphicon glyphicon-eye-open spark-watch tip ' + a + '" data-toggle="tooltip" data-placement="bottom" title="Add spark to dashboard"></span></a>';
                		tools = tools + ' <a href="javascript:getNotification(\''+notiId+'\', \''+config.projectid+'\', \''+config.projectname+'\', \''+data.id+'\', \''+data.name+'\')"><span id="'+notiId+'" class="glyphicon glyphicon-bell spark-watch tip '+a2+'" data-toggle="tooltip" data-placement="bottom" title="Create/edit notification"></span></a>';
                	}

                	$(jq(config.toolkittable) + " > tbody:last").append("<tr><td>" + tools + "</td></tr>");
                }

                var bdy = "<tr>";

                if (config.drawName) {
					bdy = bdy + "<td>" + data.name + "</td>";                	
                }

				bdy = bdy + "<td>" + Math.round(data.first * 100) / 100  +
                    "</td><td><img class=\"spark\" src=\"" + getApi() + data.spark + "\" />" +  
                    "</td><td>" + Math.round(data.last * 100) / 100  + 
                    "</td><td>" + Math.round(data.low * 100) / 100 + 
                    "</td><td>" + Math.round(data.high * 100) / 100 + "</td>";

                bdy = bdy + "</tr>";
                $("#" + config.sparktable + " > tbody:last").append(bdy);
            }
            $(".tip").tooltip(app.tooltipOptions);
            $(".pop").popover(app.popoverOptions);
    });
}

function toOssmeterDateString(date) {

}

function fixHeights(table1, table2) {
    $("#" +table1+ " > tbody > tr").each(function(index, value) {
        var h = $(this).height();
        $("#" + table2 + " > tbody > tr:eq("+index+")").css('height', h+'px');
    });
}

function addProjectToComparison(projectid, projectname) {
	"use strict";
	var projects = $.cookie("cccc");

	if (!projects) {
		projects = [];
	}

	var ids = $.map(projects, function(a) { return a.id; });

	if ($.inArray(projectid, ids) == -1) {
		projects.push({
			id:projectid,
			name:projectname
		});
		$.cookie("cccc", projects);
	}
	$("#compare-bar").fadeIn(500);
	drawCompareBar();
}

function closeCompareBar() {
	$("#compare-bar").fadeOut(500);
}

function drawCompareBar(warn) {
	"use strict";
	$("#compare-bar-projects").empty();
	var projects = $.cookie("cccc");
	for (var p in projects) {
		$("#compare-bar-projects").append(
			'<div class="col-md-3"><h3>' + projects[p].name + '</h3></div>'
			);
	}
}

function drawSparklineTable(projectname, table, metriclist) {
         $.get(getApi() + "/projects/p/"+projectname+"/s/"+metriclist.join("+"), function (result) {
            for (var r in result) {
                var data = result[r];

                if (r == 0) { // Set up the header
                    $(table + " > thead:last").append(
                        "<tr><th>metric</th>" +
                        "<th>" + data.firstDate + "</th>" +
                        "<th>" + data.months + " months</th>" +
                        "<th>" + data.lastDate + "</th>" +
                        "<th>low</th>" +
                        "<th>high</th></tr>");
                }

                $(table + " > tbody:last").append("<tr><td title=\"" + data.description + "\">" + data.name + 
                    "</td><td>" + Math.round(data.first * 100) / 100  +
                    "</td><td><img class=\"spark\" src=\"" + getApi() + data.spark + "\" />" +  
                    "</td><td>" + Math.round(data.last * 100) / 100  + 
                    "</td><td>" + Math.round(data.low * 100) / 100 + 
                    "</td><td>" + Math.round(data.high * 100) / 100 + "</td></tr>");
            }
        });
    }

function compareProjects() {
	var metrics = app.compare.metrics;
	// Sparks
	$.get(getApi() + "/projects/p/@project.getShortName()/s/"+metrics.join("+"), function (result) {
		for (var r in result) {
            var data = result[r];


        }
	});
}

function getOneYearAgoDateString() {
	var d = new Date();
	return (d.getFullYear()-1) + "" + ("0" + (d.getMonth()+1)).slice(-2) + "" + ("0" + d.getDate()).slice(-2);
}

/*
	Taken from: http://stackoverflow.com/questions/10599933/convert-long-number-into-abbreviated-string-in-javascript-with-a-special-shortn 
*/
function abbreviateNumber(value) {
    var newValue = value;
    if (value >= 1000) {
        var suffixes = ["", "k", "m", "b","t"];
        var suffixNum = Math.floor( (""+value).length/3 );
        var shortValue = '';
        for (var precision = 2; precision >= 1; precision--) {
            shortValue = parseFloat( (suffixNum != 0 ? (value / Math.pow(1000,suffixNum) ) : value).toPrecision(precision));
            var dotLessShortValue = (shortValue + '').replace(/[^a-zA-Z 0-9]+/g,'');
            if (dotLessShortValue.length <= 2) { break; }
        }
        if (shortValue % 1 != 0)  shortNum = shortValue.toFixed(1);
        newValue = shortValue+suffixes[suffixNum];
    } else if (value % 1 != 0){
    	newValue = value.toFixed(2);
    }
    return newValue;
}

function drawSpiderChart(container, factoids, config) {

	// if (!config) {
	// 	config = {
	// 		w: 150,
	// 		h: 150,
	// 		maxValue: 4,
	// 		levels: 4,
	// 		ExtraWidthX:200
	// 	};
	// }

	var d = [];

	for (var f in factoids) {
		var stars = 1;

		if (!factoids[f].factoid) continue;

		switch (factoids[f].stars) {
			case "FOUR": stars = 4; break;
			case "THREE": stars = 3; break;
			case "TWO": stars = 2; break;
		}

        var factoid_name;
        if(factoids[f].name.length > 15 && factoids[f].name.split(" ").length > 2) {
            var label_terms = factoids[f].name.split(" ");
            factoid_name = "..." +  label_terms[label_terms.length-2] + " " + label_terms[label_terms.length-1];
        }
        else {
            factoid_name = factoids[f].name;
        }

		var ax = { axis: factoid_name, value: stars } ;
		d.push(ax);
	}

	RadarChart.draw(container, [d], config);
}
