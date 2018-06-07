{
    JsonSerializer<Object> ser;
    if (getClass() != BeanPropertyWriter.class) {
        // Do something
    }
    return new BeanPropertyWriter(this, ser);
}