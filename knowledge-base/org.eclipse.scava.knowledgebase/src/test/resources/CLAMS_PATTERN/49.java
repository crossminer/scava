{

    Policy policy;
    StatefulSession session;
    Driver driver;
    DriverAdditionalInfo driverAdditional;
    session.insert( policy );
    session.insert( driver );
    session.insert( driverAdditional );

    session.fireAllRules();
}
