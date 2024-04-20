# Game On Application
Welcome to Game On, an Android application designed to help users find and order sports fields in Israel for their practice sessions. Below is an overview of the application along with important details for developers and users.

This is my final project for Android Application Development course, in Afeka - the academic college of Engineering in Tel Aviv.

# Overview
Game On is a mobile application built for Android devices that facilitates the process of finding, viewing, and ordering sports fields in Israel. Users can register, view available fields on a map, and place orders for specific dates and times.

<img src="https://github.com/Lioravraham5/GameOn/assets/159531151/75f86bb4-792d-47e5-9079-624f64f9fc2d" alt="WelcomePage" width="25%" height="25%">

# Features:
1. **User Registration**: Users can register by entering a username, full name, and password. Registered users gain access to the ordering functionality.
2. **Map Integration**: Utilizes Google Maps to display all available fields and their locations.
3. **Real-Time Database**: Utilizes Firebase's real-time database to store:
   - Registered user details including associated orders
   - Field details, including addresses and associated orders.
4. **Order History**: Users can view all the orders they have created.

## Application Flow
1) **Login process**
   - Unsigned user: must create an account in the application by providing a unique username, full name, and password.
   - Signed user: can log in to the application using their username and password to access its features and functionalities.
     
<div style="display: flex;">
  <img src="https://github.com/Lioravraham5/GameOn/assets/159531151/c1ea7d87-9ee1-4c8a-b1fc-cec16ca05d52" alt="Image 1" style="width: 25%; height: 25%;">
  <img src="https://github.com/Lioravraham5/GameOn/assets/159531151/2492efb8-74d8-4403-bef6-0b3ac0a328e2" alt="Image 2" style="width: 25%; height: 25%;">
</div>


2) **Homepage**
   
   serves as the main screen for users to navigate through different functionalities of the Game On application. Here's a summary of what this screen offers:
   - My Orders: Users can view their all orders by clicking on the "My Orders" button.
   - Create Field Order: Users can proceed to create a new field order by clicking on the "Create Field Order" button.

<img src="https://github.com/Lioravraham5/GameOn/assets/159531151/3fa66714-4c09-4b5a-bcd9-009fe02d5cbd" alt="WelcomePage" width="25%" height="25%">


3) **Creating field order process**
   - The app displays a map with all available fields in Israel, allowing users to visually identify fields in different regions. Users can search for fields in specific cities by entering the city name in a search bar.
   - Upon selecting a field of interest, users are directed to the order creation screen, where they specify the date and start time for their practice session. The end time is automatically calculated as one hour after       the start time. Additionally, the user can view future orders made by other users for the same field.
   - After finalizing the booking details, users confirm the order.

<div style="display: flex;">
  <img src="https://github.com/Lioravraham5/GameOn/assets/159531151/cdf6bdd1-51dd-4786-967f-13ec6dd3d860" alt="Image 1" style="width: 25%; height: 25%;">
  <img src="https://github.com/Lioravraham5/GameOn/assets/159531151/4352092f-b342-472d-b53c-6d0f52479633" alt="Image 2" style="width: 25%; height: 25%;">
</div>

<div style="display: flex;">
  <img src="https://github.com/Lioravraham5/GameOn/assets/159531151/e81f085a-55e0-41a4-9eed-b90a02b24863" alt="Image 1" style="width: 25%; height: 25%;">
  <img src="https://github.com/Lioravraham5/GameOn/assets/159531151/ef5847c6-4e3f-41fb-972b-8df40429cc09" alt="Image 2" style="width: 25%; height: 25%;">
</div>

<img src="https://github.com/Lioravraham5/GameOn/assets/159531151/c6724757-582c-4db6-aeaf-e7923dad35e7" alt="WelcomePage" width="25%" height="25%">


4) **All orders page**
   
   Users can access this page to review information about their previous fields orders, including dates, times, and details about the fields. 

<img src="https://github.com/Lioravraham5/GameOn/assets/159531151/846d3784-0791-46b2-8598-ed9042d3b0e3" alt="WelcomePage" width="25%" height="25%">

