#!/bin/bash


add_project() {
    myproj=$1
    myid=$(echo $myproj | cut -d, -f1)
    perl scava_create_project.pl $myid
    mytask=$(echo $myid | sed 's/\.//g')
    mystart=$(echo $myproj | cut -d, -f2)
    mystop=$(echo $myproj | cut -d, -f3)
    mytype=$(echo $myproj | cut -d, -f4)
    perl scava_create_task.pl $mytask $mystart $mystop $mytype
    }

for project in `cat list_projects.txt`; do
    add_project $project
done
