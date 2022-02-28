# Starlify connector for aws gateway
Exports the aws api details to starlify as Service, System and Flow.

## Dependencies
1. Java-8 +

### spring-boot-starter-web
For exposure of connector etc. on http.



## Start
First clone the project using below link
https://github.com/entiros/starlify-aws-connector.git

## Configuration
Make sure proper aws api gateway and starlify url's configured properly in properties file like this

```
aws:
  server:
    url: https://apigateway.us-east-1.amazonaws.com
starlify:
  url: https://api.starlify.com

```

Go to cloned location and run below command to start the process
mvn clean spring-boot:run

## import aws api details to Starlify
Use below endpoint to start importing api details to starlify as services, systems and flows

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
After successful request submission, you should be able to see all the systems and services from aws in give starlify network.