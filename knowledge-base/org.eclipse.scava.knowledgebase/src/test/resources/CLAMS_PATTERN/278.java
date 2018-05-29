{
    Job job;
    WorkingMemory workingMemory;
    workingMemory.retract(workingMemory.getFactHandle(job));
    workingMemory.fireAllRules();
}
