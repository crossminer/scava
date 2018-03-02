@license{
  Copyright (c) 2017 Centrum Wiskunde & Informatica
  
  This program and the accompanying materials are made
  available under the terms of the Eclipse Public License 2.0
  which is available at https://www.eclipse.org/legal/epl-2.0/
  
  SPDX-License-Identifier: EPL-2.0
}
module ActiveCommitters

import org::eclipse::scava::metricprovider::MetricProvider;
import org::eclipse::scava::metricprovider::ProjectDelta;
import ValueIO;
import IO;
import Map;
import Set;
import Relation;
import List;
import DateTime;
import String;
import util::Math;
import analysis::statistics::SimpleRegression;
import analysis::statistics::Descriptive;
import analysis::statistics::Frequency;
import analysis::statistics::Inference;
 
@metric{committersToday}
@doc{Who have been active today?}
@friendlyName{Active committers}
@appliesTo{generic()}
set[str] committersToday(ProjectDelta delta = \empty()) {
  return {co.author | /VcsCommit co := delta};
}

@metric{committersEmailsToday}
@doc{Who have been active today?}
@friendlyName{Active committers}
@appliesTo{generic()}
set[str] committersEmailsToday(ProjectDelta delta = \empty()) {
  return {co.email | /VcsCommit co := delta};
}

@metric{firstLastCommitDatesPerDeveloper}
@doc{Collects per developer the first and last dates on which he or she contributed code. This basic metric is used downstream for other metrics, but
it is also used to drill down on the membership of specific individuals of the development team.} 
@friendlyName{First and last commit dates per developer}
@uses = ("committersToday":"committersToday")
@appliesTo{generic()}
map[str, tuple[datetime, datetime]] firstLastCommitDates(ProjectDelta delta = \empty(), map[str, tuple[datetime first, datetime last]] prev = (), 
  set[str] committersToday = {}) {
  map[str, tuple[datetime, datetime]] developerCommitDates = prev;
  for (author <- committersToday) {
    if (author in prev) {
      // don't assume the dates in delta are newer than in prev.
      // for instance, then can get mixed up during conversion from svn to git
      developerCommitDates[author] = <min([delta.date, prev[author].first]), max([delta.date, prev[author].last])>;
    } else {
      developerCommitDates[author] = <delta.date, delta.date>;
    }
  }
  return developerCommitDates;
}

@metric{commitsPerDeveloper}
@doc{The number of commits per developer indicates not only the volume of the contribution of an individual but also the style in which he or she commits,
when combined with other metrics such as churn. Few and big commits are different from many small commits. This metric is used downstream by other metrics as well.}
@friendlyName{Number of commits per developer}
@appliesTo{generic()}
@historic
map[str, int] commitsPerDeveloper(ProjectDelta delta = \empty(), map[str, int] prev = ()) {
  map[str, int] result = prev;
  
  for (/VcsCommit co := delta) {
    result[co.author]?0 += 1;
  }
  
  return result;
}

@metric{committersAge}
@doc{Measures in days the amount of time between the first and last contribution of each developer.}
@friendlyName{Developer experience in project}
@uses = ("firstLastCommitDatesPerDeveloper" : "commitDates")
@appliesTo{generic()}
rel[str, int] ageOfCommitters(map[str, tuple[datetime first, datetime last]] commitDates = ()) {
  return { <author, daysDiff(commitDates[author].first, commitDates[author].last) + 1> | author <- commitDates };
}

@metric{developmentTeam}
@doc{Lists the names of people who have been contributing code at least once in the history of the project.}
@friendlyName{Development team}
@uses = ("committersToday" : "committersToday")
@appliesTo{generic()}
set[str] developmentTeam(set[str] prev = {}, set[str] committersToday = {}) {
  return prev + committersToday;
}

@metric{developmentTeamEmails}
@doc{Lists the names of people who have been contributing code at least once in the history of the project.}
@friendlyName{Development team}
@uses = ("committersEmailsToday" : "committersEmailsToday")
@appliesTo{generic()}
set[str] developmentTeamEmails(set[str] prev = {}, set[str] committersEmailsToday = {}) {
  return prev + committersEmailsToday;
}


@metric{developmentDomainNames}
@doc{Lists the domain names of email addresses of developers if such information is present.}
@friendlyName{Development team domain names}
@uses = ("developmentTeamEmails" : "developmentTeamEmails")
@appliesTo{generic()}
set[str] developmentDomainNames(set[str] developmentTeamEmails = {}) {
  return {domain | /^[^@]*@<domain:.*>$/ <- developmentTeamEmails};
}

