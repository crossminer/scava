{
    ArrayList<JsonNode> _children;
    ArrayNode other;
    int len = other.size();
    if (len > 0) {
        other.addContentsTo(_children);
    }
}