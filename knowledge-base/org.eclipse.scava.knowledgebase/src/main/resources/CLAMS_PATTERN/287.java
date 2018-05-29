{
    Variable variable;
    RuleFlowProcess process;
    Map<String, Object> parameters;
    ListDataType listDataType = new ListDataType();
    ObjectDataType personDataType = new ObjectDataType();
    personDataType.setClassName( " a string " );
    listDataType.setType(personDataType);
    variable.setType(listDataType);
    personDataType = new ObjectDataType();
    personDataType.setClassName( " a string " );
    KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
    ((AbstractRuleBase) ((InternalKnowledgeBase) kbase).getRuleBase()).addProcess(process);
    StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();

    ksession.startProcess( " a string " , parameters);
}    personDataType.setClassName( "  " a string "  " );    personDataType.setClassName( "  " a string "  " );    ksession.startProcess( "  " a string "  " , parameters);    personDataType.setClassName( "  " a string "  " );    personDataType.setClassName( "  " a string "  " );    ksession.startProcess( "  " a string "  " , parameters);}    personDataType.setClassName( "  "  " a string "  "  " );    personDataType.setClassName( "  "  " a string "  "  " );    ksession.startProcess( "  "  " a string "  "  " , parameters);