@metric{sizeOfDevelopmentTeam}
@doc{How many people have ever contributed code to this project?}
@friendlyName{Size of development team}
@uses = ("developmentTeam" : "team")
@appliesTo{generic()}
int sizeOfDevelopmentTeam(set[str] team = {}) {
  return size(team);
}

@metric{activeCommitters}
@doc{A list of committers who have been active the last two weeks. This metric is meant for downstream processing.}
@friendlyName{Committers of last two weeks}
@uses = ("committersToday":"committersToday")
@appliesTo{generic()}
rel[datetime, set[str]] activeCommitters(ProjectDelta delta = \empty(), rel[datetime,set[str]] prev = {}, set[str] committersToday = {}) {
  today    = delta.date;
  twoweeks = decrementDays(today, 14);
  return {<d,t> | <d,t> <- prev, d > twoweeks} + {<today, committersToday>};  
}

@metric{longerTermActiveCommitters}
@doc{Committers who have been active the last 12 months. This metric is meant for downstream processing.}
@friendlyName{Committers of last year}
@uses = ("committersToday":"committersToday")
@appliesTo{generic()}
rel[datetime, set[str]] longerTermActiveCommitters(ProjectDelta delta = \empty(), rel[datetime,set[str]] prev = {}, set[str] committersToday = {}) {
  today    = delta.date;
  twelvemonths = decrementMonths(today, 12);
  return {<d,t> | <d,t> <- prev, d > twelvemonths} + {<today, committersToday>};  
}


@metric{numberOfActiveCommitters}
@doc{Number of active committers over time (active in last two weeks). This measures a smooth window of two weeks, where every day we report the number of developers in the previous 14 days.}
@friendlyName{Number of active committers}
@uses = ("activeCommitters" :"activeCommitters")
@appliesTo{generic()}
@historic{}
int numberOfActiveCommitters(rel[datetime, set[str]] activeCommitters = {}) 
  = size({c | /str c := activeCommitters});
    
@metric{numberOfActiveCommittersLongTerm}
@doc{Number of long time active committers over time (active in last year). This measures a smooth window of one year, where every day we report the number of developers active in the previous 365 days.}
@friendlyName{Number of active committers long term}
@uses = ("longerTermActiveCommitters" :"activeCommitters")
@appliesTo{generic()}
@historic
int numberOfActiveCommittersLongTerm(rel[datetime, set[str]] activeCommitters = {}) 
  = size({c | /str c := activeCommitters});

@metric{maximumActiveCommittersEver}
@doc{What is the maximum number of committers who have been active together in any two week period?}
@friendlyName{Maximum active committers ever}
@uses = ("numberOfActiveCommitters.historic" :"history")
@appliesTo{generic()}
int maximumActiveCommittersEver(rel[datetime d, int n] history = {}) {
  if (size(history) > 0) {
    return max(history<n>);
  }
  return 0;
}

@metric{developmentTeamStability}
@doc{We measured the activity of individual development team members to infer the size of the development team and possibly a trend.
The growth or shrinkage of the team}
@friendlyName{Development team stability}
@uses = ("numberOfActiveCommitters.historic" :"history"
      ,"maximumActiveCommittersEver":"maxDevs"
      ,"numberOfActiveCommitters":"activeDevs"
      ,"sizeOfDevelopmentTeam":"totalDevs"
      ,"numberOfActiveCommittersLongTerm":"longTermActive")
@appliesTo{generic()}
Factoid developmentTeamStability(rel[datetime day, int active] history = {}, int maxDevs = 0, int totalDevs = 0, int activeDevs = 0, int longTermActive = 0) {
  sl = historicalSlope(history, 6);

  stability = \one();
  team = "";
  
  if (-0.1 < sl && sl < 0.1 && longTermActive > 0) {
    stability = \three(); 
    team = "In the last half year the development team was stable and active.";
  }
  else if (-0.1 < sl && sl < 0.1 && longTermActive == 0) {
    stability = \one(); 
    team = "The project has seen hardly anybody developing in the last half year.";
  } 
  else if (sl < 0 && longTermActive > 0) {
    stability = \two(); 
    team = "People have been leaving the development team in the last half year.";
  }
  else if (sl > 0) {
    stability = \four(); 
    team = "In the last half year the development team has been growing.";
  }
  
  txt = "<team>
        'The total number of developers who have worked on this project ever is <totalDevs>.
        'The maximum number of active developers for this project during its lifetime is <maxDevs>.
        'In the last two weeks there were <activeDevs> people actively developing, 
        '<longTermActive> in the last twelve months.";
        
  return factoid(txt, stability);
}

