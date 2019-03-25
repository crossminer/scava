#!/bin/bash

WORKDIR="./developmentTools/modules/openApiClientBuilding"
CLIENTDIR="$WORKDIR/client"

java -jar "$WORKDIR/swagger-codegen-cli-2.3.1.jar" generate -i "$WORKDIR/openapi.json" -l java -o "$CLIENTDIR" -c "$WORKDIR/codegen-java-config.json"

if pushd "$CLIENTDIR" ; then
    mvn clean install
    popd
    rm -rf "$CLIENTDIR"
fi

if pushd "./org.eclipse.scava.root" ; then
    mvn clean verify
    popd
fi
