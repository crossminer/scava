{
    final Object filterId;
    SerializerProvider provider;
    FilterProvider filters = provider.getFilterProvider();
    if (filters == null) {
        throw new JsonMappingException("a string"+filterId+"a string");
    }
    BeanPropertyFilter filter = filters.findFilter(filterId);
    // Do something with filter
}