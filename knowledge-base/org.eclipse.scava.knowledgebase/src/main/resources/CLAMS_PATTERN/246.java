{
    Person jack;
    Reader source;
    List<Message> myList;
    PackageBuilder builder = new PackageBuilder();
    builder.addRuleFlow(source);
    if (!builder.getErrors().isEmpty()) {
        for (DroolsError error: builder.getErrors().getErrors()) {
            // Do something
        }
    }

    Package pkg = builder.getPackage();
    RuleBase ruleBase = RuleBaseFactory.newRuleBase();
    ruleBase.addPackage( pkg );
    StatefulSession session = ruleBase.newStatefulSession();
    session.setGlobal( " a string " , myList);

    session.insert(jack);
    session.fireAllRules();
}    session.setGlobal( "  " a string "  " , myList);    session.setGlobal( "  " a string "  " , myList);}    session.setGlobal( "  "  " a string "  "  " , myList);
