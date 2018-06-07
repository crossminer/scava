{
    InputAccessor acc;
    MatchStrength strength;
    final static byte UTF8_BOM_1;
    final static byte UTF8_BOM_2;
    final static byte UTF8_BOM_3;
    if (!acc.hasMoreBytes()) {
        // Do something
    }
    byte b = acc.nextByte();
    if (b == UTF8_BOM_1) {
        if (!acc.hasMoreBytes()) {
            // Do something
        }
        if (acc.nextByte() != UTF8_BOM_2) {
            // Do something
        }
        if (!acc.hasMoreBytes()) {
            // Do something
        }
        if (acc.nextByte() != UTF8_BOM_3) {
            // Do something
        }
        if (!acc.hasMoreBytes()) {
            // Do something
        }
        b = acc.nextByte();
    }
    int ch = skipSpace(acc, b);
    if (ch == "c") {
        ch = skipSpace(acc);
    }
    if (ch == "c") {
        ch = skipSpace(acc);
    } else {
        // Do something
    }

    if (ch == "c") {
        ch = skipSpace(acc);
    }
    if (ch == "c") {
        return tryMatch(acc, "a string", strength);
    }
    if (ch == "c") {
        return tryMatch(acc, "a string", strength);
    }
    if (ch == "c") {
        return tryMatch(acc, "a string", strength);
    }
}