# Infobip 

### Java url shortner service
Url shortner service shortens the long url into 5 digit unique  alphanumeric string, it uses 62 alphanumeric literals to generate unique code, which gives 62 raise to 5 number of unique values. This shortened url can be used to redirect back to original long url. For using this service, user will have to register itself with account id. He will get generated password after successful registration.
This project uses basic authentication of spring, so user will have to pass his account id and password to use the rest calls. After registration user can register any number of long urls to generate shortened url's.User can also get his usage statistics by passing account id.
Url shortener service project is running on http port 8080. The base url is 
http://localhost:8080

##### Building project
- Go to the project directory
- Run the command
```sh
mvn clean install
```

##### Run project

```sh
java -jar target/infobip-0.0.1-SNAPSHOT.jar
```

### Rest calls
 Creating account:
```sh
method type : post
http://localhost:8080/account

Demo type:
curl -X POST \
  http://localhost:8080/account \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 5a49da0a-8fe1-bd52-a1d7-625574f993bd' \
  -d '{
  "accountId":"username"
}'
```

Register url:
After the account is created you can register the url to be shortened.

```sh
Method type : post
http://localhost:8080/register

Demo type:
curl -X POST \
  http://localhost:8080/register \
  -H 'authorization: Basic cGF2YW46aDNyWGxNVGw=' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: c2201ac6-dd18-0988-d293-e2816e7ff320' \
  -d ' {
 "url": "http://www.facebook.com",   
 "redirectType" : 308
 }'
 
 Result : 
 Shortened url : localhost:8080/vxteOI
```

You can use this shortened url to hit the original website with authorization credenetials. This url will redirect you to original long url.

Redirect url :
```sh
localhost:8080/vxteOI

Demo: 
curl -X GET \
  http://localhost:8080/vxteOI \
  -H 'authorization: Basic cGF2YW46aDNyWGxNVGw=' \
  -H 'cache-control: no-cache' \
  -H 'postman-token: 9f74373c-cced-7aea-4365-9a989add8662'
```


You can get statistics of the user too, how many time he has used shortened url.

User statistics:

```sh
http://localhost:8080/statistic/username

Demo:
curl -X GET \
  http://localhost:8080/statistic/username \
  -H 'authorization: Basic cGF2YW46akZKTGQwTXo=' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 357b5645-7aa0-c4c4-5847-f31f5d2cd581'
  
  Result:
  {
  "http://www.google.com": 1,
  "http://www.facebook.com": 1
  }
```



