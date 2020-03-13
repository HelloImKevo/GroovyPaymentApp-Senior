# SkeletonPaymentApp-Senior
This App is intended for Senior candidates. There are Android and iOS variants of this mobile payment App in the respective project directories. This application may include intentional compiler errors out-of-the box, and many of the functions and screens described below are currently not implemented.

| Login Screen | Order Entry |
| :---: | :---: |
![Login Screen](ScreenMocks/Groovy-01-Login.png) | ![Order Entry](ScreenMocks/Groovy-13-OrderEntry.png)

# First Steps: Repo & Project Setup
* Fork the `master` branch of this repo and push the clone to your own repository. Your repository can be public or private, but if it's private you'll need to adjust the settings to invite one or two of us as collaborators so we can review your commits.
* Create a Stripe Developer account over at: https://dashboard.stripe.com/register  
* Grab your **Publishable** and **Secret** API keys from your dashboard: https://dashboard.stripe.com/test/apikeys  
* The Stripe SDK architecture is built around the expectation of using a Client-Server model, where the Client (Mobile App) uses a **Publishable** API key, and the Server uses a **Secret** API key to generate a **Client Secret** string. We don't have a Server for this test application, so we emulate the "Server" within our app. The Android and iOS applications both use asynchronous utilities for generating a **Client Secret** using the Stripe **Secret** API key.  
// TODO: Document Git cloning and forking.  
// TODO: Stripe developer account setup.  
// TODO: Android apikeys.gradle setup.  

## Android Project Setup & Code Style
[Android README](Android/README.md)

## iOS Project Setup & Code Style
[iOS README](iOS/README.md)

# Expectations & Submission Guidelines  

# Business Requirements Document (BRD)  

## Task ID: #001 - Main Dashboard Crash  
#### Estimated LOE (Level of Effort): 2 Hours  

[//]: # (Line breaks must be added to control text wrapping for multi-line rows, otherwise the image will get squashed)

| Requirements | Business Mock |
| :--- | --- |
| The application currently **crashes** when a `Placeholder` button is clicked on the<br>main dashboard.<br><br>Design an alert dialog that is shown with an `"Under Construction"` type of message<br>instead of crashing. | ![Construction Dialog](ScreenMocks/Groovy-04-UnderConstruction.png) |

## Task ID: #002 - Generating Test Data & Populating the Database
#### Estimated LOE (Level of Effort): 2 Hours  

## Task ID: #003 - Product List Facelift  
#### Estimated LOE (Level of Effort): 2 Hours  

| Requirements | Business Mock |
| :--- | --- |
| Users of this application have complained that the Product list looks very bland.<br>(We've gotten lots of negative feedback on the Google Play Store and Apple App Store)<br><br>Update the style of the products to match this new mock from the Design team as closely<br>as possible (or use your creativity to implement a better design). | ![Order Entry](ScreenMocks/Groovy-13-OrderEntry.png) |

## Task ID: #004 - Language Translation of Your Choice
#### Estimated LOE (Level of Effort): 2 Hours  

| Requirements | Business Mock |
| :--- | --- |
| Google Play Store users have left us lots of negative feedback because the application<br>only supports English and Spanish.<br><br>For this task, localize all of the app's string resources to another language<br>of your choice. Go through the app and extract any hard-coded strings as necessary.<br><br>**You do not need to worry about the names and descriptions of database entities.** | ![Spanish](ScreenMocks/Groovy-09-Spanish.png) |
| The system language can be changed at Runtime under System settings. | ![System Language](ScreenMocks/Groovy-07-SystemLanguage.png) |

## Task ID: #005 - Database-Driven Payment Types  
#### Estimated LOE (Level of Effort): 4 Hours  

## Task ID: #006 - Checkout with Cash Payments!  
#### Estimated LOE (Level of Effort): 4 Hours  

## Task ID: #007 - Generate the Client Secret... On the Client!  
#### Estimated LOE (Level of Effort): 4 Hours  

## Task ID: #008 - Pay with a Credit Card Number  
#### Estimated LOE (Level of Effort): 4 Hours  
References:  
https://stripe.com/docs/payments/accept-a-payment#android  
https://stripe.com/docs/testing  

## Git Workflow References

Useful git commands for quickly traversing repos:  
```
# Pushing from a local repository to GitHub hosted remote
git remote add origin git@github.com:USERNAME/REPO-NAME.git

# Clone your fork to your local machine
git clone git@github.com:USERNAME/FORKED-PROJECT.git
```

## GitHub Standard Fork & Pull Request Workflow  
Useful link about project forks:  
https://gist.github.com/Chaser324/ce0505fbed06b947d962  

# Useful References

### Game-Icons.net  
Many thanks to the contributors and maintainers over at game-icons.net - it has been an extremely helpful resource in prototyping mobile applications.  
https://game-icons.net  

### Stripe
https://stripe.com/docs/development  

### Retrofit
https://square.github.io/retrofit  

### Gson
https://github.com/google/gson  
