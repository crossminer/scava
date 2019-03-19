#!/bin/sh
# REQUIRED TOOLS: webpack, typescript, yarn (install e.g. using 'brew install')

# remove previous build product if existing
rm -r -f WebContent/lib
rm -r -f build

# install
yarn install

# build
yarn run build
