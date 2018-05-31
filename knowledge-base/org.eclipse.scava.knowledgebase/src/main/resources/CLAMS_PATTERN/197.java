{
    final KnowledgeAgent kAgent;
    ClassLoader ruleLoader;
    if(ruleLoader!=null) {
        KnowledgeBaseImpl kImpl = (KnowledgeBaseImpl)kAgent.getKnowledgeBase();
        ((ReteooRuleBase)kImpl.getRuleBase()).getRootClassLoader().addClassLoader(ruleLoader);
    }
}
