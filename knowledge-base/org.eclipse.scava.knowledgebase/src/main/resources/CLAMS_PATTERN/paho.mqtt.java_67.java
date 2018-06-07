{
    DataInputStream dis;
    boolean end;
    String[] names;
    int count;
    while (!end) {
        try {
            names[count] = decodeUTF8(dis);
        } catch (Exception e) {
            // Do something
        }
    }
}