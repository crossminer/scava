{
    Composite composite;
    TableViewer viewer;
    Composite container;
    setControl(container);

    TableViewerBuilder builder = new TableViewerBuilder(composite).doubleClickListener(new IDoubleClickListener() {
        // Do something
    }).modelClass(History.class).input(new ArrayList<History>());

    viewer = builder.build();

    builder.columnBuilder(Messages.HISTORY_TAB_EVENT, SWT.LEFT).percentWidth(0).property(History.PROP_EVENT)
    .build();

    builder.columnBuilder(Messages.HISTORY_TAB_TOPIC, SWT.LEFT).percentWidth(0).property(History.PROP_TOPIC)
    .build();

    builder.columnBuilder(Messages.HISTORY_TAB_MSG, SWT.LEFT).percentWidth(0).property(History.PROP_MSG).build();
    builder.columnBuilder(Messages.HISTORY_TAB_QOS, SWT.CENTER).percentWidth(0).property(History.PROP_QOS)
    .format(new IValueFormatter<Enum<?>>() {
        // Do something
    }
    // Do something with viewer
}