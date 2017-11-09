
# About

## Requirements
* [Leiningen](https://github.com/technomancy/leiningen) >= 2.0.0
* [Docker](https://www.docker.com/)

# Installation
```bash
// build image
docker run -it clojure_journal bash

// run container
docker-compose up -d
```

# Usage

## Web Server
```bash
// start a web server.
lein ring server
```