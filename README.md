# mypfinance
MyPFinance is a budget tracking application, used by users to track their income and expenses

🙋‍♂️ I will be posting some screenshots from the current state of the app, because I couldn't upload heavy GIFs and show the frontend in action. Bare in mind that frontend isn't my favorite so UI probably won't look as good as it could've ;)

**Here is the HomePage of the app, where you would find dashboards with statistics on your spending and a form to quickly add a new transaction:**

![1](https://user-images.githubusercontent.com/76811860/219962536-6ce8f797-24fc-4763-b499-64d473cd7aee.png)

**Moving on, on the next page, you can see the Expenses you've been recording. They can be sorted by date/amount/category, by simply typing on the column's name. You also have paginated result, so you can choose how many transactions you want to see in a single screen:**

![2](https://user-images.githubusercontent.com/76811860/219962613-2cfa2224-f085-47c8-9052-7f6c12f9d65d.png)

**Next up is the 'Settings' page of our app, where you can add new expense/income categories, set general settings like in which currency you would like your account to be in and the system name that shows as a logo at the top of the navigation bar:**

![3](https://user-images.githubusercontent.com/76811860/219962618-eb1d9dcb-100e-4a36-ac7b-03fed72c6008.png)

**By clicking on the 'Add category' button, you will be prompted with a form, in which you would speficy the name of the new category, as well as the color you would like it to be displayed in, in the app:**

![4](https://user-images.githubusercontent.com/76811860/219962622-92ffbcf2-3c92-477f-be3a-7164b9c78c87.png)

**You will be notified by a pop-up like this, each time you add a new transaction/category:**

![5](https://user-images.githubusercontent.com/76811860/219962630-69dea9c7-7ef8-4a98-ba68-968a371399b8.png)

**And finally, here is a simple view of your profile apge, where you can modify your account details, except the email and username ;)**

![6](https://user-images.githubusercontent.com/76811860/219962633-b08f2214-d452-4507-b732-1d35e1d83459.png)

**I will be adding a portoflio tracking section to the app as well as optimizing the general UI/UX! Would love me some help with the frontend, pleaseee ;((**

HOW TO SETUP BACKEND / DB /FRONTEND of the APP:

1. Clone the project from GH to your local machine
2. Setup Java SDK. (The app is written in Java 17)
3. Make sure the code compiles and that your IDE sets it up as maven project!
4. Go to application.properties and check the DB configs.
5. Configure a PostgreSQL DB on your local machine (Using pgadmin or another db creation provder)
6. After all of this is setup, you can start the application in two ways:

- First way is to separetly start the backend and then the frontend. You can do this by first runnning the SpringApplication.main method. After that navigate to the frontend folder that's in this directory "/src/mypfinance-frontend". There you can run the following command (keeping in mind that we use node v16.* so check your versioning before running the commands)s: **npm install** , **npm run serve**. This should start the frontend application too, which will be configured in localhost:8081, so you can click on the link provided by the npm run serve or just go to **localhost:8081**

- Second option is to use maven for running the app. Since we preconfigured our app in the pom.xml as a single deployable unit, you can just run **mvn clean install -DskipTests**, that way you willl build a jar in your target folder. After which, you can run this command to fire up the jar:   **java -jar target/mypfinance-0.0.1-SNAPSHOT.jar**. Then navigate to the mypfinance-frontend folder and run **npm install**, **npm run serve**. After that you can go to localhost:8081 and start playing with the app :)

