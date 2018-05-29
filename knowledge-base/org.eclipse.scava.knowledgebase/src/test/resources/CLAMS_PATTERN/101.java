{
    try {
        ProcessContext context = new ProcessContext(getProcessInstance().getKnowledgeRuntime());
        context.setNodeInstance(this);
    } catch (Exception e) {
        // Do something
    }
}
