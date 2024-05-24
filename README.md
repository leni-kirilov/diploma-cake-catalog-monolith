# Cake-Catalog-monolith
My diploma project from 2016. To be used in combination with https://github.com/leni-kirilov/diploma-cake-catalog-auth

This is the monolithic application for registering a user and creating cakes in a catalog

Users can create and make cakes public. 
Public cakes are visible by all registered users, but editable only by owners

## How to setup

Install `sdkman` and `jenv`

```
sdk use grails 3.2.3
sdk use gradle 3.2
sdk use groovy 2.4.7
jenv use 1.8
```

(If it doesn't run, stop any gradle daemons or use `sdk default gradle 3.2`

`CREATE DATABASE cake_catalog_monolith`

## How to run

`grails run-app`

## How to run as it would be on PRD

(builds war, runs migrations, starts app-server)
`./webapp-runner-local.sh`

## Status

Master build:
[![Build Status](https://travis-ci.org/leni-kirilov/cake-catalog-monolith.svg?branch=master)](https://travis-ci.org/leni-kirilov/cake-catalog-monolith)

Heroku deployment per: 
http://webcache.googleusercontent.com/search?q=cache:yJo7bA8IfwUJ:davydotcom.com/blog/2015-05-31-running-grails-3-on-heroku+&cd=4&hl=en&ct=clnk&gl=bg
