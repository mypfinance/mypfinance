# mypfinance
MyPFinance is a budget tracking application, used by users to track their income and expenses

HOW TO SETUP BACKEND / DB /FRONTEND of the APP:

1. Clone the project from GH to your local machine
2. Setup Java SDK. (The app is written in Java 17)
3. Make sure the code compiles and that your IDE sets it up as maven project!
4. Go to application.properties and check the DB configs.
5. Configure a PostgreSQL DB on your local machine (Using pgadmin or another db creation provder)
6. After all of this is setup, you can start the application in two ways:

- First way is to separetly start the backend and then the frontend. You can do this by first runnning the SpringApplication.main method. After that navigate to the frontend folder that's in this directory "/src/mypfinance-frontend". There you can run the following command (keeping in mind that we use node v16.* so check your versioning before running the commands)s: **npm install** , **npm run serve**. This should start the frontend application too, which will be configured in localhost:8081, so you can click on the link provided by the npm run serve or just go to **localhost:8081**

- Second option is to use maven for running the app. Since we preconfigured our app in the pom.xml as a single deployable unit, you can just run **mvn clean install -DskipTests**, that way you willl build a jar in your target folder. After which, you can run this command to fire up the jar:   **java -jar target/mypfinance-0.0.1-SNAPSHOT.jar**. Then navigate to the mypfinance-frontend folder and run **npm install**, **npm run serve**. After that you can go to localhost:8081 and start playing with the app :)
