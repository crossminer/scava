{
    WorkItemManager manager;
    Map<String, Object> results;
    WorkItem workItem;
    String i = (String) workItem.getParameter( " a string " );
    String operation = (String) workItem.getParameter( " a string " );
    String parameterType = (String) workItem.getParameter( " a string " );
    Object parameter = workItem.getParameter( " a string " );
    try {
        manager.completeWorkItem(workItem.getId(), results);
    } catch (ClassNotFoundException e) {
        // Do something
    } catch (InstantiationException e) {
        // Do something
    } catch (IllegalAccessException e) {
        // Do something
    } catch (NoSuchMethodException e) {
        // Do something
    } catch (InvocationTargetException e) {
        // Do something
    }
    // Do something with i

    // Do something with operation

    // Do something with parameterType

    // Do something with parameter

}    String i = (String) workItem.getParameter( "  " a string "  " );    String operation = (String) workItem.getParameter( "  " a string "  " );    String parameterType = (String) workItem.getParameter( "  " a string "  " );    Object parameter = workItem.getParameter( "  " a string "  " );    String i = (String) workItem.getParameter( "  " a string "  " );    String operation = (String) workItem.getParameter( "  " a string "  " );    String parameterType = (String) workItem.getParameter( "  " a string "  " );    Object parameter = workItem.getParameter( "  " a string "  " );}    String i = (String) workItem.getParameter( "  "  " a string "  "  " );    String operation = (String) workItem.getParameter( "  "  " a string "  "  " );    String parameterType = (String) workItem.getParameter( "  "  " a string "  "  " );    Object parameter = workItem.getParameter( "  "  " a string "  "  " );
