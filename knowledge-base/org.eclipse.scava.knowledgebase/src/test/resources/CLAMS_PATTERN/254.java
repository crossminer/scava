{
    ClockType clock;
    ComponentImplementationConfig cic;
    KnowledgeSessionConfiguration ksessionConfig = KnowledgeBaseFactory.newKnowledgeSessionConfiguration(getProperties(cic));
    if (clock != null) {
        switch (clock) {
        case REALTIME:
            ksessionConfig.setOption(ClockTypeOption.get(org.drools.ClockType.REALTIME_CLOCK.getId()));
            break;
        case PSEUDO:
            ksessionConfig.setOption(ClockTypeOption.get(org.drools.ClockType.PSEUDO_CLOCK.getId()));
            break;
        }
    }
}
