{
    KnowledgePackage knowledgePackage;
    Map<String, KnowledgePackage> packages;
    Map<String, Process> processes;
    packages.put(knowledgePackage.getName(), knowledgePackage);
    for (Process process: knowledgePackage.getProcesses()) {
        processes.put(process.getId(), process);
    }
}
