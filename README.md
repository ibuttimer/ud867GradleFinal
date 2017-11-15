<!-- vscode-markdown-toc -->
* 1. [Overview](#Overview)
* 2. [Why this Project](#WhythisProject)
* 3. [What Will I Learn?](#WhatWillILearn)
* 4. [How Do I Complete this Project?](#HowDoICompletethisProject)
	* 4.1. [Step 0: Starting Point](#Step0:StartingPoint)
	* 4.2. [Step 1: Create a Java library](#Step1:CreateaJavalibrary)
	* 4.3. [Step 2: Create an Android Library](#Step2:CreateanAndroidLibrary)
	* 4.4. [Step 3: Create GCE Module](#Step3:CreateGCEModule)
	* 4.5. [Step 4: Add Functional Tests](#Step4:AddFunctionalTests)
	* 4.6. [Step 5: Add a Paid Flavor](#Step5:AddaPaidFlavor)
* 5. [Optional Tasks](#OptionalTasks)
	* 5.1. [Add Interstitial Ad](#AddInterstitialAd)
	* 5.2. [Add Loading Indicator](#AddLoadingIndicator)
	* 5.3. [Configure Test Task](#ConfigureTestTask)
* 6. [Rubric](#Rubric)
	* 6.1. [Required Components](#RequiredComponents)
	* 6.2. [Required Behavior](#RequiredBehavior)
	* 6.3. [Optional Components](#OptionalComponents)
* 7. [Implementation](#Implementation)
	* 7.1. [Development Environment](#DevelopmentEnvironment)
	* 7.2. [Development Setup](#DevelopmentSetup)
	* 7.3. [Project Configuration](#ProjectConfiguration)
* 8. [Testing](#Testing)

<!-- vscode-markdown-toc-config
	numbering=true
	autoSave=true
	/vscode-markdown-toc-config -->
<!-- /vscode-markdown-toc -->
# Gradle for Android and Java Final Project

##  1. <a name='Overview'></a>Overview

In this project, you will create an app with multiple flavors that uses
multiple libraries and Google Cloud Endpoints. The finished app will consist
of four modules. A Java library that provides jokes, a Google Cloud Endpoints
(GCE) project that serves those jokes, an Android Library containing an
activity for displaying jokes, and an Android app that fetches jokes from the
GCE module and passes them to the Android Library for display.

##  2. <a name='WhythisProject'></a>Why this Project

As Android projects grow in complexity, it becomes necessary to customize the
behavior of the Gradle build tool, allowing automation of repetitive tasks.
Particularly, factoring functionality into libraries and creating product
flavors allow for much bigger projects with minimal added complexity.

##  3. <a name='WhatWillILearn'></a>What Will I Learn?

You will learn the role of Gradle in building Android Apps and how to use
Gradle to manage apps of increasing complexity. You'll learn to:

* Add free and paid flavors to an app, and set up your build to share code between them
* Factor reusable functionality into a Java library
* Factor reusable Android functionality into an Android library
* Configure a multi project build to compile your libraries and app
* Use the Gradle App Engine plugin to deploy a backend
* Configure an integration test suite that runs against the local App Engine development server

##  4. <a name='HowDoICompletethisProject'></a>How Do I Complete this Project?

###  4.1. <a name='Step0:StartingPoint'></a>Step 0: Starting Point

This is the starting point for the final project, which is provided to you in
the [course repository](https://github.com/udacity/ud867/tree/master/FinalProject). It
contains an activity with a banner ad and a button that purports to tell a
joke, but actually just complains. The banner ad was set up following the
instructions here:

https://developers.google.com/mobile-ads-sdk/docs/admob/android/quick-start

You may need to download the Google Repository from the Extras section of the
Android SDK Manager.

When you can build an deploy this starter code to an emulator, you're ready to
move on.

###  4.2. <a name='Step1:CreateaJavalibrary'></a>Step 1: Create a Java library

Your first task is to create a Java library that provides jokes. Create a new
Gradle Java project either using the Android Studio wizard, or by hand. Then
introduce a project dependency between your app and the new Java Library. If
you need review, check out demo 4.01 from the course code.

Make the button display a toast showing a joke retrieved from your Java joke
telling library.

###  4.3. <a name='Step2:CreateanAndroidLibrary'></a>Step 2: Create an Android Library

Create an Android Library containing an Activity that will display a joke
passed to it as an intent extra. Wire up project dependencies so that the
button can now pass the joke from the Java Library to the Android Library.

For review on how to create an Android library, check out demo 4.03. For a
refresher on intent extras, check out;

http://developer.android.com/guide/components/intents-filters.html

###  4.4. <a name='Step3:CreateGCEModule'></a>Step 3: Create GCE Module

This next task will be pretty tricky. Instead of pulling jokes directly from
our Java library, we'll set up a Google Cloud Endpoints development server,
and pull our jokes from there. Follow the instructions in the following
tutorial to add a Google Could Endpoints module to your project:

https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints

Introduce a project dependency between your Java library and your GCE module,
and modify the GCE starter code to pull jokes from your Java library. Create
an Async task to retrieve jokes. Make the button kick off a task to retrieve a
joke, then launch the activity from your Android Library to display it.

###  4.5. <a name='Step4:AddFunctionalTests'></a>Step 4: Add Functional Tests

Add code to test that your Async task successfully retrieves a non-empty
string. For a refresher on setting up Android tests, check out demo 4.09.

###  4.6. <a name='Step5:AddaPaidFlavor'></a>Step 5: Add a Paid Flavor

Add free and paid product flavors to your app. Remove the ad (and any
dependencies you can) from the paid flavor.

##  5. <a name='OptionalTasks'></a>Optional Tasks

For extra practice to make your project stand out, complete the following tasks.

###  5.1. <a name='AddInterstitialAd'></a>Add Interstitial Ad

Follow these instructions to add an interstitial ad to the free version.
Display the ad after the user hits the button, but before the joke is shown.

https://developers.google.com/mobile-ads-sdk/docs/admob/android/interstitial

###  5.2. <a name='AddLoadingIndicator'></a>Add Loading Indicator

Add a loading indicator that is shown while the joke is being retrieved and
disappears when the joke is ready. The following tutorial is a good place to
start:

http://www.tutorialspoint.com/android/android_loading_spinner.htm

###  5.3. <a name='ConfigureTestTask'></a>Configure Test Task

To tie it all together, create a Gradle task that:

1. Launches the GCE local development server
2. Runs all tests
3. Shuts the server down again

##  6. <a name='Rubric'></a>Rubric

###  6.1. <a name='RequiredComponents'></a>Required Components

* Project contains a Java library for supplying jokes
* Project contains an Android library with an activity that displays jokes passed to it as intent extras.
* Project contains a Google Cloud Endpoints module that supplies jokes from the Java library. Project loads jokes from GCE module via an async task.
* Project contains connected tests to verify that the async task is indeed loading jokes.
* Project contains paid/free flavors. The paid flavor has no ads, and no unnecessary dependencies.

###  6.2. <a name='RequiredBehavior'></a>Required Behavior

* App retrieves jokes from Google Cloud Endpoints module and displays them via an Activity from the Android Library.

###  6.3. <a name='OptionalComponents'></a>Optional Components

Once you have a functioning project, consider adding more features to test your Gradle and Android skills. Here are a few suggestions:

* Make the free app variant display interstitial ads between the main activity and the joke-displaying activity.
* Have the app display a loading indicator while the joke is being fetched from the server.
* Write a Gradle task that starts the GCE dev server, runs all the Android tests, and shuts down the dev server.

##  7. <a name='Implementation'></a>Implementation

###  7.1. <a name='DevelopmentEnvironment'></a>Development Environment
The project was developed using Android Studio 2.3.3.

###  7.2. <a name='DevelopmentSetup'></a>Development Setup
The development environment may be configured as follows:
* Clone the repository (https://github.com/ibuttimer/ud867GradleFinal) from GitHib in Android Studio
* When prompted to create a Studio project, choose yes and Import the project using the default Gradle wrapper.

###  7.3. <a name='ProjectConfiguration'></a>Project Configuration
The project may be configured to communicate with either a local or remote backend. This is done by setting the <code>BACKEND_TYPE</code> meta-data value in <code>app/src/main/AndroidManifest.xml</code>.<br>
The valid settings to communicate with a development GCE running on the local machine are:
* <code>emulator</code> - when running the application on an emulator 
* <code>local</code> - when running the application on a device

To communicate with a GCE deplayed to the Google Cloud Platform the value should be set to:
* <code>deployed</code>

When running the application on a device, the <code>BACKEND_LOCAL_URL</code> meta-data value in <code>app/src/main/AndroidManifest.xml</code> should be updated with the IP address of the local machine.

##  8. <a name='Testing'></a>Testing
In order to execute the test task, run the <code>runTestSuite</code> task from <code>ud867GradleFinal/Tasks/other</code> in the Gradle sidebar. Alternatively, run the <code>gradlew runTestSuite</code> command from a terminal window in the application root folder.

The test reports will be available in the <code>app/build/reports/androidTests/connected/flavors</code> folder.
