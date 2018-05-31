{
    MarshallerReaderContext context;
    JBPMMessages.ProcessInstance _instance;
    InternalWorkingMemory wm;
    InternalRuleBase ruleBase;
    WorkflowProcessInstanceImpl processInstance;
    Header _header;
    String processId;
    if( _instance == null ) {
        ExtensionRegistry registry = PersisterHelper.buildRegistry( context, null );
        try {
            _header = PersisterHelper.readFromStreamWithHeader( context, registry );
        } catch ( ClassNotFoundException e ) {
            // Do something
        }
        _instance = JBPMMessages.ProcessInstance.parseFrom( _header.getPayload(), registry );
        // Do something with _instance
    }

    Process process = ruleBase.getProcess( processId );
    processInstance.setKnowledgeRuntime( wm.getKnowledgeRuntime() );
    // Do something with process
}
