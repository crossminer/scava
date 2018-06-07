{

    Filter feq = Filter.filter(Criteria.where("a string").eq("a string"));
    Filter fne = Filter.filter(Criteria.where("a string").ne("a string"));

    DocumentContext JsonDoc = JsonPath.parse(JSON_DOCUMENT);

    List<String> eq = JsonDoc.read("a string", feq);
    List<String> ne = JsonDoc.read("a string", fne);
    // Do something with eq

    // Do something with ne
}