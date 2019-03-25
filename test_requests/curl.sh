#!/usr/bin/env bash

echo "Uploading file..."
curl -X POST \
  http://127.0.0.1:8080/data/snapshot/upload \
  -H 'content-type: multipart/form-data' \
  -F snapshot=@src/integration-test/resources/MOCK_DATA.csv
echo "Done"

echo "Getting record with primary key 'Tresom'"
curl -X GET http://127.0.0.1:8080/data/snapshot/records/Tresom
echo ""

echo ""
echo "Deleting record with primary key 'Tresom'"
curl -X DELETE http://127.0.0.1:8080/data/snapshot/records/Tresom
echo ""

echo "Again getting record with primary key 'Tresom'"
curl -X GET http://127.0.0.1:8080/data/snapshot/records/Tresom
echo ""
