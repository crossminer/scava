{
    Coords coords;
    boolean batch;
    SkeletonJoint joint;
    WorkingMemoryEntryPoint headEP;
    WorkingMemoryEntryPoint leftHandEP;
    StatefulKnowledgeSession kSession;
    WorkingMemoryEntryPoint rightKneeEP;
    WorkingMemoryEntryPoint rightHipEP;
    WorkingMemoryEntryPoint leftShoulderEP;
    WorkingMemoryEntryPoint rightHandEP;
    WorkingMemoryEntryPoint rightFootEP;
    WorkingMemoryEntryPoint leftHipEP;
    WorkingMemoryEntryPoint leftElbowEP;
    WorkingMemoryEntryPoint neckEP;
    WorkingMemoryEntryPoint rightElbowEP;
    WorkingMemoryEntryPoint torsoEP;
    WorkingMemoryEntryPoint leftKneeEP;
    WorkingMemoryEntryPoint leftFootEP;
    WorkingMemoryEntryPoint rightShoulderEP;
    switch ( joint.ordinal() ) {
    case number :
        headEP.insert( coords );
        break;
    case number :
        neckEP.insert( coords );
        break;
    case number :
        torsoEP.insert( coords );
        break;
    case number :
        leftShoulderEP.insert( coords );
        break;
    case number :
        leftElbowEP.insert( coords );
        break;
    case number :
        leftHandEP.insert( coords );
        break;
    case number :
        rightShoulderEP.insert( coords );
        break;
    case number :
        rightElbowEP.insert( coords );
        break;
    case number :
        rightHandEP.insert( coords );
        break;
    case number :
        leftHipEP.insert( coords );
        break;
    case number :
        leftKneeEP.insert( coords );
        break;
    case number :
        leftFootEP.insert( coords );
        break;
    case number :
        rightHipEP.insert( coords );
        break;
    case number :
        rightKneeEP.insert( coords );
        break;
    case number :
        rightFootEP.insert( coords );
        break;
    default :
    }

    if ( ! batch ) {
        kSession.fireAllRules();
    }
}
