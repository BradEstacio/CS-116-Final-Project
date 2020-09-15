/**
 * @author Bradley Estacio and Patrick Inosanto
 * 12/6/19
 * 
 * Class that manages both one time and repeated orders -
 * Uses OneTimeOrders and RepeatedOrders
 * Has one array list.
 * The array list is where all the OneTimeOrders/RepeatedOrders are stored.
 * Also has a method that sorts all the orders by date.
 * 
 * Uses of OrderManager:
 * Adds orders
 * Deletes order - @param orderID
 * List orders for a particular customer ID in increasing date and sorted order - @param customerID
 * Calculate and report for each month on inventory needed for each product ID (based on current orders)
 */
import java.util.*;

public class OrderManager 
{
	private ArrayList<OneTimeOrders> orders = new ArrayList<OneTimeOrders>();
	private int removeCounter = 1;
	public OrderManager(String order)
	{
		String[] orderSplit = order.split(","); //splits string taken into file for ease of sorting
		if(orderSplit[0].equals("O"))
		{
			OneTimeOrders newOrder = new OneTimeOrders(orderSplit[1], orderSplit[2], orderSplit[3], Integer.parseInt(orderSplit[4]));
			orders.add(newOrder);
		}
		else if(orderSplit[0].equals("R"))
		{
			RepeatedOrders newOrder = new RepeatedOrders(orderSplit[1], orderSplit[2], orderSplit[3], Integer.parseInt(orderSplit[4]), Integer.parseInt(orderSplit[5]), orderSplit[6]);
			orders.add(newOrder);
		}
	}
	
	//adds orders
	public void addOrder(String nextOrder)
	{
		String[] orderSplit = nextOrder.split(","); //splits string taken into file for ease of sorting
		if(orderSplit[0].equals("O"))
		{
			OneTimeOrders newOrder = new OneTimeOrders(orderSplit[1], orderSplit[2], orderSplit[3], Integer.parseInt(orderSplit[4]));
			orders.add(newOrder);
		}
		else if(orderSplit[0].equals("R"))
		{
			RepeatedOrders newOrder = new RepeatedOrders(orderSplit[1], orderSplit[2], orderSplit[3], Integer.parseInt(orderSplit[4]), Integer.parseInt(orderSplit[5]), orderSplit[6]);
			orders.add(newOrder);	
		}
	}
	
	//deletes orders - removeCounter++ 
	public void deleteOrder(int orderID) throws OrderManagerException
	{
		if(orderID - removeCounter > orders.size() || orderID < 0) //if statement makes sure orderID is valid
		{
			throw new OrderManagerException("orderID does not exist.");
		}
		else
		{
			orders.remove(orderID - removeCounter);
			this.removeCounter++;
		}
	}
	
	//lists orders for specific customer ID
	//must be increasing date sorted order
	public void listOrder(String customerID) throws OrderManagerException
	{
		boolean validCustomerID = false;
		//makes sure that customerID exists
		for(int i = 0; i < orders.size(); i++)
		{
			if(orders.get(i).getCustomerID().equals(customerID))
			{
				validCustomerID = true;
			}
		}

		if(validCustomerID)
		{
			Collections.sort(orders);
			for(int j = 0; j < orders.size(); j++)
			{
				if(orders.get(j).getCustomerID().equals(customerID))
				{
					System.out.println(orders.get(j).toString());
				}
			}
		}
		else
		{
			throw new OrderManagerException("Sorry. This Customer ID does not exist.");
		}
	}
	//Just prints toString of OneTimeOrders or RepeatedOrders
	//All in one single line if sorted beforehand
	public String toString()
	{
		String totalOrders = "";
		for(int i = 0; i < orders.size(); i++)
		{
			totalOrders += orders.get(i).toString() + "\n";
		}
		return totalOrders;
	}
	
	/**
	 * Inventory report is sorted either by year, month, and productID
	 * @see OfficeSupplyUI - inventoryReport
	 * @see OneTimeOrders compareTo
	 * Note: .getMonth() return 0-11 so reports will print out
	 * months in numbers 1-12 representing each respective month
	 * Note: when reporting inventory with repeated orders, it is stated
	 * that it is one, and the end date of the repeated order
	 */
	public void inventoryReport()
	{
		//initializing variables
		int previousMonth = 0;
		int previousYear = 0;
		int productIDAmount = 0;
		int thisMonth, thisYear;
		boolean sameMonth, sameYear, sameProductID;
		String previousProductID = "";
		String thisProductID;
		
		Collections.sort(orders);
		//everything should now be sorted by date, month, and product ID
		for(int i = 0; i < orders.size(); i++)
		{
			thisMonth = orders.get(i).getDate().getMonth() + 1;
			thisYear = orders.get(i).getDate().getYear();
			thisProductID = orders.get(i).getProductID();
			//checks if orders.get(i) is instance of RepeatedOrders so they can add properly
			if(orders.get(i) instanceof RepeatedOrders)
			{
				RepeatedOrders tempOrder = (RepeatedOrders) orders.get(i); //temporarily casted to get amount for repeated order
				productIDAmount += tempOrder.getAmount() + Math.abs((tempOrder.getAmount() * ((tempOrder.getEndDate().getDate() - tempOrder.getDate().getDate()) / tempOrder.getPeriod())));
			}
			else
			{
				productIDAmount += orders.get(i).getAmount();
			}
			//checks to make it can combine year/month/productIDs
			if(previousMonth != thisMonth)
			{
				sameMonth = false;
				previousMonth = thisMonth;
			}
			else 
			{
				sameMonth = true;
			}
			
			if(previousYear != thisYear)
			{
				sameYear = false;
				previousYear = thisYear;
			}
			else
			{
				sameYear = true;
			}
			
			if(!previousProductID.equals(thisProductID))
			{
				sameProductID = false;
				previousProductID = thisProductID;
			}
			else
			{
				sameProductID = true;
			}
					
			if(!sameYear)
			{
				System.out.println("Year: " + previousYear);
			}
			if(!sameMonth)
			{
				System.out.println("\tMonth: " + previousMonth);
			}
			if(!sameProductID)
			{
				System.out.println("\t\tNeed " + productIDAmount + " of " + previousProductID);
				productIDAmount = 0;
			}
		}
	}	
}
	

	
	
	
	
