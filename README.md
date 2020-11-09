# EasterTracker Application

This is our group's project 2 of COMP90018 in 2020 S2.

## Introduction
This is a geographic location-based social application. Friends can bury each other's easter eggs and share fun and interesting things about their geographic location in real time. This app is designed to appeal to users to go out and see more interesting things in the city, while also promoting friendship between friends.

## Project Structure

For the purpose of flexible development, we adopt the mode of webapp + webview to implement our application. 

The frontend handles the display of UI and map and data's rendering. The backend handles data processing and storage. 

Also, the frontend and the backend in our webapp are independent. This seperation makes the frontend and the backend of our application can be developed simultaneously which saves a lot of time.

After importing the webapp into webview, native Android API are uitilised to request permissions in order to make the webapp enable to access multiple sensors of system such as GPS, microphone, camera, etc.

## Technology

### Application Technology

-   WebView: let the app show Html5 pages;
-   Kotlin: Native development language of Android application

### FrontEnd Technology

- Vue: Front-end framework
- Vuex: Global state management
- Vue-Router: Front-end routing management
- Axios: AJAX communication component
- Google Maps JavaScript: API for map rendering and geographic information processing
- BootStrap & AwesomeFont: Basic UI frame and icons

### BackEnd Technology

-   Java: Native Backend Language
-   Lombok: Generate setter and getter methods in model layer
-   RESTful API: provide a standardized api for frontend to call
-   Spring framework: Back-End framework
-   MyBatis: framework for connecting and operating on MySQL database
-   MySQL: database for the system, storing all information used in application
-   Redis: act as L2 cache for the system to speed up overall performances

## Project Compilation and Running

### Project Compilation

Android Studio and Android SDK 21+ is required to compile this app successfully. To compile this application, download the code and open it with Android Studio. After you have opened the code with Android Studio, you need to sync project with Gradle. Once completed, Gradle has helped you to download all the necessary files in this project. Then, click 'build project'. Android Studio will automatically compile and build the project. You have to wait until compilation process is completed.

### Project Running

You can run this application on virtual device or on physical devices. In Android Studio, you need to first confirm that the device has already connected to the computer. Then press 'run' button to run the application on the device. Alternatively, you can also use Android Studio to generate Android apk and then install it on your device. Once installation is completed, you can run the application on your device. 

Before starting the app, you need to grant some permissions to the app so that the sensors can be used in this application, which includes GPS, camera and audio permission. After that, you will be able to see the main page of the app. You need to register and login first. Once successfully logged in, you will be able to enjoy the whole EasterTracker application.

## CopyRight

Copyright © 2020, Team: `Burn My Calories`, release under [Apache-2.0 License](https://github.com/burnMyCalories/EasterTrackerApp/blob/master/LICENSE).

