{

    String path;
    final Object jsonObject;
    try {
        Configuration c = Configuration.defaultConfiguration();
        JsonPath.using(c).parse(jsonObject).read(path);
    } catch (PathNotFoundException e) {
        // Do something
    }
}