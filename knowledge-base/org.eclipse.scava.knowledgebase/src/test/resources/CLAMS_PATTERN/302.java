{
    BusinessEntity businessEntity;
    BusinessEntity sessionInteractionKey;
    WorkItem wi;
    long workItemId = wi.getId();
    long processInstanceId = wi.getProcessInstanceId();
    StatefulKnowledgeSession ksession = JPAKnowledgeService.loadStatefulKnowledgeSession(sessionInteractionKey.getSessionId(), kbases.get( " a string " ), null, env);
    ksession.insert(businessEntity);
    ksession.fireAllRules();
    // Do something with workItemId

    // Do something with processInstanceId
}    StatefulKnowledgeSession ksession = JPAKnowledgeService.loadStatefulKnowledgeSession(sessionInteractionKey.getSessionId(), kbases.get( "  " a string "  " ), null, env);    StatefulKnowledgeSession ksession = JPAKnowledgeService.loadStatefulKnowledgeSession(sessionInteractionKey.getSessionId(), kbases.get( "  " a string "  " ), null, env);}    StatefulKnowledgeSession ksession = JPAKnowledgeService.loadStatefulKnowledgeSession(sessionInteractionKey.getSessionId(), kbases.get( "  "  " a string "  "  " ), null, env);
