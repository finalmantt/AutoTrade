# autotrade
1. Install Eclipse 
2. Install TWS API  

# Setting TWS in Eclipse
1. Create Java project 
2. Put Project Name: autotrade 
3. De-select Use defualt location 
4. Select TWS API folder (root folder)
5. Go go main class at samples/Java/apidemo/ApiDemo.java

# Connect to IB Server
1. Use connection code in Apidemo.java

# How to implements interface 
1. Create a class and implemnts the interace we need. 
2. Overing function. In this step we can see the information using System.out.print("Somthing");.

# Process
1. Accoount, Posion and Order: crete when connect to the IB server. If there are any change, the function in account, position and order will be recieved the information.
2. RealTime: get real time bars every 5 seconds.
3. Historical: get historical data. In this step we can make a condition after receiveing the RealTime data and place an order.
