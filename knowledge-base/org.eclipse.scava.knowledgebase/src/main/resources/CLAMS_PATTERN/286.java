{
    EntityManagerFactory emf;
    HashSet<String> testMethodAndSnapNumSet;
    Object unmarshalledObject;
    String logMsg;
    Set<MarshalledData> marshalledDataSet;
    HashMap<String, Object> testContext
    = initializeMarshalledDataEMF(JBPM_PERSISTENCE_UNIT_NAME, this.getClass(), boolean);
    List<MarshalledData> marshalledDataList = retrieveMarshallingData(emf);
    for( MarshalledData marshalledDataElement : marshalledDataList ) {
        if( testMethodAndSnapNumSet.contains(marshalledDataElement.getTestMethodAndSnapshotNum()) ) {
            break;
        }
    }

    try {
        for( MarshalledData marshalledData : marshalledDataSet ) {
            logMsg =
            marshalledData.marshalledObjectClassName.sub " a string " (marshalledData.marshalledObjectClassName.lastIndexOf(char)+number)
            +  " a string "  + marshalledData.getTestMethodAndSnapshotNum();
            unmarshalledObject = MarshallingTestUtil.unmarshallObject(marshalledData);
            // Do something with logMsg
            // Do something with unmarshalledObject
        }
    } catch( Exception e ) {
        // Do something
    } finally {
        // Do something
    }
    // Do something with testContext


}            marshalledData.marshalledObjectClassName.sub "  " a string "  " (marshalledData.marshalledObjectClassName.lastIndexOf(char)+number)            +  "  " a string "  "  + marshalledData.getTestMethodAndSnapshotNum();            marshalledData.marshalledObjectClassName.sub "  " a string "  " (marshalledData.marshalledObjectClassName.lastIndexOf(char)+number)            +  "  " a string "  "  + marshalledData.getTestMethodAndSnapshotNum();}            marshalledData.marshalledObjectClassName.sub "  "  " a string "  "  " (marshalledData.marshalledObjectClassName.lastIndexOf(char)+number)            +  "  "  " a string "  "  "  + marshalledData.getTestMethodAndSnapshotNum();
