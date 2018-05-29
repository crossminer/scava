{
    Driver driverFemale;
    Policy policy;
    StatefulSession session;
    Driver driverMale;
    session.insert(policy);

    session.insert(driverMale);
    session.fireAllRules();

    session.insert(policy);

    session.insert(driverFemale);
    session.fireAllRules();
}
