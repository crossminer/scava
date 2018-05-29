{
    Work work;
    StringBuilder xmlDump;
    if (work != null) {
        xmlDump.append( " a string "  + work.getName() +  " a string "  + EOL);
        List<ParameterDefinition> parameterDefinitions =
        new ArrayList<ParameterDefinition>(work.getParameterDefinitions());
        Collections.sort(parameterDefinitions, new Comparator<ParameterDefinition>() {
            public int compare(ParameterDefinition o1,
            ParameterDefinition o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        for (ParameterDefinition paramDefinition: parameterDefinitions) {
            DataType dataType = paramDefinition.getType();
            xmlDump.append( " a string "  + paramDefinition.getName() +  " a string "  + EOL +  " a string " );
            Object value = work.getParameter(paramDefinition.getName());
            // Do something with dataType
            // Do something with value
        }
    }
}        xmlDump.append( "  " a string "  "  + work.getName() +  "  " a string "  "  + EOL);            xmlDump.append( "  " a string "  "  + paramDefinition.getName() +  "  " a string "  "  + EOL +  "  " a string "  " );        xmlDump.append( "  " a string "  "  + work.getName() +  "  " a string "  "  + EOL);            xmlDump.append( "  " a string "  "  + paramDefinition.getName() +  "  " a string "  "  + EOL +  "  " a string "  " );}        xmlDump.append( "  "  " a string "  "  "  + work.getName() +  "  "  " a string "  "  "  + EOL);            xmlDump.append( "  "  " a string "  "  "  + paramDefinition.getName() +  "  "  " a string "  "  "  + EOL +  "  "  " a string "  "  " );
