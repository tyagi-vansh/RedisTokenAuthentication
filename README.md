# Token-Based Authentication with Redis

This is a Spring Boot application that demonstrates the use of token-based authentication with Redis for storing and validating user sessions. The project allows users to register, log in, update profiles, and view user details by verifying the access token.

## Features

- **User Registration**: Users can register by providing details such as name, email, mobile, age, department, and password.
- **Login**: Users can log in with their email and password. Upon successful login, they will receive a token that can be used for further requests.
- **Profile Update**: Users can update their profile details if they provide a valid token.
- **View User Details**: Users can view their profile details by providing a valid token.

## Technologies Used

- **Spring Boot**: For building the RESTful API.
- **Jedis**: For interacting with Redis to store and validate user tokens.
- **Redis**: In-memory data structure store for session management (storing login tokens).
- **MySQL**: For storing user information persistently.

## Prerequisites

- **Java 11** or higher.
- **Maven** for building the project.
- **Redis** server running on your machine or a remote server.
- **MySQL** for storing user data.
