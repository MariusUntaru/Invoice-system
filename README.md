# Noticeboard CRUD application

![Master Branch](https://github.com/MariusUntaru/Invoice-system/workflows/Master%20Branch/badge.svg) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=wkrzywiec_NoticeBoard&metric=coverage)](https://sonarcloud.io/dashboard?id=wkrzywiec_NoticeBoard) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=wkrzywiec_NoticeBoard&metric=alert_status)](https://sonarcloud.io/dashboard?id=wkrzywiec_NoticeBoard)

This is a simple RESTful CRUD (Create Read Update Delete) application for managing Boards, Invoices and Vendors saved in PostgreSQL database. It provides basic REST endpoints like fetching all objects of given type, finding them by their id, creating them and so on.

## Usage

An application expose 5 REST endpoints for each entity. For example *Notice* (and any other) they are:

* **GET** `{baseURL}/notices/` - lists all *Notices* (as Json array),
* **GET** `{baseURL}/notices/{id}` - gets single *Notice* (as Json) by its `{id}`,
* **POST** `{baseURL}/notices/` - creates a new *Notice* which is passed in the BODY of the request,
* **PUT** `{baseURL}/notices/{id}` - updates an existing *Notice* (with an `{id}`) with `Notice` passed in the body of the request,
* **DELETE** `{baseURL}/notices/{id}`- deletes an existing *Notice* by its `{id}`.

The `{baseUrl}` would be `http://localhost:8080`. 

All available endpoints are listed on *Swagger UI* page which can be entered, when application is running, under *http://localhost:8080/swagger-ui.html* URL.
