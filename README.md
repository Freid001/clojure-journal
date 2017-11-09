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

##### GET /journal/:account_number/balance

###### response
```json
{
    "total_balance": 0,
    "total_credit": 100,
    "total_debit": 100
}
```

##### GET /journal/:account_number/entries

###### response
```json
[
  {
    "credit":0.0,
    "debit":100.0,
    "timestamp":1510247774,
    "account_number":111,
    "id":"dd40014f-bc12-487c-84cb-0b8c2c0c4e4b"
  },
  {
    "credit":100.0,
    "debit":0.0,
    "timestamp":1510239819,
    "account_number":111,
    "id":"9d15750b-7f3d-4908-8d8c-5fcc42087030"
  }
]
```

##### GET /journal/entry/:id

###### response
```json
{
  "credit":0.0,
  "debit":100.0,
  "timestamp":1510239819,
  "account_number":111,
  "id":"dd40014f-bc12-487c-84cb-0b8c2c0c4e4b"
}
```

##### PUT /journal

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
  "id":"dd40014f-bc12-487c-84cb-0b8c2c0c4e4b"
}
```