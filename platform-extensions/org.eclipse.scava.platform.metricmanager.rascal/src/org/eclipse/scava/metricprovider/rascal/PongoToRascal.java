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

import java.io.File;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import org.eclipse.scava.metricprovider.rascal.trans.model.BooleanMeasurement;
import org.eclipse.scava.metricprovider.rascal.trans.model.DatetimeMeasurement;
import org.eclipse.scava.metricprovider.rascal.trans.model.IntegerMeasurement;
import org.eclipse.scava.metricprovider.rascal.trans.model.ListMeasurement;
import org.eclipse.scava.metricprovider.rascal.trans.model.Measurement;
import org.eclipse.scava.metricprovider.rascal.trans.model.RascalMetrics;
import org.eclipse.scava.metricprovider.rascal.trans.model.RealMeasurement;
import org.eclipse.scava.metricprovider.rascal.trans.model.SetMeasurement;
import org.eclipse.scava.metricprovider.rascal.trans.model.StringMeasurement;
import org.eclipse.scava.metricprovider.rascal.trans.model.TupleMeasurement;
import org.eclipse.scava.metricprovider.rascal.trans.model.URIMeasurement;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.imp.pdb.facts.IDateTime;
import org.eclipse.imp.pdb.facts.IListWriter;
import org.eclipse.imp.pdb.facts.IMap;
import org.eclipse.imp.pdb.facts.IMapWriter;
import org.eclipse.imp.pdb.facts.ISet;
import org.eclipse.imp.pdb.facts.ISetWriter;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.type.ITypeVisitor;
import org.eclipse.imp.pdb.facts.type.Type;
import org.rascalmpl.uri.URIUtil;
import org.rascalmpl.values.ValueFactoryFactory;

public class PongoToRascal {
	private static IValueFactory VF = ValueFactoryFactory.getValueFactory();

	public static IMap makeMap(Map<String, File> foldersMap) {
		IMapWriter result = VF.mapWriter();

		for (Entry<String, File> entry : foldersMap.entrySet()) {
			result.put(VF.sourceLocation(URIUtil.assumeCorrect(entry.getKey())),
					VF.sourceLocation(entry.getValue().getAbsolutePath()));
		}

		return result.done();
	}
	
	public static ISet makeLocSet(Map<String, File> foldersMap) {
		ISetWriter result = VF.setWriter();

		for (Entry<String, File> entry : foldersMap.entrySet()) {
			result.insert(VF.sourceLocation(entry.getValue().getAbsolutePath()));
		}

		return result.done();
	}

	public static ISourceLocation makeProjectLoc(Project project) {
		try {
			return VF.sourceLocation(URIUtil.create("project", project.getName(),""));
		} catch (URISyntaxException e) {
			Rasctivator.logException("unexpected URI problem", e);
			return VF.sourceLocation(URIUtil.rootScheme("error"));
		}
	}
	public static IValue toValue(final RascalMetrics m, Type type,
			final boolean historic) {
		if (m.getMeasurements().size() == 0) {
			return null;
		}

		if (!historic) {
			return type.accept(new MeasurementsToValue(m.getMeasurements()));
		} else {
			// every metric is a date and a list value
			ISetWriter w = VF.setWriter();
			SimpleDateFormat df = new SimpleDateFormat("yyyyddmm");

			for (Measurement x : m.getMeasurements()) {
				try {
					Object date = x.getDbObject().get("__date");
					java.util.Date parse = df.parse(date.toString());
					IDateTime dt = VF.datetime(parse.getTime());
					ListMeasurement l = (ListMeasurement) x;
					
					try {
						IValue val = type.accept(new MeasurementsToValue(l.getValue()));

						if (val != null) {
							w.insert(VF.tuple(new IValue[] { dt, val }));
						}
					}
					catch (NoSuchElementException e) {
						// Ignore; may happen for the first record when there is a date but no measured values yet,
						// or on dates where just nothing happened and a metric was undefined because of this fact.
						// In this case the historic data will skip the date as well.
					}
				} 
				catch (ParseException e) {
					Rasctivator.logException("error in parsing date: "+ x.getDbObject().get("__date"), e);
				}
			}
			return w.done();
		}
	}

