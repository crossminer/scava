/**
 */
package crossflow.tests;

import crossflow.CrossflowFactory;
import crossflow.ReusableComponent;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Reusable Component</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class ReusableComponentTest extends TaskTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ReusableComponentTest.class);
	}

	/**
	 * Constructs a new Reusable Component test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReusableComponentTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Reusable Component test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected ReusableComponent getFixture() {
		return (ReusableComponent)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(CrossflowFactory.eINSTANCE.createReusableComponent());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

} //ReusableComponentTest
