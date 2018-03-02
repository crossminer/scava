@license{
  Copyright (c) 2017 Centrum Wiskunde & Informatica
  
  This program and the accompanying materials are made
  available under the terms of the Eclipse Public License 2.0
  which is available at https://www.eclipse.org/legal/epl-2.0/
  
  SPDX-License-Identifier: EPL-2.0
}
module Churn

import org::eclipse::scava::metricprovider::ProjectDelta;
import org::eclipse::scava::metricprovider::MetricProvider;

import Relation;
import List;
import DateTime;
import analysis::statistics::SimpleRegression;
import analysis::statistics::Descriptive;
import analysis::statistics::Frequency;
import analysis::statistics::Inference;
import util::Math;


@metric{churnPerCommit}
@doc{Count churn. Churn is the number lines added or deleted. We measure this per commit because the commit
is a basic unit of work for a programmer. This metric computes a table per commit for today and is not used for comparison between projects. It is used further downstream to analyze activity.}
@friendlyName{Counts number of lines added and deleted per commit.}
@appliesTo{generic()}
@historic{}
map[loc, int] churnPerCommit(ProjectDelta delta = \empty()) {
   map[loc, int] result = ();
   for (/VcsRepositoryDelta vcsDelta := delta) {
    result += (vcsDelta.repository.url + co.revision : churn(co) | /VcsCommit co := vcsDelta);
   }
   return result;
}

@metric{commitsToday}
@doc{Counts the number of commits made today.}
@friendlyName{Number of commits today}
@appliesTo{generic()}
@historic{}
int commitsToday(ProjectDelta delta = \empty()) 
  = (0 | it + 1 |/VcsCommit _ := delta)
  ;
  
@metric{churnToday}
@doc{Counts the churn for today: the total number of lines of code added and deleted. This metric is used further downstream to analyze trends.}
@friendlyName{Churn of today}
@appliesTo{generic()}
@historic{}
int commitsToday(ProjectDelta delta = \empty()) 
  = churn(delta);
  

@metric{commitActivity}
@doc{Number of commits in the last two weeks: collects commit activity over a 14-day sliding window.}
@friendlyName{Commits in last two weeks}
@uses = ("commitsToday":"commitsToday")
@appliesTo{generic()}
rel[datetime, int] commitActivity(ProjectDelta delta = \empty(), rel[datetime,int] prev = {}, int commitsToday = 0) {
  today    = delta.date;
  twoweeks = decrementDays(today, 14);
  return {<d,t> | <d,t> <- prev, d > twoweeks} + {<today, commitsToday>};  
}

@metric{churnActivity}
@friendlyName{Churn over the last two weeks}
@doc{Churn in the last two weeks: collects the lines of code added and deleted over a 14-day sliding window.}
@uses = ("churnToday":"churnToday")
@appliesTo{generic()}
rel[datetime, int] churnActivity(ProjectDelta delta = \empty(), rel[datetime,int] prev = {}, int churnToday = 0) {
  today    = delta.date;
  twoweeks = decrementDays(today, 14);
  return {<d,t> | <d,t> <- prev, d > twoweeks} + {<today, churnToday>};  
}

@metric{churnInTwoWeeks}
@doc{Sum of churn over the last two weeks}
@doc{Churn in the last two weeks: aggregates the lines of code added and deleted over a 14-day sliding window.}
@friendlyName{Sum of churn in the last two weeks}
@uses = ("churnActivity":"activity")
@appliesTo{generic()}
@historic{}
int churnInTwoWeeks(rel[datetime, int] activity = {}) 
  = (0 | it + ch | <_, ch> <- activity);
  
@metric{commitsInTwoWeeks}
@doc{Number of commits in the last two weeks}
@doc{Churn in the last two weeks: aggregates the number of commits over a 14-day sliding window.}
@friendlyName{Number of commits in the last two weeks}
@uses = ("commitActivity":"activity")
@appliesTo{generic()}
@historic{}
int commitsInTwoWeeks(rel[datetime, int] activity = {}) 
  = (0 | it + ch | <_, ch> <- activity);
  
@metric{churnPerCommitInTwoWeeks}
@doc{The ratio between the churn and the number of commits indicates how large each commit is on average. We compute this as a sliding average over two weeks which smoothens exceptions and makes it possible to see a trend historically. Commits should not be to big all the time, because that would indicate either that programmers are not focusing on well-defined tasks or that the system architecture does not allow for separation of concerns.}
@friendlyName{Churn per commit in two weeks}
@uses = ("commitsInTwoWeeks":"commits","churnInTwoWeeks":"churn")
@appliesTo{generic()}
@historic{}
int churnPerCommitInTwoWeeks(int churn = 0, int commits = 1) = churn / commits;  

@metric{commitSize}
@doc{The activity of a project is characterized by the number of contributions per time unit and their size. We also report on the size of each unit of work (commit) size. Commits should not be to big all the time, because that would indicate either that programmers are not focusing on well-defined tasks or that the system architecture does not allow for separation of concerns.}
@friendlyName{Commit frequency and size}
@appliesTo{generic()}
@uses= ("churnPerCommitInTwoWeeks.historic":"ratioHistory",
        "churnPerCommitInTwoWeeks":"ratio")
