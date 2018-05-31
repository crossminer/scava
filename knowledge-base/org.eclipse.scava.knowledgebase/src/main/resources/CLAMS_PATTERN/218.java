{

    Policy policy;
    Policy policyM;
    Policy policy2;
    Driver driver;
    StatefulKnowledgeSession ksession;
    try {
        FactHandle driverFH = ksession.insert(driver);
        FactHandle policyFH = ksession.insert(policy);
        FactHandle policy2FH = ksession.insert(policy2);
        FactHandle policyMFH = ksession.insert(policyM);
        ksession.fireAllRules();
        ksession.retract(driverFH);
        ksession.retract(policyFH);
        ksession.retract(policy2FH);
        ksession.retract(policyMFH);
    } catch (Throwable t) {
        // Do something
    }
}
