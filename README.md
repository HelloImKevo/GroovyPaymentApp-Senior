# SkeletonPaymentApp-Senior
Just a skeleton shell app with lots of method stubs and possibly even intentional compiler errors.

## This App is intended for Senior candidates.
There are Android and iOS variants of this mobile payment app in the respective directories.

Login Screen                                             | Main Dashboard
:-------------------------------------------------------:|:---------------------------------------------------------:
![Login Screen](ScreenMocks/GroovyMobilePayments-01-Login.png) | ![Main Dashboard](ScreenMocks/GroovyMobilePayments-05-MainDashboard.png)

Under Construction                                       | Under Construction
:-------------------------------------------------------:|:---------------------------------------------------------:
![Construction Dialog](ScreenMocks/GroovyMobilePayments-04-UnderConstruction.png) | ![Construction Dialog](ScreenMocks/GroovyMobilePayments-04-UnderConstruction.png)

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

## Core Project Dependencies

```
// Google Gson
implementation 'com.google.code.gson:gson:2.8.5'

// Square Retrofit HTTP Library - https://square.github.io/retrofit/
implementation 'com.squareup.retrofit2:retrofit:2.5.0'
implementation 'com.squareup.retrofit2:converter-gson:2.5.0'
implementation 'com.squareup.okhttp3:okhttp:3.12.0'
implementation 'com.squareup.okhttp3:logging-interceptor:3.11.0'
```

## AOSP Java Code Style Guidelines
* Consistency  
* Readability  
* Well-Organized  
* Self-Documenting Code  
* Appropriate Use of Annotations  
* Use / Address Analyzer Warnings  
https://source.android.com/setup/contribute/code-style  

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

# Business Requirements Document (BRD)  

## Task ID: #001  
#### Estimated LOE (Level of Effort): 2 Hours  

[//]: # (Line breaks must be added to control text wrapping for multi-line rows, otherwise the image will get squashed)

| Requirements | Business Mocks |
| :--- | --- |
| The application currently **crashes** when a `Placeholder` button is clicked on the main dashboard.<br>Design an alert dialog that is shown with an `"Under Construction"` type of message instead of crashing. | ![Construction Dialog](ScreenMocks/GroovyMobilePayments-04-UnderConstruction.png) |

```
// TODO: Add requirements / screen mocks
```

<html>
    <table>

        <!-- Table Headers -->
        <tr>
            <td><strong>Requirements</strong></td>
            <td><strong>Screen Mocks</strong></td>
        </tr>

        <!-- Table Cell Contents -->
        <tr>
            <td><p>The application currently crashes when a `Placeholder` button is clicked on the main dashboard. Design an alert dialog that is shown with an `"Under Construction"` type of message.</p></td>
            <td><img src="ScreenMocks/GroovyMobilePayments-04-UnderConstruction.png" width=300></td>
        </tr>
    </table>
</html>
