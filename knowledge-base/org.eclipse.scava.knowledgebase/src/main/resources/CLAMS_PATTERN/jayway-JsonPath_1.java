{
    final String DOCUMENT;
    List<Integer> result = JsonPath.using(Configuration.defaultConfiguration().setOptions(Option.SUPPRESS_EXCEPTIONS)).parse(DOCUMENT).read("a string");
    // Do something with result
}