	private static final class MeasurementsToValue implements
			ITypeVisitor<IValue, RuntimeException> {
		private final Iterable<Measurement> m;

		private MeasurementsToValue(Iterable<Measurement> m) {
			this.m = m;
		}

		@Override
		public IValue visitAbstractData(Type arg0) throws RuntimeException {
			throw new UnsupportedOperationException();
		}

		@Override
		public IValue visitAlias(Type arg0) throws RuntimeException {
			return arg0.getAliased().accept(this);
		}

		@Override
		public IValue visitBool(Type arg0) throws RuntimeException {
			throw new UnsupportedOperationException();
		}

		@Override
		public IValue visitConstructor(Type arg0) throws RuntimeException {
			throw new UnsupportedOperationException();
		}

		@Override
		public IValue visitDateTime(Type arg0) throws RuntimeException {
			return toSingleDateTimeValue(m);
		}

		@Override
		public IValue visitExternal(Type arg0) throws RuntimeException {
			throw new UnsupportedOperationException();
		}

		@Override
		public IValue visitInteger(Type arg0) throws RuntimeException {
			return toSingleIntValue(m);
		}

		@Override
		public IValue visitList(Type arg0) throws RuntimeException {
			return toListValue(m);
		}

		@Override
		public IValue visitMap(Type arg0) throws RuntimeException {
			return toMapValue(m);
		}

		@Override
		public IValue visitNode(Type arg0) throws RuntimeException {
			throw new UnsupportedOperationException();
		}

		@Override
		public IValue visitNumber(Type arg0) throws RuntimeException {
			throw new UnsupportedOperationException();
		}

		@Override
		public IValue visitParameter(Type arg0) throws RuntimeException {
			throw new UnsupportedOperationException();
		}

		@Override
		public IValue visitRational(Type arg0) throws RuntimeException {
			throw new UnsupportedOperationException();
		}

		@Override
		public IValue visitReal(Type arg0) throws RuntimeException {
			return toSingleRealValue(m);
		}

		@Override
		public IValue visitSet(Type arg0) throws RuntimeException {
			return toSetValue(m);
		}

		@Override
		public IValue visitSourceLocation(Type arg0) throws RuntimeException {
			return toSingleLocValue(m);
		}

		@Override
		public IValue visitString(Type arg0) throws RuntimeException {
			return toSingleStringValue(m);
		}

		@Override
		public IValue visitTuple(Type arg0) throws RuntimeException {
			return toSingleTupleValue(m);
		}

		@Override
		public IValue visitValue(Type arg0) throws RuntimeException {
			throw new UnsupportedOperationException();
		}

		@Override
		public IValue visitVoid(Type arg0) throws RuntimeException {
			throw new UnsupportedOperationException();
		}

		protected IValue toSingleTupleValue(Iterable<Measurement> m) {
			return toTupleValue((TupleMeasurement) m.iterator().next());
		}

		protected IValue toSingleStringValue(Iterable<Measurement> m) {
			return toStringValue((StringMeasurement) m.iterator().next());
		}

		protected IValue toSingleLocValue(Iterable<Measurement> m) {
			return toURIValue((URIMeasurement) m.iterator().next());
		}

		protected IValue toSetValue(Iterable<Measurement> m) {
			ISetWriter writer = VF.setWriter();
			for (Measurement n : m) {
				writer.insert(toValue(n));
			}
			return writer.done();
		}

		protected IValue toSingleRealValue(Iterable<Measurement> m) {
			return toRealValue((RealMeasurement) m.iterator().next());
		}

		protected IValue toSingleIntValue(Iterable<Measurement> m) {
			return toIntValue((IntegerMeasurement) m.iterator().next());
		}

		protected IValue toSingleDateTimeValue(Iterable<Measurement> m) {
			return toDatetimeValue((DatetimeMeasurement) m.iterator().next());
		}

		protected IValue toMapValue(Iterable<Measurement> m) {
			IMapWriter writer = VF.mapWriter();
			for (Measurement n : m) {
				TupleMeasurement t = (TupleMeasurement) n;
				List<Measurement> elems = t.getValue();
				writer.put(toValue(elems.get(0)), toValue(elems.get(1)));
			}
			return writer.done();
		}

		protected IValue toListValue(Iterable<Measurement> m) {
			IListWriter writer = VF.listWriter();
			for (Measurement n : m) {
				writer.append(toValue(n));
			}
			return writer.done();
		}

		private static IValue toValue(Measurement e) {
			if (e instanceof IntegerMeasurement) {
				return toIntValue((IntegerMeasurement) e);
			} else if (e instanceof RealMeasurement) {
				return toRealValue((RealMeasurement) e);
			} else if (e instanceof StringMeasurement) {
				return toStringValue((StringMeasurement) e);
			} else if (e instanceof URIMeasurement) {
				return toURIValue((URIMeasurement) e);
			} else if (e instanceof DatetimeMeasurement) {
				return toDatetimeValue((DatetimeMeasurement) e);
			} else if (e instanceof BooleanMeasurement) {
				return toBoolValue((BooleanMeasurement) e);
			} else if (e instanceof ListMeasurement) {
				return toListValue((ListMeasurement) e);
			} else if (e instanceof SetMeasurement) {
				return toSetValue((SetMeasurement) e);
			} else if (e instanceof TupleMeasurement) {
				return toTupleValue((TupleMeasurement) e);
			}

			throw new IllegalArgumentException(e.toString());
		}

		private static IValue toDatetimeValue(DatetimeMeasurement e) {
			return VF.datetime(e.getValue(), e.getTimezoneHours(), e.getTimezoneMinutes());
		}

		private static IValue toTupleValue(TupleMeasurement e) {
			List<Measurement> col = e.getValue();
			IValue[] elems = new IValue[col.size()];

			for (int i = 0; i < elems.length; i++) {
				elems[i] = toValue(col.get(i));
			}

			return VF.tuple(elems);
		}

		private static IValue toSetValue(SetMeasurement e) {
			ISetWriter w = VF.setWriter();

			for (Measurement m : e.getValue()) {
				w.insert(toValue(m));
			}

			return w.done();
		}

		private static IValue toListValue(ListMeasurement e) {
			IListWriter w = VF.listWriter();

			for (Measurement m : e.getValue()) {
				w.insert(toValue(m));
			}

			return w.done();
		}

		private static IValue toBoolValue(BooleanMeasurement e) {
			return VF.bool(e.getValue());
		}

		private static IValue toURIValue(URIMeasurement e) {
			return VF.sourceLocation(URIUtil.assumeCorrect(e.getValue()));
		}

		private static IValue toStringValue(StringMeasurement e) {
			return VF.string(e.getValue());
		}

		private static IValue toRealValue(RealMeasurement e) {
			return VF.real(e.getValue());
		}

		private static IValue toIntValue(IntegerMeasurement e) {
			return VF.integer(e.getValue());
		}

	}

}
