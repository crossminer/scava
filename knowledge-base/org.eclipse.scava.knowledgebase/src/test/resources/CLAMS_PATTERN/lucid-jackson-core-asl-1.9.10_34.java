{
    String exp;
    long value;
    int index;
    String act = printToString(value);

    if (!exp.equals(act)) {
        assertEquals("a string"+exp+"a string"+exp.length()+"a string"+act.length()+"a string"+index+"a string", exp, act);
    }
}