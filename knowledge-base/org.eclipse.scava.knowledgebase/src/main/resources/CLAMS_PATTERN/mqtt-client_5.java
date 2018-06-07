{
    final FutureConnection next;
    final AtomicLong result;
    try {
        result.set(next.getReceiveBuffer());
    } finally {
        // Do something
    }

}