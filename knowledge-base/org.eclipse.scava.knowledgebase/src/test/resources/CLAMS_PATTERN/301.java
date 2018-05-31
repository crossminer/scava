{
    ActivationCreatedEvent event;
    String milestoneName;
    String ruleFlowGroup = ((Rule) event.getActivation().getRule()).getRuleFlowGroup();
    if ( " a string " .equals(ruleFlowGroup)) {
        String ruleName = event.getActivation().getRule().getName();
        if (milestoneName.equals(ruleName) && checkProcessInstance((Activation) event.getActivation())) {
            if ( !((InternalKnowledgeRuntime) getProcessInstance().getKnowledgeRuntime()).getActionQueue().isEmpty() ) {
                ((InternalKnowledgeRuntime) getProcessInstance().getKnowledgeRuntime()).executeQueuedActions();
            }
            synchronized(getProcessInstance()) {
                // Do something
            }
        }
    }
}    if ( "  " a string "  " .equals(ruleFlowGroup)) {    if ( "  " a string "  " .equals(ruleFlowGroup)) {}    if ( "  "  " a string "  "  " .equals(ruleFlowGroup)) {
