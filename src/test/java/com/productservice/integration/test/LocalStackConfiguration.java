package com.productservice.integration.test;

import org.springframework.boot.test.context.TestConfiguration;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.DYNAMODB;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.Network;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.dynamodb.model.Product;

@TestConfiguration
@TestPropertySource(properties = { "config.aws.dynamodb.access-key=test1", "config.aws.dynamodb.secret-key=test231" })
public class LocalStackConfiguration {
    @Autowired
    AmazonDynamoDB amazonDynamoDB;

    static LocalStackContainer localStack = new LocalStackContainer(DockerImageName.parse("localstack/localstack"))
	    .withServices(DYNAMODB).withNetworkAliases("localstack")
	    .withNetwork(Network.builder().createNetworkCmdModifier(cmd -> cmd.withName("test-net")).build());

    static {
	localStack.start();
	System.setProperty("amazon.dynamodb.endpoint", localStack.getEndpointOverride(DYNAMODB).toString());
    }

    @PostConstruct
    public void init() {
	DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

	ListTablesResult tables = amazonDynamoDB.listTables();
	if (tables == null || tables.getTableNames().isEmpty()) {
	    CreateTableRequest tableUserRequest = dynamoDBMapper.generateCreateTableRequest(Product.class);
	    tableUserRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
	    amazonDynamoDB.createTable(tableUserRequest);
	}

    }
}
