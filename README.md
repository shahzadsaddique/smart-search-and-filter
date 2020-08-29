# Smart Search and Filter on Movies Data
A small web service for smart search and filter, implemented using Java, Spring boot, Spring data, Hibernate and related technology stack.

Liquibase is used for designing and creating the required database components i.e tables, relations, keys.

**Features**
1. Import movies data from a CSV file
2. Advance search and filters, on a single value, multiple values, on a number, range of numbers. Search and filter implemented using spring data search specification.

## Getting started

**Prerequisites**

1. JDK 1.8
2. Maven
3. MySQL Database
    
**Database configurations**

Before starting the application please create a mysql user and database, Login to mysql server and run the following commands:
    
        1. CREATE USER 'movie_data_user'@'localhost' IDENTIFIED BY 'movie_data_pwd';
        2. GRANT ALL PRIVILEGES ON * . * TO 'movie_data_user'@'localhost';
        3. FLUSH PRIVILEGES;
        4. CREATE DATABASE movies_data;

Note: I assumed that MYSQL server is running on the same machine where the application is going to start.

## Installation

Navigate to project root directory 

```sh
$ mvn clean 
$ mvn test
$ mvn install
$ mvn spring-boot:run
```
App will start running at port 8080

## View Test results
```sh
$ mvn clean
$ mvn test
.
.
.
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 16, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  24.018 s
[INFO] Finished at: 2020-07-22T16:54:04+01:00
[INFO] ------------------------------------------------------------------------
```

## Resources Details

#### Import movies data using CSV file:

```sh
$ curl --location --request POST 'http://localhost:8080/v1/movies/parse-csv-file' --form 'moviesDataFile=@/home/shahzad/Desktop/IMDB-Moive-Data.csv'

Response: A list of newly added movies
[
    {
        "id": 1004,
        "rank": 11,
        "title": "Fantastic Beasts and Where to Find Them",
        "description": "The adventures of writer Newt Scamander in New York's secret community of witches and wizards seventy years before Harry Potter reads his book in school.",
        "director": "David Yates",
        "year": 2016,
        "runtimeMinutes": 132,
        "rating": 7.5,
        "votes": 232072,
        "revenueMillions": 234.02,
        "metascore": 66.0,
        "genres": [
            {
                "id": 10,
                "name": "Fantasy"
            },
            {
                "id": 2,
                "name": "Adventure"
            },
            {
                "id": 9,
                "name": "Family"
            }
        ],
        "actors": [
            {
                "id": 42,
                "name": " Katherine Waterston"
            },
            {
                "id": 41,
                "name": "Eddie Redmayne"
            },
            {
                "id": 43,
                "name": " Alison Sudol"
            },
            {
                "id": 44,
                "name": "Dan Fogler"
            }
        ]
    },
    .
    .
    .
]
```

#### Search & filter movies:
For search & filter specifications based REST query language used

#### Search movies by single year

```sh
$ curl --location --request GET 'http://localhost:8080/v1/movies/search?q=year:2012'

Response: A pagable response of max 25 movies with year 2012 will be return
```

#### Search movies by years range

```sh
$ curl --location --request GET 'http://localhost:8080/v1/movies/search?q=year>2010,year<2014'

Response: A pagable response of max 25 movies from year 2010 to 2014 will be return
```
#### Search movies by single genre

```sh
$ curl --location --request GET 'http://localhost:8080/v1/movies/search?q=genre:Comedy'

Response: A pagable response of max 25 movies having genre `Comedy` will be return
```
#### Search movies by multiple genres

```sh
$ curl --location --request GET 'http://localhost:8080/v1/movies/search?q=genre:Comedy,genre:Drama'

Response: A pagable response of max 25 movies having genre `Comedy` and `Drama` will be return
```

#### Search movies by multiple filters

```sh
$ curl --location --request GET 'http://localhost:8080/v1/movies/search?q=genre:Comedy,year>2010,rating>8'

Response: A pagable response of max 25 movies having genre `Comedy` and year 2010 or above and rating 8 or above will be return
```

#### Sample pageable response for all search queries

```json
  {
    "content": [
        {
            "id": 159,
            "rank": 159,
            "title": "Scott Pilgrim vs. the World",
            "description": "Scott Pilgrim must defeat his new girlfriend's seven evil exes in order to win her heart.",
            "director": "Edgar Wright",
            "year": 2010,
            "runtimeMinutes": 112,
            "rating": 8.0,
            "votes": 291457,
            "revenueMillions": 31.0,
            "metascore": 69.0,
            "genres": [
                {
                    "id": 8,
                    "name": "Comedy"
                },
                {
                    "id": 1,
                    "name": "Action"
                },
                {
                    "id": 10,
                    "name": "Fantasy"
                }
            ],
            "actors": [
                {
                    "id": 523,
                    "name": " Alison Pill"
                },
                {
                    "id": 522,
                    "name": " Kieran Culkin"
                },
                {
                    "id": 407,
                    "name": " Mary Elizabeth Winstead"
                },
                {
                    "id": 493,
                    "name": "Michael Cera"
                }
            ]
        }
    ],
    "pageable": {
        "sort": {
            "sorted": true,
            "unsorted": false,
            "empty": false
        },
        "pageSize": 25,
        "pageNumber": 0,
        "offset": 0,
        "unpaged": false,
        "paged": true
    },
    "totalElements": 38,
    "totalPages": 2,
    "last": false,
    "first": true,
    "sort": {
        "sorted": true,
        "unsorted": false,
        "empty": false
    },
    "numberOfElements": 25,
    "size": 25,
    "number": 0,
    "empty": false
}
```
