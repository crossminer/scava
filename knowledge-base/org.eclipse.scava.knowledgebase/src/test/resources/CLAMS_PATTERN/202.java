{
    MarshallerWriteContext context;
    JBPMMessages.Variable.Builder builder;
    ObjectMarshallingStrategy strategy;
    Map<String, Object> variables;
    for(String key : variables.keySet()) {
        if(variables.get(key) != null) {
            Integer index = context.getStrategyIndex( strategy );
            builder.setStrategyIndex( index )
            .setValue( ByteString.copyFrom( strategy.marshal( context.strategyContext.get( strategy ),
            context,
            variables.get(key) ) ) );
        }
    }
}
