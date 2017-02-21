#!/bin/bash
cd xgrow_new
make
cd ..
javac *.java
java dnaImagePro
