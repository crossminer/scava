{
    final CallbackConnection next;
    final UTF8Buffer[] topics;
    final Promise<Void> future = new Promise<Void>();
    next.getDispatchQueue().execute(new Task() {
        public void run() {
            next.unsubscribe(topics, future);
        }
    });
}