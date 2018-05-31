{
    List<ContextInstance> exclusiveGroupInstances;
    Object object;
    List<String> keys;
    List<NodeInstance> nodeInstances;
    WorkflowProcessInstanceImpl workFlow;
    ObjectMarshallingStrategy strategy;
    ObjectOutputStream stream;
    Collection<NodeInstance> groupNodeInstances;
    stream.writeLong(workFlow.getId());
    Collections.sort(nodeInstances,
    new Comparator<NodeInstance>() {
        public int compare(NodeInstance o1,
        NodeInstance o2) {
            return (int) (o1.getId() - o2.getId());
        }
    });
    if (exclusiveGroupInstances == null) {
        // Do something
    } else {
        for (ContextInstance contextInstance: exclusiveGroupInstances) {
            for (NodeInstance nodeInstance: groupNodeInstances) {
                stream.writeLong(nodeInstance.getId());
            }
        }
    }
    for (String key : keys) {
        if(object != null) {
            stream.writeUTF(strategy.getClass().getName());
            strategy.write(stream, object);
        }
    }
}
