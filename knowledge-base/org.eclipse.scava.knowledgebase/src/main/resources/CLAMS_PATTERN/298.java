{
    List<String> list;
    WorkflowProcessImpl process;
    final Package pkg = new Package(  " a string "  );

    ActionDescr actionDescr = new ActionDescr();
    actionDescr.setText(  " a string "  );

    PackageBuilder pkgBuilder = new PackageBuilder( pkg );
    DialectCompiletimeRegistry dialectRegistry = pkgBuilder.getPackageRegistry( pkg.getName() ).getDialectCompiletimeRegistry();
    JavaDialect javaDialect = ( JavaDialect ) dialectRegistry.getDialect(  " a string "  );

    ProcessDescr processDescr = new ProcessDescr();
    processDescr.setClassName(  " a string "  );
    processDescr.setName(  " a string "  );

    process.setName(  " a string "  );
    ProcessBuildContext context = new ProcessBuildContext(pkgBuilder, pkgBuilder.getPackage(), null, processDescr, dialectRegistry, javaDialect);

    pkgBuilder.addPackageFromDrl( new StringReader( " a string " ) );

    ProcessDialect dialect = ProcessDialectRegistry.getDialect(  " a string "  );
    javaDialect.compileAll();
    assertEquals( number, javaDialect.getResults().size() );

    final RuleBase ruleBase = RuleBaseFactory.newRuleBase();
    ruleBase.addPackage( pkgBuilder.getPackage() );
    final WorkingMemory wm = ruleBase.newStatefulSession();

    wm.setGlobal(  " a string " , list );

    ProcessContext processContext = new ProcessContext( ((InternalWorkingMemory) wm).getKnowledgeRuntime() );
    // Do something with context

    // Do something with dialect

    // Do something with processContext
}    final Package pkg = new Package(  "  " a string "  "  );    actionDescr.setText(  "  " a string "  "  );    JavaDialect javaDialect = ( JavaDialect ) dialectRegistry.getDialect(  "  " a string "  "  );    processDescr.setClassName(  "  " a string "  "  );    processDescr.setName(  "  " a string "  "  );    process.setName(  "  " a string "  "  );    pkgBuilder.addPackageFromDrl( new StringReader( "  " a string "  " ) );    ProcessDialect dialect = ProcessDialectRegistry.getDialect(  "  " a string "  "  );    wm.setGlobal(  "  " a string "  " , list );    final Package pkg = new Package(  "  " a string "  "  );    actionDescr.setText(  "  " a string "  "  );    JavaDialect javaDialect = ( JavaDialect ) dialectRegistry.getDialect(  "  " a string "  "  );    processDescr.setClassName(  "  " a string "  "  );    processDescr.setName(  "  " a string "  "  );    process.setName(  "  " a string "  "  );    pkgBuilder.addPackageFromDrl( new StringReader( "  " a string "  " ) );    ProcessDialect dialect = ProcessDialectRegistry.getDialect(  "  " a string "  "  );    wm.setGlobal(  "  " a string "  " , list );}    final Package pkg = new Package(  "  "  " a string "  "  "  );    actionDescr.setText(  "  "  " a string "  "  "  );    JavaDialect javaDialect = ( JavaDialect ) dialectRegistry.getDialect(  "  "  " a string "  "  "  );    processDescr.setClassName(  "  "  " a string "  "  "  );    processDescr.setName(  "  "  " a string "  "  "  );    process.setName(  "  "  " a string "  "  "  );    pkgBuilder.addPackageFromDrl( new StringReader( "  "  " a string "  "  " ) );    ProcessDialect dialect = ProcessDialectRegistry.getDialect(  "  "  " a string "  "  "  );    wm.setGlobal(  "  "  " a string "  "  " , list );
