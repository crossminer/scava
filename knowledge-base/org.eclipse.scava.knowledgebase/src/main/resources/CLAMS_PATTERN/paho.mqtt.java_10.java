{
    IMqttToken token;
    String topicFilter;
    onPublish myOnPublish;
    int testno;
    final int messageCount;
    int count;
    final String methodName = Utility.getMethodName();
    if (testno == 0) {
        try {
            if (++count < messageCount) {
                token.getClient().publish(topicFilter, "a string".getBytes(), 0, boolean, null, myOnPublish);
            } else {
                // Do something
            }
        } catch (Exception exception) {
            // Do something
        }
    } else {
        // Do something
    }
    // Do something with methodName


}