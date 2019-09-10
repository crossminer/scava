#!/bin/bash


add_project() {
    myproj=$1
    perl scava_create_project.pl $myproj
    mytask=$(echo $myproj | sed 's/\.//g')
    perl scava_create_task.pl $mytask
    }

for project in `cat list_projects.txt`; do
    add_project $project
done
