# CS-116-Final-Project
Final project Java files from CS 116 - Object Oriented Programming

# General Overview
 This was a final project I had worked on for my java class with a lab partner, Patrick Inosanto. The final project was to create an Office Supply Order System included reading/writing files, asking for user input with 
 conditions, writing and throwing exceptions, and using inheritance with interfaces and classes. Basic Javadocs included inside files. Two text files were included for testing
 the main class, those being `orderssmall.txt.` and `testFile.txt`. Another java file was required to randomly generate a text file.


# OfficeSupplyUI.java
Methods inside the UI throw `OfficeSupplyUIException.java`.

The UI allows the following options for the user:
1. Specify a file name of orders to load (omitting .txt)
2. Specify the information to add on order or a repeated order.
3. Specify an order ID to delete.
4. Specify a customer ID to display a list of orders for that customer in inceasing date sorted order.
5. Calculate and output and order inventory report (sorted by year, month, and productID)
6. Quit - Allows the user to quit the program.
  
Assumptions:
- User must have loaded a file before being able to access options 2-5
- If the user loads a new file, the program assumes the user wants to overwrite a previous file and states it when `loadFile()` is run.

When the program is first run:
```
Welcome to the Office Supply Order System. How may we help you today?
Enter a number corresponding to the options listed below...
1. Load a file
2. Add an order or repeated order
3. Delete an order
4. List orders for a specific customer
5. Inventory report
6. Quit
```

## OrderManager.java
This class manages both one time and repeated orders via an array list where they're stored. This class includes a method that sorts all the orders by date. The order
manager is mainly used for adding/deleting orders, listing orders for a specific customer ID, and to calculate and report for each month on the inventory needed for each product
ID based on the current orders stored. Class throws `OrderManagerException`.

## OneTimeOrders.java
This class makes one time orders for the order manager which has instance variables for customerID, productID, date, and amount. Class implements `Comparable<OneTimeOrders>` 
and throws `OneTimeOrdersException`.

#### OneTimeOrdersTest.java
Test for `OneTimeOrders.java in order to make sure code was running properly. 

## RepeatedOrders.java
This class makes repeated orders for OrderManager and is a child class of `OneTimeOrders.java` which throws `RepeatedOrdersException`. Along with the additional instance
variables from the one time orders, this class includes a period of how often the order should occur and an end date.

# RandomFileGenerator.java
This program generates a random file called `randomOrders.txt`. It asks the user for a number which tells it the number of lines that will be generated, while also making sure
it is a positive integer. It uses `companylist.txt` to fill `static ArrayList<String> companyNames`.

Assumptions:
- Customer IDs are only from `companylist.txt`
- Product IDs are alphanumerical
- Dates are in 2019
- Order amounts are integers 100<
- Periods in repeated orders are 1-30 days.
- Repeated order end dates are in 2019.