@metric{projectAge}
@doc{Age of the project (nr of days between first and last commit)}
@friendlyName{Age of the project (nr of days between first and last commit)}
@uses = ("firstLastCommitDatesPerDeveloper":"firstLastCommitDates")
@appliesTo{generic()}
int projectAge(map[str, tuple[datetime first, datetime last]] firstLastCommitDates = ()) {
  if (firstLastCommitDates == ()) {
    throw undefined("No commit dates available", |tmp:///|);
  }

  firstDate = min([ firstLastCommitDates[name][0] | name <- firstLastCommitDates]);
  lastDate = max([ firstLastCommitDates[name][1] | name <- firstLastCommitDates]);
  
  return daysDiff(firstDate, lastDate) + 1;
}

@metric{developmentTeamExperience}
@doc{Based on committer activity, how experienced is the current team?}
@friendlyName{Development team experience}
@uses = ("firstLastCommitDatesPerDeveloper": "firstLastCommitDates", "commitsPerDeveloper": "commitsPerDeveloper")
@appliesTo{generic()}
Factoid developmentTeamExperience(
  ProjectDelta delta = \empty(),
  map[str, tuple[datetime first, datetime last]] firstLastCommitDates = (),
  map[str, int] commitsPerDeveloper = ())
{
  if (delta == \empty() || commitsPerDeveloper == ()) {
    throw undefined("No delta available", |tmp:///|);
  }
  
  today = delta.date;
  sixMonthsAgo = decrementMonths(today, 6);
  
  committersInLastHalfYear = { name | name <- firstLastCommitDates, firstLastCommitDates[name].last > sixMonthsAgo };
  
  experiencedCommittersInLastHalfYear = { name | name <- committersInLastHalfYear,
    firstLastCommitDates[name].last > decrementMonths(firstLastCommitDates[name].first, 6),
    (commitsPerDeveloper[name]?0) > 24 }; // at least 1 commit per week on average
  
  numExperiencedCommitters = size(experiencedCommittersInLastHalfYear);
  
  stars = numExperiencedCommitters + 1;
  
  if (stars > 4) {
    stars = 4;
  }
  
  txt = "";
  
  if (stars == 1) {
    txt = "There were no experienced committers working for the project in the last 6 months.";
  }
  else if (stars == 2) {
    txt = "There was only one experienced committer working for the project in the last 6 months.";
    txt += " Overall, they contributed <commitsPerDeveloper[getOneFrom(experiencedCommittersInLastHalfYear)]> commits.";
  }
  else {
    txt = "The number of experienced committers working for the project in the last 6 months is <numExperiencedCommitters>.";
    txt += " Their average overall number of commits is <round(mean([commitsPerDeveloper[d] | d <- experiencedCommittersInLastHalfYear]),0.01)>.";
  }

  if (size(committersInLastHalfYear) == numExperiencedCommitters) {
    txt += " There were no other committers active in the last 6 months."; 
  }
  else {
    txt += " In total, <size(committersInLastHalfYear)> committers have worked on the project in the last six months.";
  }

  return factoid(txt, starLookup[stars]);
}

@metric{committersoverfile}
@doc{Calculates the gini coefficient of committers per file}
@friendlyName{Committers over file}
@appliesTo{generic()}
@uses=("countCommittersPerFile": "perFile")
@historic{}
real giniCommittersOverFile(ProjectDelta delta = \empty(), map[loc,int] perFile = ()) {
  map[loc, int] committersOverFile = distribution({<perFile[l], l> | l <- perFile});
  map[int, int] distCommitterOverFile = distribution(committersOverFile);
  
  if (size(distCommitterOverFile) > 0) {
    if (sum(distCommitterOverFile<1>) == 0) {
      return 1.0; // completely honest distribution of nothing over everything.
    }
    return round(gini([<0,0>]+[<x, distCommitterOverFile[x]> | x <- distCommitterOverFile]), 0.01);
  }

  throw undefined("not enough data to compute committer over file spread", |project://<delta.project.name>|);
}

@metric{countCommittersPerFile}
@doc{Count the number of committers that have touched a file.}
@friendlyName{Number of committers per file}
@appliesTo{generic()}
@uses= ("committersPerFile": "perFile")
map[loc file, int numberOfCommitters] countCommittersPerFile(ProjectDelta delta = \empty(), rel[loc file, str person] perFile = {}) {
  return (f : size(perFile[f]) | f <- domain(perFile));
}

@metric{committersPerFile}
@doc{Register which committers have contributed to which files}
@friendlyName{Committers per file}
@appliesTo{generic()}
rel[loc, str] committersPerFile(ProjectDelta delta = \empty(), rel[loc, str] prev = {}) 
  = prev + { <vcrd.repository.url + vci.path, vc.author> 
           | /VcsRepositoryDelta vcrd <- delta
           , /VcsCommit vc <- delta
           , vc.author != "null", vc.author != ""
           , VcsCommitItem vci <- vc.items
           }; 


@metric{developmentTeamExperienceSpread}
@doc{How specialized is the development team? Or are people working on different parts of the project?}
@friendlyName{Development team experience spread}
@uses = ("committersoverfile": "developmentTeamExperienceSpread", "countCommittersPerFile": "perFile")
@appliesTo{generic()}
Factoid developmentTeamExperienceSpread(real developmentTeamExperienceSpread = 0.0, map[loc,int] perFile = ()) {
  list[int] amounts = [ perFile[i] | i <- perFile];
  if (amounts == []) {
    throw undefined("No commit data available.", |tmp:///|);
  }
  
  med = median(amounts);
  maxi = List::max(amounts);
  
  if (developmentTeamExperienceSpread >= 0.5) {
    if (med >= 2) {
      return factoid(\four(), "Developers are spreading out over the entire project and it can be expected that most files have more than one contributor.");
    }
    else {
        return factoid(\three(), "Developers are spreading out over the entire project but most files will have only one contributor.");
    }
  }
  else {
    if (maxi > 1) {
      return factoid(\two(), "Developers are mostly focusing on their own files in the project, but there is definitely some collaboration going on.");
    }
    else {
      return factoid(\one(), "Developers are mostly focused on their own files in the project.");
    }
  }
}

@metric{commitsPerWeekDay}
@doc{On which day of the week do commits take place?}
@friendlyName{Commits per week day}
@appliesTo{generic()}
map[str, int] commitsPerWeekDay(ProjectDelta delta = \empty(), map[str, int] prev = ()) {
  dayOfWeek = printDate(delta.date, "EEE");
  return prev + ( dayOfWeek : (prev[dayOfWeek]?0) + (0 | it + 1 | /VcsCommit vcsCommit <- delta));
}

@metric{percentageOfWeekendCommits}
@doc{Percentage of commits made during the weekend}
@friendlyName{Percentage of weekend commits}
@appliesTo{generic()}
@uses=("commitsPerWeekDay":"commitsPerWeekDay")
@historic{}
int percentageOfWeekendCommits(map[str,int] commitsPerWeekDay = ()) {
  total = sum([commitsPerWeekDay[d] | d <- commitsPerWeekDay]);
  weekend = (commitsPerWeekDay["Sat"]?0) + (commitsPerWeekDay["Sun"]?0);

  if (total > 0 && weekend > 0) {
    return percent(weekend, total);
  }
  
  return 0;  
}

private Factoid factoid(StarRating stars, str msg) = factoid(msg, stars);

@metric{weekendProject}
@doc{Is this project mainly developed during the week?}
@friendlyName{Week day project}
@appliesTo{generic()}
@uses=("percentageOfWeekendCommits": "percentageOfWeekendCommits")
Factoid weekendProject(int percentageOfWeekendCommits = -1) {
  if (percentageOfWeekendCommits == -1) {
    throw undefined("No weekend commit data available.", |tmp:///|);
  }

  if (percentageOfWeekendCommits > 75) {
    return factoid(\one(), "Over the entire lifetime of this project, commits have been done usually over the weekend.");
  }
  else if (percentageOfWeekendCommits > 50) {
    return factoid(\two(), "Over the entire lifetime of this project, commits are done mostly over the weekend, but also significantly during the week.");
  }
  else if (percentageOfWeekendCommits > 25) {
    return factoid(\three(), "Over the entire lifetime of this project, commits are done mostly during the week, but also significantly during the weekend.");
  }
  else {
    return factoid(\four(), "Over the entire lifetime of this project, commits are done usually during the week, and hardly during the weekend.");
  }
}
