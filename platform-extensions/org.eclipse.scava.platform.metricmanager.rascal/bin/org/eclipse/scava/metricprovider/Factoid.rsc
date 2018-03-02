@license{
  Copyright (c) 2017 Centrum Wiskunde & Informatica
  
  This program and the accompanying materials are made
  available under the terms of the Eclipse Public License 2.0
  which is available at https://www.eclipse.org/legal/epl-2.0/
  
  SPDX-License-Identifier: EPL-2.0
}
module org::eclipse::scava::metricprovider::Factoid

data StarRating 
  = \one() 
  | \two()
  | \three()
  | \four()
  ;
  
public map[int, StarRating] starLookup = (1:\one(), 2:two(), 3:three(), 4:four());
  
data Factoid
  = factoid(str freetext, StarRating rating);
