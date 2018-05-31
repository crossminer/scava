{
    java.awt.event.KeyEvent evt;
    StatefulKnowledgeSession ksession;
    if (evt.getKeyCode() == KeyEvent.VK_D) {
        ksession.insert(new KeyD());
        ksession.fireAllRules();
    }
}
