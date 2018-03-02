@license{
Copyright (c) 2014 SCAVA Partners.
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html
}
module Split

import String;

public tuple[bool match, str left, str right] firstSplit(str src, str sep) {
	p = findFirst(src, sep);
	if (p == -1) {
		return <false, "", "">;
	}
	pp = p + size(sep);
	return <true, src[..p], (pp < size(src)) ? src[pp..] : "">;
}

public tuple[bool match, str left, str right, str sep] firstSplit(str src, set[str] seps) {
	str left, right, sep;
	srcSize = size(src);
	pos = srcSize + 1;
	
	for (s <- seps) {
		p = findFirst(src, s);
		if (p != -1 && p < pos) {
			pos = p;
			pp = p + size(s);
			left = src[..p];
			right = (pp < srcSize) ? src[pp..] : "";
			sep = s;
		}
	}
	
	if (pos < srcSize) {
		return <true, left, right, sep>;
	}
	
	return <false, "", "", "">;
}
