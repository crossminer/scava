{
    ObjectOutputStream stream;
    NodeInstance nodeInstance;
    stream.writeLong(nodeInstance.getId());
    stream.writeLong(nodeInstance.getNodeId());
}
