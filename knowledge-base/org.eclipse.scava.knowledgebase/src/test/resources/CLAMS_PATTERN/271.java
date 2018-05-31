{
    ExchangePattern exchangePattern;
    Integer sessionId;
    Object processEvent;
    Long processInstanceId;
    Map<String,Object> parameters;
    Object messageContentOut;
    final StatefulKnowledgeSession ksessionStateful;
    String processEventType;
    ProcessInstance processInstance;
    String _processId;
    String _messageContentOutName;
    ProcessActionType processActionType;
    switch (processActionType) {
    case START_PROCESS:
        if (_processId != null) {
            try {
                sessionId = Integer.valueOf(ksessionStateful.getId());
                processInstance = ksessionStateful.startProcess(_processId, parameters);
                processInstanceId = Long.valueOf(processInstance.getId());
            } catch (RuntimeException re) {
                // Do something
            } finally {
                // Do something
            }
        } else {
            // Do something
        }
        break;
    case SIGNAL_EVENT:
        if (processInstanceId != null) {
            try {
                sessionId = Integer.valueOf(ksessionStateful.getId());
                ksessionStateful.signalEvent(processEventType, processEvent, processInstanceId.longValue());
            } catch (RuntimeException re) {
                // Do something
            } finally {
                // Do something
            }
        } else {
            // Do something
        }
        break;
    case ABORT_PROCESS_INSTANCE:
        if (processInstanceId != null) {
            try {
                sessionId = Integer.valueOf(ksessionStateful.getId());
                ksessionStateful.abortProcessInstance(processInstanceId.longValue());
                // Do something with sessionId
            } catch (RuntimeException re) {
                // Do something
            } finally {
                // Do something
            }
        } else {
            // Do something
        }
        break;
    }
    if (processInstanceId != null) {
        if (ExchangePattern.IN_OUT.equals(exchangePattern)) {
            try {
                if (processInstance == null) {
                    processInstance = ksessionStateful.getProcessInstance(processInstanceId.longValue());
                }
                if (processInstance != null) {
                    messageContentOut = ((WorkflowProcessInstance)processInstance).getVariable(_messageContentOutName);
                    // Do something with messageContentOut
                }
            } catch (RuntimeException re) {
                // Do something
            } finally {
                // Do something
            }
        }
    }
}
