#!/bin/bash

echo "Hello"
repos="/Users/blue/repos/balab/puppet-tutorial"
for r in $repos;
do
  echo $r
  /Users/blue/exe/pmd-bin-6.3.0/bin/run.sh cpd --minimum-tokens 150 --files $r --language pp --format xml >> $r/cpd.xml
done
