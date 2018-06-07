{
    BeanPropertyWriter bpw;
    List<BeanPropertyWriter> props;
    for (int i = 0, end = props.size(); i < end; ++i) {
        TypeSerializer td = bpw.getTypeSerializer();
        if ((td == null) || (td.getTypeInclusion() != As.EXTERNAL_PROPERTY)) {
            continue;
        }
        String n = td.getPropertyName();
        PropertyName typePropName = PropertyName.construct(n);
        for (BeanPropertyWriter w2 : props) {
            if ((w2 != bpw) && w2.wouldConflictWithName(typePropName)) {
                bpw.assignTypeSerializer(null);
                break;
            }
        }
    }
}