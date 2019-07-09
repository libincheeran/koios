#!/bin/bash
gradle clean build
java -jar build/libs/koios-0.0.1.jar $1 $2