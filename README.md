# Starlify connector for AWS API Gateway
Exports the API details to Starlify as systems and services.

## Dependencies
1. Java-8 +
2. Maven

### spring-boot-starter-web
For exposure of connector etc. on http.

## Start
Start by cloning the project by using the link below:  
https://github.com/entiros/starlify-aws-connector.git

## Configuration
Put the text below in your property file to configure your URL for AWS API Gateway and Starlify:  
```
aws:
	server:
		url: https://apigateway.us-east-1.amazonaws.com
starlify:
  	url: https://api.starlify.com

```

Go to cloned location and run the command below to start the process:  
```
mvn clean spring-boot:run
```

## Import AWS API Gateway details to Starlify
Use the endpoint below to start importing API details to Starlify as systems and services:

```
Method : POST
URL : http://localhost:8080/submitRequest
Body : 
			{
				"starlifyKey":"starlify-api-key",
				"apiKey":"aws-api-key",
				"networkId":"starlify-network-id-to-create-services-systems-and-flows",
				"apiSecret":"aws-api-secret",
                		"region":"aws-region"
			}
```

## Output
After successful request submission, you should be able to see all the systems and services from AWS in your Starlify network.