Factoid commitSize(rel[datetime, int] ratioHistory = {}, int ratio = 0) {
  ratioSlope = historicalSlope(ratioHistory, 6);
  ratioMedian = historicalMedian(ratioHistory, 6);

   msg = "In the last two weeks the average size of a commit (total lines added/deleted) was <ratio>, this is <if (ratio > ratioMedian) {>more<}else{>less<}>
         'than the normal <round(ratioMedian)> in the last six months. <slopeText(ratioSlope, "Commits are getting smaller (which is a good thing)", "Commit size is stable", "Commits are getting larger")>.";
  
  // TODO: the magic constants are not well tested here
  if (ratio < 500) {
    if (ratioMedian > 250) {
      return factoid(msg, \two());
    }
    else if (ratioMedian > 125) {
      return factoid(msg, \three());
    }
    else {
      return factoid(msg, \four());
    }    
  }
  else {
    return factoid(msg, \one());
  }       
}

@metric{churnVolume}
@doc{Reporting how much work is actually done to the system currently and in the last six months.}
@friendlyName{Churn Volume}
@appliesTo{generic()}
@uses= ("churnInTwoWeeks.historic":"churnHistory", "churnInTwoWeeks":"churn")
Factoid churnVolume(rel[datetime, int] churnHistory = {}, int churn = 0) {
  churnSlope = historicalSlope(churnHistory, 6); 
  churnMedian = historicalMedian(churnHistory, 6);
  
  msg = "In the last two weeks the churn (total lines added and deleted) was <churn>. This is <if (churn > churnMedian) {>more<}else{>less<}>
        'than the median <round(churnMedian)> in the last six months. Activity is <slopeText(churnSlope, "slowing down", "stable", "speeding up")>.";
  
  if (churn > 0) {
    if (churnMedian < 100) {
      return factoid(msg, \two());
    }
    else if (churnMedian < 500) {
      return factoid(msg, \three());
    }
    else {
      return factoid(msg, \four());
    }    
  }
  else {
    return factoid(msg, \one());
  }       
}

@metric{churnPerCommitter}
@doc{Count churn per committer: the number of lines of code added and deleted. It zooms in on the single committer producing a table which can be used for downstream processing.}
@friendlyName{Churn per committer}
@appliesTo{generic()}
@historic{}
map[str author, int churn] churnPerCommitter(ProjectDelta delta = \empty())
  = sumPerItem([<co.author, churn(co)> | /VcsCommit co := delta])
  ;
  
private map[&T, int] sumPerItem(lrel[&T item, int val] input)
  = (x : s | x <- { * input<item> }, int s := filt(input, x));  
 
private int filt(lrel[&T, int] input, &T i) = (0 | it + nu | nu <- [n | <i, n> <- input]);
  
@metric{churnPerFile}
@doc{Churn per file counts the number of files added and deleted for a single file. This is a basic metric to indicate hotspots in the design of the system which is changed often. This metric is used further downstream.}
@friendlyName{Churn per file}
@appliesTo{generic()}
map[loc file, int churn] churnPerFile(ProjectDelta delta = \empty(), map[loc file, int churn] prev = ()) {
  result = prev;
  for (/VcsRepositoryDelta rd := delta, /VcsCommitItem co := rd) {
    result[rd.repository.url + co.path]?0 += churn(co);
  }
  return result;
}
      
int churn(node item) 
  = (0 | it + count | /linesAdded(count) := item)
  + (0 | it + count | /linesDeleted(count) := item)
  ;

@metric{filesPerCommit}
@doc{Counts the number of files per commit to find out about the separation of concerns in the architecture or in the tasks the programmers perform. This metric is used further downstream.}
@friendlyName{Number of files per commit}
@appliesTo{generic()}
@historic{}
map[loc, int] numberOfFilesPerCommit(ProjectDelta delta = \empty()) {
  map[loc, int] result = ();
  
  for (/VcsRepositoryDelta vcrd <- delta) {
    for (/VcsCommit vc <- delta) {
      result[vcrd.repository.url + vc.revision]? 0 += size(vc.items);
    }
  }
  
  return result;
}

@metric{commitLocality}
@doc{Find out if commits are usually local to a file or widespread over the system. If the commits are widespread this can mean either a problem with 
separation of concerns in the architecture of the system, or that the developers are not concentrating on one task at the same time.} 
@friendlyName{Commit locality}
@appliesTo{generic()}
@uses=("filesPerCommit.historic":"filesPerCommit")
Factoid commitLocality(rel[datetime day, map[loc, int] files] filesPerCommit = {}) {
   counts = [ d[f] | <_, map[loc, int] d> <- filesPerCommit, loc f <- d];
   if (counts == []) {
      throw undefined("No commit data available.", |tmp:///|);
   }
   
   med = median(counts);
   
   if (med <= 1) {
     return factoid("Commits are usually local to a single file (which is a good thing).", \four());
   }
   else if (med <= 5) {
     return factoid("Commits are usually local to a small group of files (which is a good thing).", \three());
   }
   else if (med <= 10) {
     return factoid("Commits usually include between 5 and 10 files.", \two());
   }
   else {
     return factoid("Commits usually include more than 10 files.", \one());
   }  
}
  
@metric{coreCommittersChurn}
@doc{Find out about the committers what their total number of added and deleted lines for this system.}
@friendlyName{Churn per core committer}
@appliesTo{generic()}
@uses = ("churnPerCommitter" : "committerChurn")
map[str, int] coreCommittersChurn(map[str, int] prev = (), map[str, int] committerChurn = ()) 
  = prev + (author : (prev[author]?0) + committerChurn[author] | author <- committerChurn);
  
  
