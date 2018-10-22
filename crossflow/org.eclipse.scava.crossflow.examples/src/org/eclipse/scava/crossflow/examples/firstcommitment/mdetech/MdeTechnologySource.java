/**
 * 
 */
package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

public class MdeTechnologySource extends MdeTechnologySourceBase {

	/**
	 * 
	 */
	public MdeTechnologySource() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see org.eclipse.scava.crossflow.examples.firstcommitment.mdetech.MdeTechnologySourceBase#produce()
	 */
	@Override
	public void produce() {
		// Use MDE extension-keyword tuple from enum to produce the same tuple in form of a String-String tuple for GhRepoRetriever to consume		
		for (MDE m : MDE.values()) {
			ExtensionKeywordTuple extensionKeywordTuple = new ExtensionKeywordTuple();
			extensionKeywordTuple.field0 = m.getExtension();
			extensionKeywordTuple.field1 = m.getKeyword();
			//System.out.println("Produced tuple: " + extensionKeywordTuple.getField0() + ", " + extensionKeywordTuple.getField1());
			getMdeTechnologies().send(extensionKeywordTuple);
		}

	}

}
