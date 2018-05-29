{
    InternalKnowledgeRuntime kruntime;
    RuleBase ruleBase = ((InternalKnowledgeBase) kruntime.getKnowledgeBase()).getRuleBase();
    if (ruleBase != null) {
        return ((InternalRuleBase) ((InternalKnowledgeBase) kruntime.getKnowledgeBase()).getRuleBase()).getRootClassLoader();
    }
    CompositeClassLoader result = new CompositeClassLoader();
    result.addClassLoader(this.getClass().getClassLoader());
}
