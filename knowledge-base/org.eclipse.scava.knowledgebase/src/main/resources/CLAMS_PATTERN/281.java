{

    final ObjectDescriptor od;
    final RelationDescriptor rd = od.getInRelations().get( number );

    final Object oldValue = od.getObject();

    Class valueType = od.getInRelations().get( number ).getRange();
    // Do something with rd

    // Do something with oldValue

    // Do something with valueType
}
