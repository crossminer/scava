{
    CharsToNameCanonicalizer child;
    int _longestCollisionList;
    final static int MAX_ENTRIES_FOR_REUSE;
    final static int MAX_COLL_CHAIN_FOR_REUSE;
    final int DEFAULT_TABLE_SIZE;
    final int size;
    if (child.size() > MAX_ENTRIES_FOR_REUSE
    || child._longestCollisionList > MAX_COLL_CHAIN_FOR_REUSE) {
        synchronized (this) {
            initTables(DEFAULT_TABLE_SIZE);
        }
    } else {
        if (child.size() <= size()) {
            // Do something
        }
        synchronized (this) {
            // Do something
        }
    }
}