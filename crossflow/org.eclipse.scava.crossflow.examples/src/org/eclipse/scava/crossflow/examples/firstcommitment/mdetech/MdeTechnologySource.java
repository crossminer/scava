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
			StringStringTuple extensionKeywordTuple = new StringStringTuple();
			extensionKeywordTuple.field0 = m.getExtension();
			extensionKeywordTuple.field1 = m.getKeyword();
			getMdeTechnologies().send(extensionKeywordTuple);
			System.out.println("SENT: " + extensionKeywordTuple);
		}

	}

}
