#Features:
As part of this project

1. Fetch the web content from url and find the 10th character, every 10th character and display it on the screen
2. Fetch the web content from url and find the every 10th character and Split the text into words using whitespace characters (i.e. space, tab, line break, etc.), count the occurrence of every unique word
       display the count for each word on the screen

Application should run above 3 requests Simultaneously from the given url

# Architecture
MVI design pattern with repository and Use cases
Presentation layer -- Domain layer -- Data layer

#Tech stack Details

Programming Language : Kotlin

Dependency Injection - Hilt-android Hilt provides a standard way to incorporate Dagger dependency injection into an Android application.

Network - Ktor client library is a Kotlin based networking library to fetch the data from remote

Data type - No external data type [like json] used, web content from url is fetched and converted to string and used in the application

Testing - Espresso, Hilt, Compose testing components used

Navigation - raamcosta.compose-destinations compose based navigation library used.

UI - Jetpack compose used for UI design and state management

Note :
- While counting characters from web content, space is not considered. [i.e remove the space from the string before calculating the 10th position and characters]
- Application will support for both raw Html content [with html/JS scripts] and Only Web content [text only].
  Provided settings option [FAB button] in the UI to select between Text only or Html content and click on get data button to display the results.
- Three Text views are displayed in the UI.
- Text views are scrollable while displaying lengthy results.
- Char position to find [i.e 10] is mentioned in Constants file [CHAR_POSITION_TO_FIND], so that it can be changed to any other value and verify this solution.

Parallel Request Handling:
- Parallel api requests are handled with kotlin Coroutine scope and Async/Await mechanism.
- For Each api request, loading progress bar is displayed until response received.

Error handling:
- Network error handling is taken care for each api request and error messages are displayed.
- In case of api error, error message [unable to load the data] displayed in the UI
- No custom exception classed are created

Testing:
- Instrumentation test cases are covered UI actions and results using espresso library
- Unit testing for Usecases and Repository classed added with Junit and Compose testing libraries.

Local Storage:
- Data base or any other local storage/cache libraries are not used to persist the data.

UI
- Single activity with Single screen written in Jetpack compose library
- Material design and compose UI components are used
- Compose mutable state is used for state management

- UI navigation - raamcosta.compose-destinations library is used for basic compose navigation
[Refer : https://github.com/raamcosta/compose-destinations]


