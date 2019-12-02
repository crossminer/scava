/**
 */
package crossflow.tests;

import crossflow.CrossflowFactory;
import crossflow.Serializer;

import junit.framework.TestCase;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Serializer</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class SerializerTest extends TestCase {

	/**
	 * The fixture for this Serializer test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Serializer fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(SerializerTest.class);
	}

	/**
	 * Constructs a new Serializer test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SerializerTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Serializer test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(Serializer fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Serializer test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Serializer getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(CrossflowFactory.eINSTANCE.createSerializer());
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

} //SerializerTest
