{
    Class<?> type;
    return (ClassUtil.canBeABeanType(type) == null) && !ClassUtil.isProxyType(type);
}