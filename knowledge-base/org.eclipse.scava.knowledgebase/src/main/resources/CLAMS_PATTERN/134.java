{
    WorkItem wi;
    WorkItemManager wim;
    try {
        // Do something
    } catch (InterruptedException ex) {
        Logger.getLogger(ProcessAndEventMultiThreadIntegrationTest.class.getName()).log(Level.SEVERE, null, ex);
    }
    System.out.println( " a string "  + wi.getName() +  " a string "  + wi.getId());
    wim.completeWorkItem(wi.getId(), null);
}    System.out.println( "  " a string "  "  + wi.getName() +  "  " a string "  "  + wi.getId());    System.out.println( "  " a string "  "  + wi.getName() +  "  " a string "  "  + wi.getId());}    System.out.println( "  "  " a string "  "  "  + wi.getName() +  "  "  " a string "  "  "  + wi.getId());
