{
    int expLen;
    boolean negative;
    int intLen;
    int fractLen;
    if (fractLen < 0 && expLen < 0) {
        return resetInt(negative, intLen);
    }
    return resetFloat(negative, intLen, fractLen, expLen);
}