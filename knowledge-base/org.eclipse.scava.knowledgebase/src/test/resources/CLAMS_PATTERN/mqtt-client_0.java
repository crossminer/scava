{
    final CallbackConnection next;
    final Promise<Void> future = new Promise<Void>();
    next.getDispatchQueue().execute(new Task() {
        // Do something
    });
    // Do something with future
}