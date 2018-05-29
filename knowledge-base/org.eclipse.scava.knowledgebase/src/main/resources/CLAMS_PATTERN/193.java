{

    Order internationalUSPSOrderRejected;
    List<Order> rejectedNational;
    Order internationalExpressOrder;
    Order nationalStandardOrder;
    List<Order> priorityCustomer;
    List<Order> rejectedInternational;
    Order internationalUSPSOrderAccepted;
    StatefulKnowledgeSession ksession;
    Order nationalExpressOrder;
    ksession.setGlobal( " a string " , rejectedNational);
    ksession.setGlobal( " a string " , rejectedInternational);
    ksession.setGlobal( " a string " , priorityCustomer);

    ksession.insert(internationalExpressOrder);
    ksession.insert(internationalUSPSOrderRejected);
    ksession.insert(internationalUSPSOrderAccepted);
    ksession.insert(nationalStandardOrder);
    ksession.insert(nationalExpressOrder);
    ksession.fireAllRules();
}    ksession.setGlobal( "  " a string "  " , rejectedNational);    ksession.setGlobal( "  " a string "  " , rejectedInternational);    ksession.setGlobal( "  " a string "  " , priorityCustomer);    ksession.setGlobal( "  " a string "  " , rejectedNational);    ksession.setGlobal( "  " a string "  " , rejectedInternational);    ksession.setGlobal( "  " a string "  " , priorityCustomer);}    ksession.setGlobal( "  "  " a string "  "  " , rejectedNational);    ksession.setGlobal( "  "  " a string "  "  " , rejectedInternational);    ksession.setGlobal( "  "  " a string "  "  " , priorityCustomer);
