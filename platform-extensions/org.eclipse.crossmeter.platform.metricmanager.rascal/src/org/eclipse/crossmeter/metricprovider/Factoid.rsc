@license{
Copyright (c) 2014 CROSSMETER Partners.
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html
}
module org::eclipse::crossmeter::metricprovider::Factoid

data StarRating 
  = \one() 
  | \two()
  | \three()
  | \four()
  ;
  
public map[int, StarRating] starLookup = (1:\one(), 2:two(), 3:three(), 4:four());
  
data Factoid
  = factoid(str freetext, StarRating rating);
