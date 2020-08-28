# product-manager

1. To create a microservice without Spring-boot
	I have checked some microservice framework which can be used. I have came across Jersey, SparkJava and Dropwizard framework.
	I have started analyzing Dropwizard framework and there are some ready configurations available. And it's helpful while developing the microservices.
	It provides various features. Spring-Boot is also being developed on same principles. 
	
	In case of SparkJava there is not information available so I didn't opt this option.
	
	Jersey framework is also a good option for this service. As dropwizard internally used jersey to call rest-endpoints.
	I have already started working on Dropwizard Framework due to time constrain I was not able to explore much into this.
	
	Dropwizard is having so many functionality support like - checkLiveliness api, create docker images etc. over other framework
	IF we are not looking light weight framework we can use jersy to develop this service

2. The microservice that I have shared will use 2 database One to store product details and other to store orders. This was stated in the problem statement.
	For this I have create 2 in-memory databases. Postgres and H2. When we start utility dummy data will be added in the corresponding tables.
	You can able to see the same from Swagger. The database connections are configurable and can be changes from yml file configuration. Refer configuration.yml file
	for more details. To change the database we need to add driver in pom file and change the configuration in yml file. We also have to remove/comment the code to addd dummy data in com.lti.rest.App class. 
	Comment below 2 methods cals from run method. <line number 53 and 54>
		setupProductDb(productDatasource); 
		setupOrderDb();

4. Before running the application please kill the job running on 5433, 9090 and 9091. Otherwise application will not start. The embedded postgress server is running 
	on 5433. To change this you have make changes com.lti.rest.App (line number 72 method - setupOrderDb) and  configuration
	You can change the port 9090 and 9091 in configuration.yml and servie will start on those ports	
		
	You need to build and run the service with Java-8 otherwise the NoClassDefinition found exception will be thrown for higher java version. To resolve this 
	we may need to add depenedency.
		
5. How to start the service - There are 2 ways you can start the service. Checkout the code build the code. It will take some time to download the dependencies.
	eventually it will create a jar file with name product-manager-0.0.1-SNAPSHOT.jar. 
	To start the service use below command java -jar product-manager-0.0.1-SNAPSHOT.jar server <path to configuration.yml file>
	The configuration.yml is present in the project-manager folder. If these 2 paths are not provided the service will not start.
	
	
	If you are in target directory run -
	java -jar product-manager-0.0.1-SNAPSHOT.jar server ../configuration.yml
	
	or run below command from same dir once the project is build f
	
	java -jar target\product-manager-0.0.1-SNAPSHOT.jar server configuration.yml 
	
	The another way is to import class in IDE and run the com.lti.rest.App as a main class. 
	
	This is will start the running on jetty server by default. We can not run this on any other server. This is the limitation. I have completed so much 
	developement on this framework and due to time constraints I was not able to start implementing the same in jersey server.
	
6. Once the application started run http://localhost:9090/product-manager/swagger to see the detailed endpoints.
		If you modified the port in change the ports in above url
	
7. I have created below apis for the specified requirements
	
	i. I want to get the list of all the products across all the categories - 
		Refer api http://localhost:9090/product-manager/productDetails/v1 -
			It will fetch the list of products of all the catogories
		Class - ProductDetailResource Method - getProductsAcrossCatogory
		
	ii. I want to get the list of all the products by category or of a price less than or greater than a specified price for that product -
				Refer api - http://localhost:9090/product-manager/productDetails/v1/query/product
				Class - ProductDetailResource Method - getProductsByCategory
				
	iii. I want to get the list of all the products by company or of a price less than or greater than a specified price for that company
				Refer api - Refer api - http://localhost:9090/product-manager/productDetails/v1/query/product
				Class - ProductDetailResource Method - getProductsByCategory
				
	iv. I want to get the discounted price of all the products by category - \
				Refer api - http://localhost:9090/product-manager/productDetails/v1/{category}
				While providing the list of products category-wise it has been handled. If we want we can create a different api. The code is ready need to creat a api. This can also be added in every api, but it will be additional processing and may take time and impact performance. So added only in one api
	
	v. I should be able to place a dummy order in a separate (database) instance. Order placement should fail if the stock = number of dummy orders  -
			As mentioned the products and orders are stored in 2 different database. For now I have used in-memory databases as suggested but these can be changed.
			As to perform database operations jdbi3 is used we need to create tables manually. You can get the sample queries from the code.
			If the order quantity enter is greater than or equals to the products in stock the operder will not be placed -
			post Api - http://localhost:9090/product-manager/order/v1 
			For this to process you need to enter product id from product table.
			
			
	vi. I should be able to get the available stock details across products. This should consider the dummy orders placed and return stocks accordingly. -
				This is been handled in all the apis to generate the available stock 
				refer api - http://localhost:9090/product-manager/productDetails/v1
	
	vii. I should be able to delete the dummy order(s)
			The duplicate orders will be categorized basis productID, quantity and userId and the status of order.
			If any records having duplicate values for the same will be deleted and only one copy will be maintained
			
			
Table details
	Product, Company and category table in one database. 
	Product table will have foreign key reference to company and category.
	
	Order table is stored in differen database so can not have any relation to product. Can not put foreing key relation and can not perform join on the same.
	

	Nomal constain to follow - The category and company name are care sensitive. 

Apart from that api to perform crud operations on database tables are also provided.


			
			
If we want to change the database, we may also need to change the sql query syntax. Didn't get time to check if jdbi supports univesal query syntax across all dbs
Couldn't write the test cases due to time constain. As a bit of RnD was needed to write the test cases for Dropwizard Framweork
Also couldn't explore if we can store this in no-sql (Mongodb) and it's queries, performance comapare to RDBMS due to time constrains 
