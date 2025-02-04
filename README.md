# Online Polling System - API Documentation

## Table of Contents

1. [Introduction](#introduction)
2. [API Overview](#api-overview)
3. [Authentication](#authentication)
4. [Postman Testing](#postman-testing)
    - [Create Poll](#create-poll)
    - [Get Poll Details](#get-poll-details)
    - [Cast Vote](#cast-vote)
5. [Error Handling](#error-handling)
6. [Contact](#contact)

---

## Introduction

This document provides a comprehensive guide to the API endpoints for the Online Polling System.  It explains how to use the API to create polls, cast votes, and retrieve poll details.  This guide focuses on using Postman for testing the API.

---

## API Overview

The Online Polling System allows users to:

- **Create Polls** with multiple options.
- **Cast Votes** on existing polls.
- **View Poll Details** and voting statistics.
- Uses **JWT Authentication** to ensure secure access to the system.

---

## Authentication

Before testing any endpoints in Postman, you must authenticate and obtain a JWT (JSON Web Token).

1. **Login or Register:**
   - **Method**: `POST`
   - **Endpoint**: `/auth/login` (or `/auth/register` for new users)
   - **Body** (Example - for login):
     ```json
     {
       "username": "testuser",
       "password": "password123"
     }
     ```
   - **Response** (Successful Login):
     ```json
     {
       "token": "<your-jwt-token>"
     }
     ```

2. **Using the JWT Token:**
   For all other endpoints (except login/registration), include the `Authorization` header in your requests with the value:

   ## Postman Testing

This section provides detailed instructions on how to test the API endpoints using Postman.

### 1. Create Poll

- **Method**: `POST`
- **Endpoint**: `/poll/create`
- **Headers**:
 - `Authorization`: `Bearer <your-jwt-token>`
 - `Content-Type`: `application/json` (Important!)
- **Body** (Example):
 ```json
 {
   "title": "Favorite Programming Language",
   "description": "Select your favorite programming language.",
   "options": ["Java", "Python", "JavaScript"]
 }
 ```
- **Response** (Successful Creation):
 ```json
 {
   "id": 1,
   "title": "Favorite Programming Language",
   "description": "Select your favorite programming language.",
   "options": ["Java", "Python", "JavaScript"]
 }
 ```

### 2. Get Poll Details

- **Method**: `GET`
- **Endpoint**: `/poll/{poll_id}`  (Replace `{poll_id}` with the actual ID of the poll you want to view.)
- **Headers**:
 - `Authorization`: `Bearer <your-jwt-token>`
- **Response** (Example):
 ```json
 {
   "id": 1,
   "title": "Favorite Programming Language",
   "description": "Select your favorite programming language.",
   "options": ["Java", "Python", "JavaScript"],
   "votes": {  // Example: Add votes object to response
       "Java": 5,
       "Python": 10,
       "JavaScript": 7
   }
 }
 ```

### 3. Cast Vote

- **Method**: `POST`
- **Endpoint**: `/vote/cast/{poll_id}` (Replace `{poll_id}` with the ID of the poll you want to vote on.)
- **Headers**:
 - `Authorization`: `Bearer <your-jwt-token>`
 - `Content-Type`: `application/json` (Important!)
- **Body** (Example):
 ```json
 {
   "option": "Java"
 }
 ```
- **Response** (Successful Vote):
 ```json
 {
   "message": "Vote cast successfully!"
 }
 ```

---

## Error Handling

The API will return appropriate HTTP status codes and error messages in case of invalid requests or server errors.  Common error codes include:

- `400 Bad Request`: Invalid request parameters or body.
- `401 Unauthorized`: Missing or invalid JWT token.
- `404 Not Found`: Resource not found.
- `500 Internal Server Error`: Server error.

---

## Contact 

 For any questions regarding the API, please get in touch with basu.debmusic20@gmail.com.
