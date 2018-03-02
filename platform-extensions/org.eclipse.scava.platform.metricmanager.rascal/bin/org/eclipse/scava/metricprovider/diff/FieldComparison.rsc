@license{
  Copyright (c) 2017 Centrum Wiskunde & Informatica
  
  This program and the accompanying materials are made
  available under the terms of the Eclipse Public License 2.0
  which is available at https://www.eclipse.org/legal/epl-2.0/
  
  SPDX-License-Identifier: EPL-2.0
}
module org::eclipse::scava::metricprovider::diff::FieldComparison

import IO;
import List;
import Set;
import Type;
import String;
import Relation;

import org::eclipse::scava::metricprovider::diff::DataType;

import util::ValueUI;

import lang::java::m3::Core;
import lang::java::m3::AST;

public set[FieldChange] getFieldChanges(M3 newModel) = { addedField(f) | f <- newModel@declarations<0>, isField(f) };

public set[FieldChange] getFieldChanges(M3 oldModel, M3 newModel) {
  
  set [FieldChange] fieldChanges = {};
  set [FieldChange] tempFields = getAddedAndRemovedFields(oldModel, newModel);

    
  for (aField <- tempFields) {fieldChanges += aField; }
  tempFields = getAllChangedFields(oldModel, newModel);
  for (aField <- tempFields) {fieldChanges += aField; }
  
  return fieldChanges;
}

// This method returns the set of FieldChanges form added and removed fields only 
private set [FieldChange]  getAddedAndRemovedFields(M3 oldModel, M3 newModel) {
    oldPublicFields = { f | f <- oldModel@declarations<0>, isField(f) };
  newPublicFields = { f | f <- newModel@declarations<0>, isField(f) };
    set [FieldChange] addRemFieldsSet = {};
  set [loc] addedFields = newPublicFields- oldPublicFields;
    set [loc] removedFields = oldPublicFields - newPublicFields;
    for ( aField <- addedFields) {  addRemFieldsSet = addRemFieldsSet + addedField(aField); }
  for ( rField <- removedFields) {  addRemFieldsSet = addRemFieldsSet + deletedField(rField); } 
    return addRemFieldsSet;
}

// This method returns the set of FieldChanges for fields which are changed 
// (modifier, type or deprecated)
private set [FieldChange]  getAllChangedFields(M3 oldModel, M3 newModel) {
  
  set [loc] oldPublicFields = { f | f <- oldModel@declarations<0>, isField(f) };
  set [loc] newPublicFields = { f | f <- newModel@declarations<0>, isField(f) };
  set [FieldChange] returnSet = {};
  set [loc] commonFields = oldPublicFields & newPublicFields;
  
  //set[loc] oldDeprecations = findDeprecations(oldModel);
  //set[loc] newDeprecations = findDeprecations(newModel);
  
  map[loc name, set[TypeSymbol] typ] oldTypes = index(oldModel@types);
  map[loc name, set[TypeSymbol] typ] newTypes = index(newModel@types);
  
  map[loc definition, set[Modifier] modifier] oldModifiers = index(oldModel@modifiers);
  map[loc definition, set[Modifier] modifier] newModifiers = index(newModel@modifiers);
  

  //
  for (loc oneField <- commonFields) {
    set[Modifier] oldFieldModifiers = oneField in oldModifiers ? oldModifiers[oneField] : {};
      set[Modifier] newFieldModifiers = oneField in newModifiers ? newModifiers[oneField] : {};
            
    if (oneField in oldTypes && oneField in newTypes) {
      TypeSymbol oldFieldType = getOneFrom(oldTypes[oneField]);
      TypeSymbol newFieldType = getOneFrom(newTypes[oneField]);
      if (oldFieldType != newFieldType) {
        returnSet += fieldTypeChanged(oneField, oldFieldType, newFieldType); 
      }
    }
    
    if  (oldFieldModifiers != newFieldModifiers) {
      returnSet += fieldModifierChanged(oneField, oldFieldModifiers, newFieldModifiers);
    }
      
    //if (isDeprecated(oneField, oldDeprecations, newDeprecations)) {
    //  returnSet += fieldDeprecated(oneField);
    //} else if (isUndeprecated(oneField, oldDeprecations, newDeprecations, newPublicFields)) {
    //  returnSet += fieldUndeprecated(oneField);
    //}
  }
  return returnSet ;
}

private map[loc to, loc from] makeFieldTypeMap(M3 model) {
  return (typeDep.to : typeDep.from[0] | typeDep <- model@typeDependency);
}
