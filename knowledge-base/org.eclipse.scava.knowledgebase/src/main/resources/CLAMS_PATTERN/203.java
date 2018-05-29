{
    boolean includeVariables;
    WorkItem workItem;
    ObjectOutputStream stream;
    stream.writeLong( workItem.getId() );
    stream.writeLong( workItem.getProcessInstanceId() );
    stream.writeUTF( workItem.getName() );
    stream.writeInt( workItem.getState() );

    if(includeVariables) {
        Map<String, Object> parameters = workItem.getParameters();
        // Do something with parameters
    }
}
