#!/bin/sh
docker run -it --rm -d --name crossflow -p 80:8080 -p 61616:61616 -p 61614:61614 -p 5672:5672 -p 61613:61613 -p 1883:1883 -p 8161:8161 -p 1099:1099 crossminer/crossflow:latest