#Steps to run the application:
1. Install Docker
2. Run docker compose file(attached in ecom-product-service repo) to install localstack container along with aws services using command: docker-compose up
3. Once localstack container is up, run this command to enter into the container: docker exec -it cont-id bash
4. Run init-create-script.sh(attached in ecom-product-service repo) - This is to create necessary tables/sqs/s3-
 buckets.
5. Start all the applications/modules
   ecom-product-service
   ecom-order-service
   ecom-notification-service
7. Use the attached(in ecom-product-service repo) postman-collections to test/verify. 
