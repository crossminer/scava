{
    Method m;
    Property prop = findProperty(getPropertyName(m.getName()));
    if (prop.getGetter() == null) {
        prop.setGetter(m);
    }
}