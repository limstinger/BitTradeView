# BitTradeView App

 ◭ 2024.12  **Mobile Programming Term Project**  ◭

 <br>

 ## **Introduction**
In this project, I aim to develop an app that integrates with the database of an automated Bitcoin trading program currently running on an EC2 cloud server.<br>
The app will visualize the trading data in various formats, providing insightful and interactive representations.

| Android App View 1     | Android App View 2     |
|--------------------|--------------------|
| ![Android_App_in_java_01](https://github.com/user-attachments/assets/dc93b9fe-b2dc-4ef8-810e-7a041219a966) | ![Android_App_in_java_02](https://github.com/user-attachments/assets/05e08325-19c0-414f-8556-008ec4e21a3b) |

 ## **Developer**
 임민규(Lim Mingyu)

 ## **Set Up & Prerequisites**
 Build environment:
  - [x] The `app.py` script enables external access to the Bitcoin trading database stored on the EC2 server, allowing the app to retrieve and utilize the data.
  - [X] Therefore, the `app.py` script must be running on the EC2 server before launching the app.

 ## **Examples**
This app is designed to use the **Navigation Viewer** to provide visualized graphs and related information in the selected fragment.<br>
The fragments consist of `BTCPriceFragment`, `BTCBalanceFragment`, `KRWBalanceFragment`, `TradeDecisionFragment`, and `FilteredTradeFragment`, making up a total of five fragments.
The descriptions of each fragment are as follows.

 
### 1.  Navigation Drawer
#### **You can easily navigate to various fragments for visualizing Bitcoin trading data using the navigation drawer.**
![스크린샷 2024-12-09 011430](https://github.com/user-attachments/assets/f271be7d-b3bf-4394-9b6f-42c2aff1cbd4)<br>

-----

### 2.  BTC Price Fragment
#### **Visualizes the price of Bitcoin at the time each trade order was executed.**
![스크린샷 2024-12-08 232237](https://github.com/user-attachments/assets/353751e6-f2e6-48e3-bba9-72a9f0728340)<br>

-----

### 3.  Trade Decision Fragment
#### **Visualizes the proportions of Buy, Hold, and Sell orders issued so far using a pie chart.**
![스크린샷 2024-12-08 231832](https://github.com/user-attachments/assets/d7596dc0-21ea-492e-9ac4-cdfbfb30efbb)<br>

-----

### 4.  BTC Balance Fragment
#### **Visualizes the amount of Bitcoin held immediately after each trade order using a line chart.**
![스크린샷 2024-12-09 021106](https://github.com/user-attachments/assets/7a7823eb-b1c5-4303-a6e0-129eb95bdf46)<br>

-----

### 5.  KRW Balance Fragment
#### **Visualizes the amount of cash (KRW) held immediately after each trade order using a line chart.**
![스크린샷 2024-12-08 231731](https://github.com/user-attachments/assets/42c115cd-439d-4d7f-934e-95916b4c4ef9)<br>

-----

### 6. Filtered Trade Fragment
#### **Displays the timestamps of all executed trade orders, the corresponding commands (Buy, Hold, Sell), and the reasons for each command.**
<div style="display: flex; justify-content: space-around;">
    <img src="https://github.com/user-attachments/assets/d00660a5-58ad-4f72-ad41-37cb8281d440" alt="스크린샷 2024-12-08 232550" width="45%" />
    <img src="https://github.com/user-attachments/assets/1abd9f78-ae49-4a7a-86a0-8a3cad7a8e89" alt="스크린샷 2024-12-08 232740" width="45%" /><br>
</div>

-----

 ## **Limitations**
 * If a network issue occurs while sending a database request packet to the EC2 server and receiving its response, it may result in a failure to visualize the data.
 * The functionality to zoom in or display specific parts of the graph has not been implemented yet, leading to reduced readability of the graph.

 ## **License**
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE_FILE_LINK) file for details.
