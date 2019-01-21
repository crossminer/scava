#!/bin/bash

java -jar "./developmentTools/modules/openApiClientBuilding/swagger-codegen-cli-2.3.1.jar" generate -i "./developmentTools/modules/openApiClientBuilding/openapi.json" -l java -o "./developmentTools/modules/openApiClientBuilding/client" -c "./developmentTools/modules/openApiClientBuilding/codegen-java-config.json"

cd "./developmentTools/modules/openApiClientBuilding/client/"

mvn clean install

cd "../../../.."

rm -rf "./developmentTools/modules/openApiClientBuilding/client"

cd "./org.eclipse.scava.root"

mvn clean verify