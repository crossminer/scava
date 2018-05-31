{
    ObjectMarshallingStrategy[] strats;
    Object data;
    ByteArrayInputStream bs;
    ObjectMarshallingStrategy selectedStrat;
    String type;
    Environment env;
    byte[] content;
    Object unmarshalledObj;
    ContentMarshallerContext marshallerContext;
    if (env != null && env.get(EnvironmentName.OBJECT_MARSHALLING_STRATEGIES) != null) {
        strats = (ObjectMarshallingStrategy[]) env.get(EnvironmentName.OBJECT_MARSHALLING_STRATEGIES);
    } else  else {
            strats[number] = new SerializablePlaceholderResolverStrategy(ClassObjectMarshallingStrategyAcceptor.DEFAULT);
        }
    for (ObjectMarshallingStrategy strat : strats) {
        if (strat.getClass().getCanonicalName().equals(type)) {
            // Do something
        }
    }
    Context context = marshallerContext.strategyContext.get(selectedStrat.getClass());
    try {
        if (marshallerContext.isUseMarshal()) {
            data = selectedStrat.unmarshal(context, null, content, ContentMarshallerHelper.class.getClassLoader());
        } else {
            ObjectInputStream oIn = new ObjectInputStream(bs) {
                @Override
                protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
                    Class<?> clazz = Classes.forName(desc.getName(), getClass());
                }
            };
            data = selectedStrat.read(oIn);
        }
        if (data instanceof Map) {
            for (Object key : ((Map)data).keySet()) {
                MarshalledContentWrapper value = (MarshalledContentWrapper) ((Map)data).get(key);
                for (ObjectMarshallingStrategy strat : strats) {
                    if (strat.getClass().getCanonicalName().equals(value.getMarshaller())) {
                        // Do something
                    }
                }
                context = marshallerContext.strategyContext.get(selectedStrat.getClass());
                if (marshallerContext.isUseMarshal()) {
                    unmarshalledObj = selectedStrat.unmarshal(context, null, value.getContent(), ContentMarshallerHelper.class.getClassLoader());
                } else {
                    oIn = new ObjectInputStream(bs) {
                        @Override
                        protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
                            Class<?> clazz = Classes.forName(desc.getName(), getClass());
                            // Do something with clazz
                        }
                    };
                    unmarshalledObj = selectedStrat.read(oIn);
                    // Do something with unmarshalledObj
                }
            }
        }
    } catch (IOException ex) {
        // Do something
    } catch (ClassNotFoundException ex) {
        // Do something
    }
}
