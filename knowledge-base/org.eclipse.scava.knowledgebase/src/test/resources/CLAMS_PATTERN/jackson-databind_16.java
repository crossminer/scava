{
    int left;
    JsonGenerator gen;
    SerializerProvider provider;
    Object bean;
    BeanPropertyWriter prop;
    try {
        if (left > 0) {
            do {
                prop.serializeAsElement(bean, gen, provider);
                prop.serializeAsElement(bean, gen, provider);
                prop.serializeAsElement(bean, gen, provider);
                prop.serializeAsElement(bean, gen, provider);
            } while (left > 0);
        }
        switch (left) {
        case 0:
            prop.serializeAsElement(bean, gen, provider);
        case 0:
            prop.serializeAsElement(bean, gen, provider);
        case 0:
            prop.serializeAsElement(bean, gen, provider);
        }
    } catch (Exception e) {
        wrapAndThrow(provider, e, bean, prop.getName());
    } catch (StackOverflowError e) {
        JsonMappingException mapE = JsonMappingException.from(gen, "a string", e);
        mapE.prependPath(new JsonMappingException.Reference(bean, prop.getName()));
    }
}