{
    TimerExpired expiration;
    WorkingMemory m_workingMemory;
    m_workingMemory.insert(expiration);
    m_workingMemory.fireAllRules();
}
