#!/usr/bin/env bash
if [ "$ENV" = "dev" ];
then
exec java -jar target/bookstore-1.0-SNAPSHOT.jar --spring.profiles.active=dev
fi