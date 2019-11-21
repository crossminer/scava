package typeGenerationWorkflow;


public class TypeTask extends TypeTaskBase {
	
	@Override
	public GeneratedType consumeA(GeneratedType generatedType) throws Exception {
		
		GeneratedType generatedTypeInst = new GeneratedType();
		//	generatedTypeInst.setStrProp( String );
		//	generatedTypeInst.setIntProp( int );
		//	generatedTypeInst.setManyStrProp( String );
		//	generatedTypeInst.setManyIntProp( int );
		//	generatedTypeInst.setEnumProp( org.eclipse.emf.ecore.impl.EClassImpl@7223f7fb (name: EnumField) (instanceClassName: null) (abstract: false, interface: false) );
		//	generatedTypeInst.setManyEnumProp( org.eclipse.emf.ecore.impl.EClassImpl@7223f7fb (name: EnumField) (instanceClassName: null) (abstract: false, interface: false) );
		return generatedTypeInst;
	
	}


}
