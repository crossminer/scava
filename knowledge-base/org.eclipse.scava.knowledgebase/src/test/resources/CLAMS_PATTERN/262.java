{
    ProcessNodeTriggeredEvent event;
    try {
        NodeInstanceLog log = new NodeInstanceLog(
            NodeInstanceLog.TYPE_ENTER,
            event.getProcessInstance().getId(),
            event.getProcessInstance().getProcessId(),
            String.valueOf(event.getNodeInstance().getId()),
            getuniqueNodeid(event.getNodeInstance().getNode()),
            event.getNodeInstance().getNodeName());
        // Do something with log
    } catch (Exception e) {
        // Do something
    }
}
