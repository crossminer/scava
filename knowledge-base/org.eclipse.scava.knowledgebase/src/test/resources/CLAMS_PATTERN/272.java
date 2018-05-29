{
    FileOutputStream fout;
    String rules;
    PackageBuilder builder = new PackageBuilder();
    try {
        builder.addPackageFromDrl(new StringReader(rules));
    } catch (Exception e) {
        // Do something
    }
    if(builder.hasErrors()) {
        String error =  " a string "  +
        builder.getErrors().toString();
        // Do something with error
    }

    Package pkg = builder.getPackage();

    try {
        DroolsStreamUtils.streamOut(fout, pkg);
    } catch (FileNotFoundException fnfe) {
        // Do something
    } catch (IOException ioe) {
        // Do something
    } finally {
        // Do something
    }
}        String error =  "  " a string "  "  +        String error =  "  " a string "  "  +}        String error =  "  "  " a string "  "  "  +
