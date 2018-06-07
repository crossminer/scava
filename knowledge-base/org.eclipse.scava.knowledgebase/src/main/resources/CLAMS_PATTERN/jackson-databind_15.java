{
    int left;
    Object bean;
    SerializerProvider provider;
    BeanPropertyWriter prop;
    JsonGenerator gen;
    try {
        if (left > 0) {
            do {
                if (prop != null) {
                    prop.serializeAsField(bean, gen, provider);
                }
                if (prop != null) {
                    prop.serializeAsField(bean, gen, provider);
                }
                if (prop != null) {
                    prop.serializeAsField(bean, gen, provider);
                }
                if (prop != null) {
                    prop.serializeAsField(bean, gen, provider);
                }
            } while (left > 0);
        }
        switch (left) {
        case 0:
            if (prop != null) {
                prop.serializeAsField(bean, gen, provider);
            }
        case 0:
            if (prop != null) {
                prop.serializeAsField(bean, gen, provider);
            }
        case 0:
            if (prop != null) {
                prop.serializeAsField(bean, gen, provider);
            }
        }
    } catch (Exception e) {
        String name = (prop == null) ? "a string" : prop.getName();
        wrapAndThrow(provider, e, bean, name);
    } catch (StackOverflowError e) {
        JsonMappingException mapE = new JsonMappingException(gen, "a string", e);
        String name = (prop == null) ? "a string" : prop.getName();
        mapE.prependPath(new JsonMappingException.Reference(bean, name));
    }
}