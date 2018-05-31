{
    String processId;
    String directory;
    File file;
    List<DiagramNodeInfo> nodeList;
    KnowledgeBase kbase;
    if (kbase == null) {
        if (kbase == null) {
            kbase = KnowledgeBaseFactory.newKnowledgeBase();
        }
        if (directory == null) {
            // Do something
        } else {
            ProcessBuilderFactory.setProcessBuilderFactoryService(new ProcessBuilderFactoryServiceImpl());
            ProcessMarshallerFactory.setProcessMarshallerFactoryService(new ProcessMarshallerFactoryServiceImpl());
            ProcessRuntimeFactory.setProcessRuntimeFactoryService(new ProcessRuntimeFactoryServiceImpl());
            BPMN2ProcessFactory.setBPMN2ProcessProvider(new BPMN2ProcessProviderImpl());
            KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
            for (File subfile: file.listFiles(new FilenameFilter() {
            // Do something
        })) {
                kbuilder.add(ResourceFactory.newFileResource(subfile), ResourceType.BPMN2);
            }
            kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
        }
    }
    Process process = kbase.getProcess(processId);
    if (process instanceof WorkflowProcess) {
        addNodesInfo(nodeList, ((WorkflowProcess) process).getNodes(),  " a string " );
    }
}        addNodesInfo(nodeList, ((WorkflowProcess) process).getNodes(),  "  " a string "  " );        addNodesInfo(nodeList, ((WorkflowProcess) process).getNodes(),  "  " a string "  " );}        addNodesInfo(nodeList, ((WorkflowProcess) process).getNodes(),  "  "  " a string "  "  " );
