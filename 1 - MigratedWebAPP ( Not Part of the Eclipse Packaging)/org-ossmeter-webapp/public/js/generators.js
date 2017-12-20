function DateGenerator(start) {
	this.current = start;
	this.next = function() {
		var format = d3.time.format("%Y%m%d");
		this.current.setDate(this.current.getDate() + 1);
		return format(this.current);
	}
}

function IntGenerator(start, f) {
	this.current = start;
	this.f = f;
	this.next = function() {
		this.current = f(this.current);
		return this.current;
	}
}


function OrdinalGenerator() {
	this.currCat = -1;
	this.cats = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P"];
	this.next = function() {
		if (this.currCat > this.cats.length) return null;
		return this.cats[this.currCat++];
	}
}

function NullGenerator() {
	this.next = function() { }
}

RandomIntFunction = function (input) {
	return Math.floor(Math.floor(Math.random() * 300));
}
LinearFunction = function (input) {
	return Math.floor(input + (Math.floor(Math.random() * 300) ));
}

function getGenerator(col) {
	var gen;
	if (col._gen_generator) {
		if (col._gen_generator === "ordinal") {
			var cats = col._gen_categories;
			gen = new OrdinalGenerator();
		} else if (col._gen_generator === "linear") {
			gen = new IntGenerator(Math.floor(Math.random() * 250) + 200, LinearFunction);
		} else if (col._gen_generator === "randomint") {
			gen = new IntGenerator(5, RandomIntFunction)
		}
	} else { 
		gen = new IntGenerator(Math.floor(Math.random() * 250) + 200, LinearFunction);
	}
	return gen;
}


function generateTestData(vis, numEntries) {
	var visId = vis.name;
	var type = vis.type;
	var x = vis.x;
	var y = vis.y;

	var generators = [];
	var testData = [];

	for (c in vis.datatable.cols) {
		var col = vis.datatable.cols[c];

		// Special case
		if (col.field === "$__date") {
			var dg = new DateGenerator(new Date(2012, 01, 01));
			generators.push(dg);
			continue;
		} 

		// X
		if (col.name === x) {
			var g = getGenerator(col);
			generators.push(g);
			continue;
		}

		// Y
		if (typeof y === 'string') {
			if (col.name === y) {
				var g = getGenerator(col);
				generators.push(g);
				continue;
			}
		} else { // Arrays
			if ($.inArray(col.name, y) != -1) {
				var g = getGenerator(col);
				generators.push(g);
				continue;
			}
		}

		// Still here? I guess you're not needed
		generators.push(new NullGenerator());
	}

	for (var i = 0; i < numEntries; i++) {
		var obj = {};
		var end = false;
		for (col in vis.datatable.cols) {
			var c = vis.datatable.cols[col];
			var g = generators[col];
			
			var data = g.next();
			if (data === null) { end = true; break; }
			obj[c.name] = g.next();
		}	
		if (end) break;
		testData.push(obj);
	}
	return testData;
}
