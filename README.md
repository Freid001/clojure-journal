# Clojure Journal

## About
* A simple REST api which stores debit & credit journal entries.
* The purpose of this project was to gain some experience writing an application in Clojure.

### Requirements
* [Docker](https://www.docker.com/) 
* [Gradle](https://gradle.org/)

### Installation
```bash
// build and run app
gradle build run

// stop app
gradle stop
```

## Usage

##### Endpoint
```
http://localhost:8000
```

##### GET /:account_number/balance

###### response
```json
{
    "balance":610.0
}
```

##### GET /:account_number/ledger

###### response
```json
[
  {
    "balance":610.0,
    "credit":90.0,
    "debit":0.0,
    "description":"supplies expense",
    "timestamp":1510434125,
    "account_number":123456789,
    "reference":"GJ1",
    "entry_id":4
  },
  {
    "balance":700.0,
    "credit":250.0,
    "debit":0.0,
    "description":"rent expense",
    "timestamp":1510434092,
    "account_number":123456789,
    "reference":"GJ1",
    "entry_id":3
  },
  {
    "balance":950.0,
    "credit":50.0,
    "debit":0.0,
    "description":"utilities expense",
    "timestamp":1510434073,
    "account_number":123456789,
    "reference":"GJ1",
    "entry_id":2
  },
  {
    "balance":1000.0,
    "credit":0.0,
    "debit":1000.0,
    "description":"cash",
    "timestamp":1510434063,
    "account_number":123456789,
    "reference":"GJ1",
    "entry_id":1
  }
]
```

##### GET /entry/:id

###### response
```json
{
  "credit":0.0,
  "debit":500.0,
  "description":"cash",
  "timestamp":1510433564,
  "account_number":123456789,
  "reference":"GJ1",
  "entry_id":1
}
```

##### PUT /entry

###### body
```json
{
  "account_number":123456789,
  "description":"cash",
  "reference":"GJ1",
  "debit":500.00
}
```

###### response
```json
{
  "credit":0.0,
  "debit":500.0,
  "description":"cash",
  "timestamp":1510433564,
  "account_number":123456789,
  "reference":"GJ1",
  "entry_id":1
}
```
