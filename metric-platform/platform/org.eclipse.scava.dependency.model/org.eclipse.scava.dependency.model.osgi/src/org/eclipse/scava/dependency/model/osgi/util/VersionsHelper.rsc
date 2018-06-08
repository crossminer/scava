@license{
	Copyright (c) 2018 Centrum Wiskunde & Informatica
	
	This program and the accompanying materials are made
	available under the terms of the Eclipse Public License 2.0
	which is available at https://www.eclipse.org/legal/epl-2.0/
	
	SPDX-License-Identifier: EPL-2.0
}
module org::eclipse::scava::dependency::model::osgi::util::VersionsHelper

import List;
import String;


//--------------------------------------------------------------------------------
// Functions
//--------------------------------------------------------------------------------

/*
 * This method only considers major and minor version values as suggested by 
 * the OSGi specification R.6. Each version follows the grammar: 
 * ("("|"[") major(.minor(.micro(.qualifier)?)?)?
 */
public bool versionExists(list[str] vers, str lowerVersion, str upperVersion) {
	found = false;
	if(size(vers) > 0) {
		lVersion = substring(lowerVersion, 1);
		uVersion = substring(upperVersion, 1);
		
		for(v <- vers, !found) {
			lInclusive = (startsWith(lowerVersion, "(")) ? false: true;
			uInclusive = (startsWith(upperVersion, "(")) ? false: true;
			found = (equalToVersion(lVersion, v) || (equalToVersion(uVersion, v)) 
				|| (lessThanVersion(lVersion, v, lInclusive) && lessThanVersion(v, uVersion, uInclusive))) 
				? true : false;
		}
	}
	return found;
}

/*
 * Returns a boolean stating if the first version (refVersion) is equal to another 
 * version (version). A version can look like "1.1.12.qualif234".
 */
public bool equalToVersion(str refVersion, str version) {
	refVArray = split(".", refVersion);
	vArray = split(".", version);
	
	refVArray = (size(refVArray) >= 2) ? refVArray[0..2] : refVArray + "0";
	vArray = (size(vArray) >= 2) ? vArray[0..2] : vArray + "0";
	
	if(toInt(refVArray[0]) == toInt(vArray[0]) && toInt(refVArray[1]) == toInt(vArray[1])) {
		return true;
	}
	return false;
}

/*
 * Returns a boolean stating if the first version (refVersion) is less than another 
 * version (version). Major, minor, and micro slots are considered. A version can 
 * look like "1.1.12.qualif234".
 */
public bool lessThanVersion(str refVersion, str version, bool inclusive) {
	refVArray = split(".", refVersion);
	vArray = split(".", version);
	
	if (size(refVArray) == 1 && refVArray[0] == "-1") {
		return false;
	}
	if (size(vArray) == 1 && vArray[0] == "-1") {
		return true;
	}
	
	refIntArray = normalizeVersion(refVArray);
	vIntArray = normalizeVersion(vArray);
	
	for(i <- [0..size(refIntArray)]) {
		if(refIntArray[i] < vIntArray[i]) {
			return true;
		}
		else if(refIntArray[i] > vIntArray[i]) {
			return false;
		}
	}
	return inclusive;
}

/*
 * Creates a version with three integer slots (i.e. macro, minor, micro).
 */
private list[int] normalizeVersion(list[str] versionArray) 
	= (size(versionArray) > 2) ? versionToInts(versionArray[0..3]) 
		: (size(versionArray) == 2) ? versionToInts(versionArray + "0") 
		: versionToInts(versionArray + ["0","0"]);

/*
 * Transforms a string version array to an integer version array.
 */
private list[int] versionToInts(list[str] versionArray) 
	= [toInt(s) | s <- versionArray];

/*
 * Detects if a version is inclusive. A version can look like "(1.1.12".
 */
public bool versionIsInclusive(str version)
	= (startsWith(version, "(")) ? false: true;

/*
 * Returns the highest version of a set of versions.
 */
public str highestVersion(set[str] versions) {
	str maxVersion = "0.0.0";
	for(v <- versions) {
		maxVersion = (lessThanVersion(maxVersion,v,true)) ? v : maxVersion;
	}
	return maxVersion;
}

/*
 * Returns the highest version of a set of versions, by considering
 * lower and upper boundary versions.
 */
public str highestVersionWithBounds(set[str] versions, str lowerVersion, str upperVersion) {
	str maxVersion = "0.0.0";
	lInclusive = (startsWith(lowerVersion, "(")) ? false: true;
	uInclusive = (startsWith(upperVersion, "(")) ? false: true;
	
	for(v <- versions) {
		maxVersion = (lessThanVersion(maxVersion,v,true) 
			&& lessThanVersion(substring(lowerVersion, 1),v,lInclusive)
			&& lessThanVersion(v,substring(upperVersion, 1),uInclusive)) 
			? v : maxVersion;
	}
	return maxVersion;
}