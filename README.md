# C196 - Mobile Application

## Overview

This is an Android application developed as a part of C196 and added to/refined for C868. This application allows users to add to and manage their school schedule.

## Features

- **User Registration and Login**: Users can register and log in to access the app's features.
- **Term Management**: Add, view, edit, and delete academic terms.
- **Course Management**: Add, view, edit, and delete courses associated with terms.
- **Assessment Management**: Add, view, edit, and delete assessments associated with courses.
- **Notes Management**: Add, view, edit, and delete notes associated with courses.

## Installation

### Prerequisites

- Android Studio
- Android SDK
- Java Development Kit (JDK)
- (Optional) Repository located at [https://gitlab.com/wgu7415723/c196.git](https://github.com/neelofar1334/Class-Scheduler-Android-App.git)

### Steps

1. **Open in Android Studio**:
 - Download the provided zip file containing the project source code.
 - Extract the zip file to a desired location on your machine.
 - Open Android Studio.
 - Select File -> Open from the top menu.
 - Navigate to the extracted project directory and select it.

3. **Build the Project**:
   - Let Android Studio download the necessary dependencies.
   - Build the project using `Build -> Make Project`.

4. **Run the Application**:
   - Connect an Android device or start an emulator.
   - Run the application using `Run -> Run 'app'`.

## Usage

1. **User Registration**:
   - Open the app.
   - Register a new account by providing a username and password.

2. **Login**:
   - Log in using your registered credentials.

3. **Managing Terms**:
   - Add a new term by clicking on the "Add Term" button.
   - View term details by selecting a term from the list.
   - Edit or delete terms as needed.

4. **Managing Courses**:
   - Add a new course by navigating to a term and clicking on the "Add Course" button.
   - View course details by selecting a course from the list.
   - Edit or delete courses as needed.

5. **Managing Assessments**:
   - Add a new assessment by navigating to a course and clicking on the "Add Assessment" button.
   - View assessment details by selecting an assessment from the list.
   - Edit or delete assessments as needed.

6. **Managing Notes**:
   - Add a new note by navigating to a course and clicking on the "Add Note" button.
   - View note details by selecting a note from the list.
   - Edit or delete notes as needed.

## Screenshots

 - Provided in separate file

## Architecture

- **MVVM (Model-View-ViewModel)**: The app uses the MVVM architecture to separate concerns and improve testability.
- **Room Database**: Used for local data storage and management.
- **LiveData**: Used for observing data changes.
- **ViewModel**: Manages UI-related data in a lifecycle-conscious way.

## Testing

The application includes unit tests for ViewModels using stub classes. To run the tests:

1. Open the project in Android Studio.
2. Navigate to the `test` and `androidTest` directories.
3. Right-click on the test class and select `Run`.

## License

Personal project.

## Contact

For any inquiries or issues, please contact me at [nkarim1@wgu.edu].

---
