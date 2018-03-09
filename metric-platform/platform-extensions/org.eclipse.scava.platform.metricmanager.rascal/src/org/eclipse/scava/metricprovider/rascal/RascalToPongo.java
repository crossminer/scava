/*******************************************************************************
 * Copyright (c) 2017 Centrum Wiskunde & Informatica
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.rascal;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.scava.metricprovider.rascal.trans.model.DatetimeMeasurement;
import org.eclipse.scava.metricprovider.rascal.trans.model.IntegerMeasurement;
import org.eclipse.scava.metricprovider.rascal.trans.model.ListMeasurement;
import org.eclipse.scava.metricprovider.rascal.trans.model.Measurement;
import org.eclipse.scava.metricprovider.rascal.trans.model.MeasurementCollection;
import org.eclipse.scava.metricprovider.rascal.trans.model.RealMeasurement;
import org.eclipse.scava.metricprovider.rascal.trans.model.SetMeasurement;
import org.eclipse.scava.metricprovider.rascal.trans.model.StringMeasurement;
import org.eclipse.scava.metricprovider.rascal.trans.model.TupleMeasurement;
import org.eclipse.scava.metricprovider.rascal.trans.model.URIMeasurement;
import org.eclipse.scava.platform.factoids.StarRating;
import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IDateTime;
import org.eclipse.imp.pdb.facts.IInteger;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IMap;
import org.eclipse.imp.pdb.facts.IReal;
import org.eclipse.imp.pdb.facts.ISet;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.ITuple;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.visitors.NullVisitor;

public class RascalToPongo {

	/**
	 * This creates the top-level table and adds uri entries where necessary.
	 */
	public static void toPongo(final MeasurementCollection measurements, IValue result) {
		result.accept(new NullVisitor<Void,RuntimeException>() {
			@Override
			public Void visitInteger(IInteger o) throws RuntimeException {
				IntegerMeasurement m = new IntegerMeasurement();
				m.setValue(o.longValue());
				measurements.add(m);
				return null;
			}
			
			@Override
			public Void visitString(IString o) throws RuntimeException {
				StringMeasurement m = new StringMeasurement();
				m.setValue(o.getValue());
				measurements.add(m);
				return null;
			}
			
			@Override
			public Void visitDateTime(IDateTime o) throws RuntimeException {
				DatetimeMeasurement m = new DatetimeMeasurement();
				m.setValue(o.getInstant(), o.getTimezoneOffsetHours(), o.getTimezoneOffsetMinutes());
				measurements.add(m);
				return null;
			}
			
			@Override
			public Void visitReal(IReal o) throws RuntimeException {
				RealMeasurement m = new RealMeasurement();
				m.setValue((float) o.doubleValue());
				measurements.add(m);
				return null;
			}
			
			@Override
			public Void visitSourceLocation(ISourceLocation o) {
				URIMeasurement m = new URIMeasurement();
				m.setValue(o.getURI().toString());
				measurements.add(m);
				return null;
			}
			
			@Override
			public Void visitMap(IMap o) throws RuntimeException {
				for (Iterator<Entry<IValue, IValue>> it = o.entryIterator(); it.hasNext(); ) {
					Entry<IValue, IValue> currentEntry = (Entry<IValue, IValue>) it.next();
					TupleMeasurement t = new TupleMeasurement();
					toPongo(t.getValue(), currentEntry.getKey());
					toPongo(t.getValue(), currentEntry.getValue());
					measurements.add(t);
				}
				return null;
			}

			@Override
			public Void visitListRelation(IList o) throws RuntimeException {
				for (IValue val : o) {
					toPongo(measurements, val);
				}
				return null;
			}
			
			@Override
			public Void visitList(IList o) throws RuntimeException {
				for (IValue val : o) {
					toPongo(measurements, val);
				}
				return null;
			}
			
			@Override
			public Void visitSet(ISet o) throws RuntimeException {
				for (IValue val : o) {
					toPongo(measurements, val);
				}
				return null;
			}
			
			@Override
			public Void visitTuple(ITuple o) throws RuntimeException {
				TupleMeasurement m = new TupleMeasurement();
				final List<Measurement> col = m.getValue();
				
				for (IValue val : o) {
					toPongo(col, val);
				}
				
				measurements.add(m);
				return null;
			}
		});
	}
	
	/**
	 * This recursively constructs Pongo objects to be stored in the database (single documents) 
	 * @param measurements
	 * @param loc
	 * @param result
	 */
	private static void toPongo(final List<Measurement> measurements, IValue result) {
		result.accept(new NullVisitor<Void,RuntimeException>() {
			@Override
			public Void visitInteger(IInteger o) throws RuntimeException {
				IntegerMeasurement m = new IntegerMeasurement();
				m.setValue(o.longValue());
				measurements.add(m);
				return null;
			}
			
			@Override
			public Void visitDateTime(IDateTime o) throws RuntimeException {
				DatetimeMeasurement m = new DatetimeMeasurement();
				m.setValue(o.getInstant(), o.getTimezoneOffsetHours(), o.getTimezoneOffsetMinutes());
				measurements.add(m);
				return null;
			}
			
			@Override
			public Void visitString(IString o) throws RuntimeException {
				StringMeasurement m = new StringMeasurement();
				m.setValue(o.getValue());
				measurements.add(m);
				return null;
			}
			
			@Override
			public Void visitReal(IReal o) throws RuntimeException {
				RealMeasurement m = new RealMeasurement();
				m.setValue((float) o.doubleValue());
				measurements.add(m);
				return null;
			}
			
			@Override
			public Void visitSourceLocation(ISourceLocation o) {
				URIMeasurement m = new URIMeasurement();
				m.setValue(o.getURI().toString());
				measurements.add(m);
				return null;
			}
			
			@Override
			public Void visitMap(IMap o) throws RuntimeException {
				// maps are stored as relations
				SetMeasurement m = new SetMeasurement();
				final List<Measurement> col = m.getValue();
				
				for (Iterator<Entry<IValue, IValue>> it = o.entryIterator(); it.hasNext(); ) {
					Entry<IValue, IValue> currentEntry = (Entry<IValue, IValue>) it.next();
					
					TupleMeasurement t = new TupleMeasurement();
					List<Measurement> elems = t.getValue();
					toPongo(measurements, currentEntry.getKey());
					toPongo(elems, currentEntry.getValue());

					col.add(t);
				}
				
				measurements.add(m);
				return null;
			}

			@Override
			public Void visitList(IList o) throws RuntimeException {
				ListMeasurement m = new ListMeasurement();
				final List<Measurement> col = m.getValue();
				
				for (IValue val : o) {
					toPongo(col, val);
				}
				
				measurements.add(m);
				return null;
			}
			
			@Override
			public Void visitSet(ISet o) throws RuntimeException {
				SetMeasurement m = new SetMeasurement();
				final List<Measurement> col = m.getValue();
				
				for (IValue val : o) {
					toPongo(col, val);
				}
				
				measurements.add(m);
				return null;
			}
			
			@Override
			public Void visitTuple(ITuple o) throws RuntimeException {
				TupleMeasurement m = new TupleMeasurement();
				final List<Measurement> col = m.getValue();
				
				for (IValue val : o) {
					toPongo(col, val);
				}
				
				measurements.add(m);
				return null;
			}
		});
	}

	public static StarRating toRating(IConstructor iValue) {
		switch (iValue.getName()) {
		case "one": return StarRating.ONE;
		case "two": return StarRating.TWO;
		case "three": return StarRating.THREE;
		case "four": return StarRating.FOUR;
		default:
			throw new IllegalArgumentException(iValue.toString());
		}
	}
}
