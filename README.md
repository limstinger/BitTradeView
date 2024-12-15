# BitTradeView App

 ◭ 2024.12  **Mobile Programming Term Project**  ◭

 <br>

 ## **Introduction**
In this project, I aim to develop an app that integrates with the database of an automated Bitcoin trading program currently running on an EC2 cloud server.<br>
The app will visualize the trading data in various formats, providing insightful and interactive representations.

| Android App View 1     | Android App View 2     |
|--------------------|--------------------|
| ![Android App in java 01](https://github.com/user-attachments/assets/692aa0c7-9d1d-45d6-83f2-f2a433f44f3f) | ![Android App in java 02](https://github.com/user-attachments/assets/c5666035-53b7-45e1-9aea-3a05504ba3f6) |

 ## **Developer**
 임민규(Lim Mingyu)

 ## **Set Up & Prerequisites**
 ***Build environment:***
  - [x] The `app.py` script enables external access to the Bitcoin trading database stored on the EC2 server, allowing the app to retrieve and utilize the data.
  - [X] Therefore, the `app.py` script must be running on the EC2 server before launching the app.
  - [X] ![app](https://github.com/user-attachments/assets/8b12430f-bf6b-4f1a-80f0-213319d514d0) <br>
  - [X] Requires Android Studio installation
<br>

***Prerequisites:***

<br>

****Gradle Dependencies****

The following libraries are required for the app to function properly, with their roles described below:
1. Retrofit
   - [x] ```implementation("com.squareup.retrofit2:retrofit:2.9.0")```<br>
         Retrofit is used for handling REST API communication.
   - [X] ```implementation("com.squareup.retrofit2:converter-gson:2.9.0")```<br>
         Converts JSON responses from the server into Java objects.
2. OkHttp
   - [x] ```implementation("com.squareup.okhttp3:okhttp:4.9.0")```<br>
         OkHttp is an HTTP client for making network requests.
   - [X] ```implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")```<br>
         Logs network requests and responses for debugging purposes.
3. MPAndroidChart
   - [x] ```implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")``` <br>
         Used for visualizing data, such as Bitcoin prices and balances in charts.
4. Material Design
   - [x] ```implementation("com.google.android.material:material:1.9.0")```<br>
         Provides Material Design components for a polished and intuitive UI.
5. AppCompat
   - [x] ```implementation("androidx.appcompat:appcompat:1.6.1")```<br>
         Ensures backward compatibility for using modern UI elements on older Android versions.
6. DrawerLayout
   - [x] ```implementation("androidx.drawerlayout:drawerlayout:1.1.1")```<br>
         Used to create a side navigation menu.
7. Gson
   - [x] ```implementation("com.google.code.gson:gson:2.8.9")```<br>
         Serializes and deserializes JSON data to and from Java objects.




 ## **Examples**
This app is designed to use the **Navigation Viewer** to provide visualized graphs and related information in the selected fragment.<br>
The fragments consist of `BTCPriceFragment`, `BTCBalanceFragment`, `KRWBalanceFragment`, `TradeDecisionFragment`, and `FilteredTradeFragment`, making up a total of five fragments.
The descriptions of each fragment are as follows.

 
### 1.  Navigation Drawer
#### **You can easily navigate to various fragments for visualizing Bitcoin trading data using the navigation drawer.**
![navigation](https://github.com/user-attachments/assets/b96a748f-1c9d-432f-84df-0b170475877b)
<br>

-----

### 2.  BTC Price Fragment
#### **Visualizes the price of Bitcoin at the time each trade order was executed.**
| BTC Price Fragment 1     | BTC Price Fragment2     |
|--------------------|--------------------|
| ![btcprice](https://github.com/user-attachments/assets/60440330-35ef-4764-8a00-f42728035469) | ![btcPrice1](https://github.com/user-attachments/assets/68b3369c-d105-4461-981f-e17006d537c8) |
<br>

-----

### 3.  Trade Decision Fragment
#### **Visualizes the proportions of Buy, Hold, and Sell orders issued so far using a pie chart.**
![tradedecision](https://github.com/user-attachments/assets/43f4a052-8da4-4272-9df9-f72bcfc69796)<br>

-----

### 4.  BTC Balance Fragment
#### **Visualizes the amount of Bitcoin held immediately after each trade order using a line chart.**
![btcbalance](https://github.com/user-attachments/assets/3bf3c316-59c8-43b7-a699-9516429242dd)<br>

-----

### 5.  KRW Balance Fragment
#### **Visualizes the amount of cash (KRW) held immediately after each trade order using a line chart.**
![krwBalance](https://github.com/user-attachments/assets/6ac756c7-0dc4-413c-87c6-4d8b93d6c955)<br>

-----

### 6. Filtered Trade Fragment
#### **Displays the timestamps of all executed trade orders, the corresponding commands (Buy, Hold, Sell), and the reasons for each command.**
| Android App View 1     | Android App View 2     |
|--------------------|--------------------|
| ![filterReason](https://github.com/user-attachments/assets/7d5ed5e0-a374-4793-a0da-df2dfa97d65f) | ![filterReason1](https://github.com/user-attachments/assets/aa2c2c02-c15b-492b-9c27-216b98299579) |

-----

 ## **Limitations**
 * 
 * The functionality to zoom in or display specific parts of the graph has not been implemented yet, leading to reduced readability of the graph.

 ## **License**
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE_FILE_LINK) file for details.
