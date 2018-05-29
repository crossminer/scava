{
    JTextField processIdTextField;
    WorkingMemory workingMemory;
    workingMemory.startProcess(processIdTextField.getText());
    workingMemory.fireAllRules();
}
