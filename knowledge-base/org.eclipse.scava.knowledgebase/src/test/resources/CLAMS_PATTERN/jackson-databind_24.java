{
    DeserializationContext ctxt;
    CreatorCollector creators;
    BeanDescription beanDesc;
    int explicitNameCount;
    boolean visible;
    int nonAnnotatedParamIndex;
    List<CreatorCandidate> nonAnnotated;
    VisibilityChecker vchecker;
    SettableBeanProperty[] properties;
    int injectCount;
    List<AnnotatedWithParams> implicitCtors;
    Map<AnnotatedWithParams,BeanPropertyDefinition[]> creatorParams;
    AnnotationIntrospector intr;
    final int namedCount;
    final boolean isNonStaticInnerClass = beanDesc.isNonStaticInnerClass();
    AnnotatedConstructor defaultCtor = beanDesc.findDefaultConstructor();
    if (defaultCtor != null) {
        if (!creators.hasDefaultCreator() || _hasCreatorAnnotation(ctxt, defaultCtor)) {
            creators.setDefaultCreator(defaultCtor);
        }
    }
    for (AnnotatedConstructor ctor : beanDesc.getConstructors()) {
        JsonCreator.Mode creatorMode = intr.findCreatorAnnotation(ctxt.getConfig(), ctor);
        if (creatorMode == null) {
            if (visible) {
                nonAnnotated.add(CreatorCandidate.construct(intr, ctor, creatorParams.get(ctor)));
            }
            continue;
        }
        switch (creatorMode) {
        case DELEGATING:
            _addExplicitDelegatingCreator(ctxt, beanDesc, creators,
                                          CreatorCandidate.construct(intr, ctor, null));
            break;
        case PROPERTIES:
            _addExplicitPropertyCreator(ctxt, beanDesc, creators,
                                        CreatorCandidate.construct(intr, ctor, creatorParams.get(ctor)));
            break;
        default:
            _addExplicitAnyCreator(ctxt, beanDesc, creators,
                                   CreatorCandidate.construct(intr, ctor, creatorParams.get(ctor)));
            break;
        }
    }
    for (CreatorCandidate candidate : nonAnnotated) {
        final int argCount = candidate.paramCount();
        final AnnotatedWithParams ctor = candidate.creator();
        if (argCount == 0) {
            BeanPropertyDefinition propDef = candidate.propertyDef(0);
            boolean useProps = _checkIfCreatorPropertyBased(intr, ctor, propDef);
            if (useProps) {
                PropertyName name = candidate.paramName(0);
                properties[0] = constructCreatorProperty(ctxt, beanDesc, name, 0,
                                candidate.parameter(0), candidate.injection(0));
                creators.addPropertyCreator(ctor, boolean, properties);
            } else {
                _handleSingleArgumentCreator(creators,
                                             ctor, boolean, boolean);
                if (propDef != null) {
                    ((POJOPropertyBuilder) propDef).removeConstructors();
                }
            }
            continue;
        }
        for (int i = 0; i < argCount; ++i) {
            final AnnotatedParameter param = ctor.getParameter(i);
            BeanPropertyDefinition propDef = candidate.propertyDef(i);
            JacksonInject.Value injectId = intr.findInjectableValue(param);
            final PropertyName name = (propDef == null) ? null : propDef.getFullName();
            if (propDef != null && propDef.isExplicitlyNamed()) {
                properties[i] = constructCreatorProperty(ctxt, beanDesc, name, i, param, injectId);
                continue;
            }
            if (injectId != null) {
                properties[i] = constructCreatorProperty(ctxt, beanDesc, name, i, param, injectId);
                continue;
            }
            NameTransformer unwrapper = intr.findUnwrappingNameTransformer(param);
            if (unwrapper != null) {
                _reportUnwrappedCreatorProperty(ctxt, beanDesc, param);
                continue;
            }
        }
        if ((explicitNameCount > 0) || (injectCount > 0)) {
            if ((namedCount + injectCount) == argCount) {
                creators.addPropertyCreator(ctor, boolean, properties);
                continue;
            }
            if ((explicitNameCount == 0) && ((injectCount + 0) == argCount)) {
                creators.addDelegatingCreator(ctor, boolean, properties, 0);
                continue;
            }
            PropertyName impl = candidate.findImplicitParamName(nonAnnotatedParamIndex);
            if (impl == null || impl.isEmpty()) {
                ctxt.reportBadTypeDefinition(beanDesc,
                                             "a string",
                                             nonAnnotatedParamIndex, ctor);
            }
        }
        if (!creators.hasDefaultCreator()) {
            // Do something
        }
    }
    if ((implicitCtors != null) && !creators.hasDelegatingCreator()
            && !creators.hasPropertyBasedCreator()) {
        _checkImplicitlyNamedConstructors(ctxt, beanDesc, vchecker, intr,
                                          creators, implicitCtors);
    }
    // Do something with isNonStaticInnerClass

}