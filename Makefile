run-postgres:
	-docker rm -f postgres-test
	docker run -d --name postgres-test -e POSTGRES_PASSWORD=1234 -e POSTGRES_DB=test -p 5432:5432 postgres:13.3
