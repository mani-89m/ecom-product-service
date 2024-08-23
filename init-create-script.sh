#!/bin/bash

# -- > Create DynamoDb Tables
echo Creating  DynamoDb \'ProductInfo\' table ...
echo $(awslocal dynamodb create-table --cli-input-json '{"TableName":"ProductInfo", "KeySchema":[{"AttributeName":"productId","KeyType":"HASH"}], "AttributeDefinitions":[{"AttributeName":"productId","AttributeType":"S"}],"BillingMode":"PAY_PER_REQUEST"}')

echo Creating  DynamoDb \'OrderInfo\' table ...
echo $(awslocal dynamodb create-table --cli-input-json '{"TableName":"OrderInfo", "KeySchema":[{"AttributeName":"orderId","KeyType":"HASH"},{"AttributeName":"productId","KeyType":"RANGE"}], "AttributeDefinitions":[{"AttributeName":"orderId","AttributeType":"S"},{"AttributeName":"productId","AttributeType":"S"}],"BillingMode":"PAY_PER_REQUEST"}')

# --> List DynamoDb Tables
echo Listing tables ...
echo $(awslocal dynamodb list-tables)

# -- > Create S3 Bucket
echo Creating  S3 Bucket \'order-receipts\'...
echo $(awslocal s3 mb s3://order-receipts)
# --> List S3 Buckets
echo $(awslocal s3 ls)

# -- > Create SQS queue
echo Creating SQS queue  \'order-info\'...
echo $(awslocal sqs create-queue --queue-name order-info --region=us-east-1)
echo $(awslocal sqs list-queues)
