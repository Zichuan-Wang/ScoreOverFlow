#!/bin/sh

sed -i "s/KEY/$DBKey/g" src/main/resources/META-INF/persistence.xml
sed -i "s/KEY/$EmailKey/g" src/main/resources/email.properties