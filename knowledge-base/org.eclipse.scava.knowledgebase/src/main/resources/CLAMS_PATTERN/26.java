{
    Policy policy;
    StatefulSession session;
    Driver driverMale;
    session.insert(policy);

    session.insert(driverMale);
    session.fireAllRules();
}
