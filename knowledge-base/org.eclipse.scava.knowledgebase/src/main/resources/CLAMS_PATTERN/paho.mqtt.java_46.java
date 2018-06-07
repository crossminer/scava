{
    BasicSyncTestCaseMIDP testCase;
    final int BUTTON1_PIN_ID;
    GPIOPin pin;
    if(pin.getID() == BUTTON1_PIN_ID ) {
        try {
            testCase.setUpBeforeClass();
            testCase.testConnect();
            testCase.testPubSub();
            testCase.tearDownAfterClass();
        } catch (Exception ex) {
            // Do something
        }
    }
}