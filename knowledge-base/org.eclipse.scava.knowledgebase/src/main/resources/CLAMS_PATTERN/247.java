{
    Person p;
    String rule;
    List<Object> list;
    String process;
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    kbuilder.add( ResourceFactory.newReaderResource( new StringReader( rule ) ), ResourceType.DRL );
    kbuilder.add( ResourceFactory.newReaderResource( new StringReader( process ) ), ResourceType.DRF );

    KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
    kbase.addKnowledgePackages( kbuilder.getKnowledgePackages() );

    StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();

    ksession.setGlobal(  " a string " , list );

    ksession.insert( p );
    ksession.startProcess( " a string " );

    assertEquals(number, ksession.getProcessInstances().size());

    assertEquals(number, ksession.getProcessInstances().size());

    ksession.fireAllRules();

    assertEquals( number, ((List<Object>) ksession.getGlobal( " a string " )).size());
    assertEquals( p, ((List<Object>) ksession.getGlobal( " a string " )).get(number));
    assertEquals(number, ksession.getProcessInstances().size());
}    ksession.setGlobal(  "  " a string "  " , list );    ksession.startProcess( "  " a string "  " );    assertEquals( number, ((List<Object>) ksession.getGlobal( "  " a string "  " )).size());    assertEquals( p, ((List<Object>) ksession.getGlobal( "  " a string "  " )).get(number));    ksession.setGlobal(  "  " a string "  " , list );    ksession.startProcess( "  " a string "  " );    assertEquals( number, ((List<Object>) ksession.getGlobal( "  " a string "  " )).size());    assertEquals( p, ((List<Object>) ksession.getGlobal( "  " a string "  " )).get(number));}    ksession.setGlobal(  "  "  " a string "  "  " , list );    ksession.startProcess( "  "  " a string "  "  " );    assertEquals( number, ((List<Object>) ksession.getGlobal( "  "  " a string "  "  " )).size());    assertEquals( p, ((List<Object>) ksession.getGlobal( "  "  " a string "  "  " )).get(number));
