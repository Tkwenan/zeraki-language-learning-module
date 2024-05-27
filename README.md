# zeraki-language-learning-module
Project Setup: 
Download the project files to your local machine by cloning the project. 
To clone the repository, follow the instructions on this link: https://docs.github.com/en/repositories/creating-and-managing-repositories/cloning-a-repository
Open the project using an IDE e.g. IntelliJ
On the IDE, run the command ‘mvn clean install’ to install the project dependencies 
Then, run the main project file, LanguageLearningModuleApplication.java to start the project on port 8080.

Accessing the API endpoints: 
A Postman collection was sent to careers@zeraki.com to enable access to the API endpoints.
To start sending requests, go to the Auth folder in the collection. 
Go to the ‘Login’ request where you will find the following login credentials: "userEmail": "john.doe@user.com", "userPassword": "qwerty"
Send the POST request to receive an authentication token. 
To be able to use the other endpoints (except the ‘Register New User’ endpoint), you will need to paste this token into the Authorization header of each request, as shown in the screenshot below.
<img width="1440" alt="Screenshot 2024-05-27 at 8 42 13 AM" src="https://github.com/Tkwenan/zeraki-language-learning-module/assets/54999162/22978d28-e59a-4272-82ca-7ac18043c67a">




