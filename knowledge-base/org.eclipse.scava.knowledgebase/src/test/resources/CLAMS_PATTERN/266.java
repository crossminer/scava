{
    final ExtensibleXmlParser parser;
    parser.endElementBuilder();
    ProcessInfo processInfo = (ProcessInfo) parser.getCurrent();
    List<Process> processes = ((ProcessBuildData) parser.getData()).getProcesses();
    for (Process p : processes) {
        if (p.getId().equals(processInfo.getProcessRef())) {
            break;
        }
    }
}
