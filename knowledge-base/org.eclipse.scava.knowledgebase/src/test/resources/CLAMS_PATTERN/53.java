{
    List<Bill> bills;
    StatefulKnowledgeSession ksession;
    List<String> errors;
    ksession.setGlobal( " a string " , errors);

    for (Bill bill : bills) {
        ksession.insert(bill);
        for (Procedure procedure : bill.getProcedures()) {
            ksession.insert(procedure);
        }
    }
    ksession.fireAllRules();

    ksession.dispose();
}    ksession.setGlobal( "  " a string "  " , errors);    ksession.setGlobal( "  " a string "  " , errors);}    ksession.setGlobal( "  "  " a string "  "  " , errors);
