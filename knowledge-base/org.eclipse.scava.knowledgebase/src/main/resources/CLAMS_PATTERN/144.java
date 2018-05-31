{
    List<Process> result;
    KnowledgeBase kbase = getSession().getKnowledgeBase();
    for (KnowledgePackage kpackage: kbase.getKnowledgePackages()) {
        result.addAll(kpackage.getProcesses());
    }
}
