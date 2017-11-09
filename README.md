# Clojure Journal

# About
* A simple REST api which stores accounting journal entries.
* Written to help me learn Clojure.

## Requirements
* [Docker](https://www.docker.com/) >= 17.06.0-c3

# Installation
```bash
// build image
docker run -it clojure_journal bash .

// run container
docker-compose up -d
```

# Usage

##### Endpoint
```
http://localhost:8000
```

##### GET /

##### POST /

###### body
```json
{
  "account_number":111,
  "credit":0,
  "debit":100
}
```

###### response
```json
{
  "credit":0.0,
  "debit":100.0,
  "timestamp":1510239819,
  "account_number":111,
  "id":"6a62d64d-27a5-487b-9bd3-41079a0fa34a"
}
```

##### GET /journal/:account_number