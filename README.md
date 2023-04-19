# upday-backend

[![CircleCI](https://circleci.com/gh/bkhablenko/upday-backend.svg?style=shield)](https://circleci.com/gh/bkhablenko/upday-backend)

TODO: Describe implemented use cases.

## How to Run

```bash
./gradlew clean build -x test && docker compose up --build
```

API documentation will be available at http://localhost:8090.

### Sample API Requests

#### List all authors

```bash
curl -i http://localhost:8080/api/v1/authors
```

#### Get author by ID

```bash
curl -i http://localhost:8080/api/v1/authors/espFx31ogpynhXHNJ2TW72
```

#### List all articles

```bash
curl -i http://localhost:8080/api/v1/articles
```

#### Get article by ID

```bash
curl -i http://localhost:8080/api/v1/articles/5hAthjCg7vobqEgG6WDruY
```

#### Search articles

By author ID:

```bash
curl -i 'http://localhost:8080/api/v1/articles?authorId=espFx31ogpynhXHNJ2TW72'
```

By topics:

```bash
curl -i 'http://localhost:8080/api/v1/articles?tags=motorcycling'
```

By date range:

```bash
curl -i "http://localhost:8080/api/v1/articles?publicationDateStart=$(date +%Y-%m-%d)"
```

#### Publish a new article

```bash
curl -i http://localhost:8080/api/v1/articles \
  -H 'Authorization: Basic YWRtaW46cEA1NXcwcmQ=' \
  -H 'Content-Type: application/json' \
  --data '{
      "title": "The Motorcycle Gangs: Losers and Outsiders",
      "description": "Reflections and insights into the Hell'\''s Angels motorcycle club and their engagement in criminal activities.",
      "body": "Some fancy text goes here.",
      "tags": ["california", "criminal"],
      "authors": ["espFx31ogpynhXHNJ2TW72"]
  }'
```

#### Edit an article

```bash
curl -i http://localhost:8080/api/v1/articles/5hAthjCg7vobqEgG6WDruY \
  -X PUT \
  -H 'Authorization: Basic YWRtaW46cEA1NXcwcmQ=' \
  -H 'Content-Type: application/json' \
  --data '{
      "description": "Here goes a better description."
  }'
```

#### Remove an article

```bash
curl -i http://localhost:8080/api/v1/articles/5hAthjCg7vobqEgG6WDruY \
  -H 'Authorization: Basic YWRtaW46cEA1NXcwcmQ=' \
  -X DELETE
```

## Notes

This project is not perfect and could be improved in certain areas.

### CI

- [ ] Build the app and run tests with Docker Compose
- [ ] Split the pipeline into multiple jobs
- [ ] Configure [parallelism](https://circleci.com/docs/parallelism-faster-jobs/)
- [ ] Scan project dependencies for vulnerabilities

### CD

- [ ] Create a Helm chart to deploy the app in K8s

### API Security

Ideally, we'd use OAuth 2 with [Keycloak](https://www.keycloak.org/). Given the time constraint, however, some endpoints require `Basic` authentication instead.

(In-memory user credentials: `admin:p@55sw0rd`.)

### Rate Limiting

Rate limiting is out of the scope of this project, but it's something that we'd definitely need in a production environment.

### Error Handling

Currently, the error response structure is inconsistent.

To fix that, consider extending [`ResponseEntityExceptionHandler`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/method/annotation/ResponseEntityExceptionHandler.html). 

### Caching

Once published, news articles rarely change. It makes sense to cache them for improved performance.

### Pagination

There's none :shrug: Consider extending [`PagingAndSortingRepository`](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/PagingAndSortingRepository.html).

### Observability

- [x] ~~Write app logs as JSON~~
- [ ] Configure log collection (e.g., [Fluentd](https://www.fluentd.org/))
- [x] ~~Expose Prometheus metrics at http://localhost:8080/metrics~~
- [ ] Set up a [RED dashboard](https://grafana.com/blog/2018/08/02/the-red-method-how-to-instrument-your-services/) with Grafana
- [x] ~~Configure tracing and trace collection~~

### Other

- [ ] Configure DB connection pool
- [ ] Validate `@RequestBody` payloads

## License

This project is licensed under the terms of the MIT license. See the [LICENSE](LICENSE) file for details.
