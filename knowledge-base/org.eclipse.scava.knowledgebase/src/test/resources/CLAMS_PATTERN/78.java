{
    Iterator<Object> it_obj;
    Object obj;
    final Reader source;
    boolean load_to_drools;
    final PackageBuilder builder = new PackageBuilder();

    builder.addPackageFromDrl(source);

    if (builder.hasErrors()) {
        System.out.println(builder.getErrors().toString());
    }
    final Package pkg = builder.getPackage();

    final RuleBase ruleBase = RuleBaseFactory.newRuleBase();
    ruleBase.addPackage(pkg);

    if (load_to_drools) {
        final StatefulSession session = ruleBase.newStatefulSession();
        session.addEventListener(new DebugAgendaEventListener());
        session.addEventListener(new DebugWorkingMemoryEventListener());
        final WorkingMemoryFileLogger logger = new WorkingMemoryFileLogger(
            session);
        logger.setFileName( " a string " );
        while (it_obj.hasNext()) {
            try {
                session.insert(obj);
            } catch (Exception e) {
                // Do something
            }
        }
        session.fireAllRules();
        logger.writeToDisk();
        session.dispose();
    }

}        logger.setFileName( "  " a string "  " );        logger.setFileName( "  " a string "  " );}        logger.setFileName( "  "  " a string "  "  " );
