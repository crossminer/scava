{
    final Shell parent;
    final AboutDialog dialog = new AboutDialog(parent);
    dialog.create();
    if (dialog.open() == Window.OK) {
        // Do something
    }
}