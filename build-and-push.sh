#!/bin/bash
./mvnw clean package
scp -P 9011 target/random-meme-api-0.0.1-SNAPSHOT.jar vasylb@192.168.31.30:./docker-apps/meme-api/reddit-meme.jar