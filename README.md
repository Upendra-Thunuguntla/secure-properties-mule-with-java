# Secure Properties Mulesoft - JAVA
Welcome to the Encryption & Decryption Web App Github Repo!

This web app is designed to provide a user-friendly interface for encrypting and decrypting properties using Mulesoft provided jar backed by Java Server. The app is built using HTML, CSS, and JS, making it easy to use and accessible to a wide range of users.

Features:
- Easy to use interface for encrypting and decrypting properties
- Secure encryption using Mulesoft provided jar backed by Java Server
- Cross-platform compatibility
- Responsive design for optimal viewing on all devices
- Docker file provided to create Docker Image with ease.

## Getting Started:
To get started with the Encryption & Decryption Web App, simply clone the repo and import into any IDE as JAVA Project.

Go to ```src/server/Main.java``` and Run the Application.

Server will be created and hosted on 9000 port. Open ```localhost:9000``` on any browser and from there, you can begin encrypting and decrypting properties with ease.

## Creating a Docker File
- In Eclipse IDE, Go to the Imported Project and right click on the project directory in Project Explorer. 
- Then Choose Export -> Go to JAVA Section and choose Jar.

![](https://raw.githubusercontent.com/Upendra-Thunuguntla/secure-properties-mule-with-java/main/res/export-options-1.jpg)

- Choose the Configuration exactly as shown as below. Make sure you have target folder cerated and Export Path is directing to that folder.

![](https://raw.githubusercontent.com/Upendra-Thunuguntla/secure-properties-mule-with-java/main/res/export-options-2.jpg)

Open Command Line in the Project path and Run the following Commands
To Build the Docker Image : 

```BASH
docker build -t secure-props .
docker run -p 9000:9000 secure-props
```

Once You see a Message ```Sever Started and running on 9000``` on console. Now, open the browser and use the application.

## Usage of Application

The following page will welcome you ✨
![](https://cdn-images-1.medium.com/v2/resize:fit:800/1*QczXEPXLuDvFGtMJIlJVVA.png)
🎱 Choose either 1️⃣ Encrypt or 2️⃣ Decrypt.

🎱 Choose 3️⃣ Algorithm & 4️⃣ Method as needed. These are Dropdowns and you can choose any combination from available options.

🎱 Enter the 5️⃣ Key to perform Encrypt or Decrypt on input.

🎱  Choose from Three 6️⃣ options 
- String  - Takes a String as input.
- File  - Takes .yaml or .properties file as input and processes all the entries available in the file. You can also just paste the content in the area provided. 

> Once you choose a File, application will read the file and Display the content in the Text Area. So, ultimately it will process the content  that is shown in the Text area.

- Whole File - Takes .yaml or .properties file as input and processes the whole file content. You can also just paste the content in the area provided.

🎱 Based on the above selection relevant input options will be shown here. Enter the input 7️⃣

### For String 
![](https://cdn-images-1.medium.com/max/800/1*_FIrYgCSOyYA5nLluSghxQ.png)
### For File & Whole File
![](https://cdn-images-1.medium.com/max/800/1*bXi_q9AZXf0Ky13IvLILUg.png)

🎱 Once all the entries are made Click on 8️⃣ Start Encryption/Decryption button to start the process.

🎱 The 9️⃣ output will be shown here with appropriate Copy 📎 or Download 📁 buttons.

## Contributing:
I welcome contributions from the community to help improve and enhance the App. If you have any suggestions or would like to contribute to the project, please feel free to submit a pull request.
