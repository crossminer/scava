{
    SmileGenerator gen;
    gen.writeBoolean(boolean);
    gen.close();
    gen.writeBoolean(boolean);
    gen.close();
    gen.writeNull();
    gen.close();
    gen.writeBoolean(boolean);
    gen.close();

    gen.enable(SmileGenerator.Feature.WRITE_END_MARKER);
    gen.writeNull();
    gen.close();
}