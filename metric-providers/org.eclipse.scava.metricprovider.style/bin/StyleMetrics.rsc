@license{
  Copyright (c) 2017 Centrum Wiskunde & Informatica
  
  This program and the accompanying materials are made
  available under the terms of the Eclipse Public License 2.0
  which is available at https://www.eclipse.org/legal/epl-2.0/
  
  SPDX-License-Identifier: EPL-2.0
}
module StyleMetrics

import org::eclipse::scava::metricprovider::MetricProvider;

extend lang::java::style::StyleChecker;

import ValueIO;
import IO;
import Map;
import Set;
import List;
import DateTime;
import String;
import util::Math;
import analysis::statistics::Inference;
import analysis::statistics::Frequency;

alias Table = rel[str group, str kind, loc file, int line];

// some reusable metric utility functions:
private int percentageOfFilesWithViolations(rel[Language, loc, M3] m3s = {}, Table violations = {}) {
  numJavaFiles = ( 0 | it + 1 | <\java(), f, _> <- m3s );
  if (numJavaFiles == 0) {
    throw undefined("No Java files available", |java+project:///|);
  }
  //println("size violations: <size(violations.file)>");
  //println("total files: <numJavaFiles>");
  return percent(size(violations.file),  numJavaFiles);
}
  
private map[loc,int] violationsPerFile(rel[Language, loc, M3] m3s = {}, Table violations = {})
  = (f:0 | <\java(), f, _> <- m3s) + distribution({ <<k,g,l>, f> | <g,k,f,l> <- violations});
  
private real spreadOverFiles(rel[Language, loc, M3] m3s = {}, Table violations = {}) 
  = spreadOverItems(violationsPerFile(m3s=m3s, violations=violations));
 
@metric{styleViolations}
@doc{
  This is a basic metric which collects per file all the style violations, recording the line number and the kind of style violation.
  Each kind of violation is grouped into a category. The resulting table is hard to interpret manually and can not be compared between projects.
  Other metrics further downstream do aggregate this information.
}
@friendlyName{All style violations}
@appliesTo{java()}
Table styleViolations(rel[Language, loc, AST] asts = {}, rel[Language, loc, M3] m3s = {}) {
  messages = [*checkAll(ast, m3) | <\java(), file, ast> <- asts, <\java(), file, m3> <- m3s];
  return { <group, kind, file.top, file.begin.line?1> | str kind(str group, loc file) <- messages};
}

@metric{filesWithStyleViolations}
@doc{Percentage of files with style violations}
@friendlyName{Counts the number of files with any kind of style violation. This metric can not be easily compared between projects.}
@appliesTo{java()}
@uses=("styleViolations": "styleViolations")
@historic{}
int filesWithStyleViolations(rel[Language, loc, M3] m3s = {}, Table styleViolations = {})
  = percentageOfFilesWithViolations(m3s=m3s, violations=styleViolations);

@metric{spreadOfStyleViolations}
@doc{Between 0 and 1 how evenly spread are the style violations. This metric makes sense if there are more than 5 files in a project and can
be compared between projects as well. If problems are widespread this may be a quality contra-indicator, while a localized problem could be easily fixed.}
@friendlyName{Spread of style violations over files}
@appliesTo{java()}
@uses=("styleViolations": "styleViolations")
@historic{}
real spreadOfStyleViolations(rel[Language, loc, M3] m3s = {}, Table styleViolations = {}) 
  = spreadOverFiles(m3s=m3s, violations=styleViolations);

private set[str] errorProneCategories = {
  "blockCheck",
  "coding"
};

@metric{errorProneness}
@doc{Percentage of the projects files with coding style violations which indicate error prone code. This is a basic metric which collects per file all the style violations, recording the line number and the kind of style violation.
  Each kind of violation is grouped into a category. The resulting table is hard to interpret manually and can not be compared between projects.
  Other metrics further downstream do aggregate this information.}
@friendlyName{Error proneness}
@uses = ("styleViolations":"styleViolations")
@appliesTo{java()}
Table errorProneness(rel[Language, loc, M3] m3s = {}, Table styleViolations = {}) 
  = { <g,c,f,l> | <g, c, f, l> <- styleViolations, c in errorProneCategories};

@metric{filesWithErrorProneness}
@doc{Percentage of files with error proneness}
@friendlyName{Files with style violations which make the code error prone. This is basic metric which can not be easily compared between projects.}
@appliesTo{java()}
@uses=("errorProneness":"styleViolations")
@historic{}
int filesWithErrorProneness(rel[Language, loc, M3] m3s = {}, Table styleViolations = {})
  = percentageOfFilesWithViolations(m3s=m3s, violations=styleViolations);

@metric{spreadOfErrorProneness}
@doc{Between 0 and 1 how evenly spread are the style violations which indicate error proneness. This metric makes sense if there are more than 5 files in a project and can
be compared between projects as well. If problems are widespread this may be a quality contra-indicator, while a localized problem could be easily fixed.}
@friendlyName{Spread of error proneness style violations over files}
@appliesTo{java()}
@uses=("errorProneness":"styleViolations")
@historic{}
real spreadOfErrorProneness(rel[Language, loc, M3] m3s = {}, Table styleViolations = {}) 
  = spreadOverFiles(m3s=m3s, violations=styleViolations);
  
private set[str] inefficiencyCategories = {
  "string"
};

@metric{inefficiencies}
@doc{Percentage of the projects files with coding style violations which indicate common inefficient ways of doing things in Java.}
@friendlyName{Inefficient code}
@uses = ("styleViolations":"styleViolations")
@appliesTo{java()}
Table inefficiencies(rel[Language, loc, M3] m3s = {}, Table styleViolations = {}) 
  = { <g,c,f,l> | <g, c, f, l> <- styleViolations, c in inefficiencyCategories};

@metric{filesWithInefficiencies}
@doc{Percentage of files with inefficiencies}
@friendlyName{Files with style violations which indicate inefficiencies. This is a basic metric which can not be easily compared between projects.}
@appliesTo{java()}
@uses=("inefficiencies":"styleViolations")
@historic{}
int filesWithInefficiencies(rel[Language, loc, M3] m3s = {}, Table styleViolations = {})
  = percentageOfFilesWithViolations(m3s=m3s, violations=styleViolations);

@metric{spreadOfInefficiencies}
@doc{Between 0 and 1 how evenly spread are the style violations which indicate inefficiencies. This metric makes sense if there are more than 5 files in a project and can
be compared between projects as well. If problems are widespread this may be a quality contra-indicator, while a localized problem could be easily fixed.}
@friendlyName{Spread of inefficiencies over files}
@appliesTo{java()}
@uses=("inefficiencies":"styleViolations")
@historic{}
real spreadOfInefficiencies(rel[Language, loc, M3] m3s = {}, Table styleViolations = {}) 
  = spreadOverFiles(m3s=m3s, violations=styleViolations);
  
private set[str] unreadableCategories = {
  "namingConvention",
  "metric",
  "sizeViolation",
  "imports",
  "classDesign"
};  

@metric{understandability}
@doc{Percentage of the projects files with coding style violations which indicate the code may be hard to read and understand, 
but not necessarily more error prone.}
@friendlyName{Inefficient code}
@uses = ("styleViolations":"styleViolations")
@appliesTo{java()}
Table understandability(rel[Language, loc, M3] m3s = {}, Table styleViolations = {}) 
  = { <g,c,f,l> | <g, c, f, l> <- styleViolations, c in unreadableCategories};

@metric{filesWithUnderstandabilityIssues}
@doc{Percentage of files with understandability issues. This is a basic metric which can not be easily compared between projects.}
@friendlyName{Files with style violations which make the code harder to understand}
@appliesTo{java()}
@uses=("understandability":"styleViolations")
@historic{}
int filesWithUnderstandabilityIssues(rel[Language, loc, M3] m3s = {}, Table styleViolations = {})
  = percentageOfFilesWithViolations(m3s=m3s, violations=styleViolations);

@metric{spreadOfUnderstandabilityIssues}
@doc{Between 0 and 1 how evenly spread are the understandability issues. This metric makes sense if there are more than 5 files in a project and can
be compared between projects as well. If problems are widespread this may be a quality contra-indicator, while a localized problem could be easily fixed.}
@friendlyName{Spread of understandability issues over files}
@appliesTo{java()}
@uses=("understandability":"styleViolations")
@historic{}
real spreadOfUnderstandabilityIssues(rel[Language, loc, M3] m3s = {}, Table styleViolations = {}) 
  = spreadOverFiles(m3s=m3s, violations=styleViolations);

@metric{errorProneFactoid}
@uses=("spreadOfErrorProneness":"spreadOfErrorProneness"
      ,"filesWithErrorProneness":"filesWithErrorProneness"
      ,"filesWithErrorProneness.historic":"filesWithErrorPronenessHistory"
      )
@doc{Explains what the impact of error prone style violations is for the project. If problems are widespread this may be a quality contra-indicator, while a localized problem could be easily fixed.}
@friendlyName{Error proneness}
@appliesTo{java()}
Factoid errorProneFactoid( real spreadOfErrorProneness  = 0.0
                         , int filesWithErrorProneness  = 0
                         , rel[datetime, int] fileWithErrorPronenessHistory = {}
                         ) 
  = genericFactoid("error prone code (e.g. complex expressions, deep nesting)", spread=spreadOfErrorProneness, files=filesWithErrorProneness, history=fileWithErrorPronenessHistory);

@metric{inefficientStringsFactoid}
@uses=("spreadOfInefficiencies":"spreadOfInefficiencies" 
      ,"filesWithInefficiencies":"filesWithInefficiencies"
      ,"filesWithInefficiencies.historic":"filesWithInefficienciesHistory"
      )
@doc{Explains what the impact of inefficient string management is for the project. If problems are widespread this may be a quality contra-indicator, while a localized problem could be easily fixed.}
@friendlyName{Inefficient string management}
@appliesTo{java()}      
Factoid inefficientStringsFactoid( real spreadOfInefficiencies  = 0.0
                               , int filesWithInefficiencies  = 0
                               , rel[datetime, int] filesWithInefficienciesHistory = {}
                         ) 
  = genericFactoid("inefficient string usage code", spread=spreadOfInefficiencies, files=filesWithInefficiencies, history=filesWithInefficienciesHistory);

@metric{understandabilityFactoid}
@uses=("spreadOfUnderstandabilityIssues":"spreadOfUnderstandabilityIssues"
      ,"filesWithUnderstandabilityIssues":"filesWithUnderstandabilityIssues"
      ,"filesWithUnderstandabilityIssues.historic":"filesWithUnderstandabilityIssuesHistory"
      )
@doc{Explains what the impact of code understandability style violations is for the project. If problems are widespread this may be a quality contra-indicator, while a localized problem could be easily fixed.}
@friendlyName{Code understandability}
@appliesTo{java()}      
Factoid understandabilityFactoid( real spreadOfUnderstandabilityIssues  = 0.0
                               , int filesWithUnderstandabilityIssues  = 0
                               , rel[datetime, int] filesWithUnderstandabilityIssuesHistory = {}
                         ) 
  = genericFactoid("hard to understand code (e.g. not following naming conventions, large methods)", spread=spreadOfUnderstandabilityIssues, files=filesWithUnderstandabilityIssues, history=filesWithUnderstandabilityIssuesHistory);


private Factoid genericFactoid(str category
                      , real spread  = 0.0
                      , int files  = 0
                      , rel[datetime, int] history = {}
                      ) {
   sl = historicalSlope(history, 6);
                         
   expect1 = "";
   expect2 = "";
                            
   switch (<sl < -0.1, -0.1 >= sl && sl <= 0.1, sl > 0.1>) {
     case <true   , _      , _     > : { expect1 = "and its getting worse in the last six months"; expect2 = "but issues have been spreading in the last six months"; }
     case <_      , true   , _     > : { expect1 = "and this situation is stable"; expect2 = "but the situation is stable"; }
     case <_      , _      , true  > : { expect1 = "but the situation is improving over the last six months"; expect2 = "and the situaton has been improving in the last six months"; }
   }
                           
   switch (<spread > 0.5, spread < 0.2,  spread == 0.0>) {
     case <_,_,true> : return factoid("Currently, there is no <category> in this project <expect1>.", \four());
     case <_,true,_> : return factoid("Currently, <category> is localized to a minor part of the project <expect2>.", \three());
     case <true,_,_> : return factoid("Currently, <category> is widely spread throughout the project <expect1>.", \one());
     default         : return factoid("Currently, there is a some small amount of <category> spread through a small part of the project <expect2>.", \two());
   }
}
