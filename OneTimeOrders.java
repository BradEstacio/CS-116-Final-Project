/**
 * @author Bradley Estacio and Patrick Inosanto
 * 12/6/19
 * 
 * This class is used to make one time orders for Order Manager
 * Uses date class and .split method - used for organizing in OrderManager.
 * Has get/set methods for customerID, productID, date and amount
 * and has toString method.
 * Has methods that @throws OneTimeOrdersException
 * @implements Comparable <OneTimeOrders>
 * Comparable interface compares two OneTimeOrder objects.
 * This will be used in OrderManager for inventoryReport
 */
import java.util.*;
public class OneTimeOrders implements Comparable<OneTimeOrders>
{
	private String customerID;
	private String productID;
	private Date date;
	private int amount;
	
	public OneTimeOrders(String customerID, String productID, String date, int amount) 
	{
		this.customerID = customerID;
		this.productID = productID;
		try
		{
			setDate(date);
		}
		catch(OneTimeOrdersException e)
		{
			System.out.println(e.getMessage());
		}
		this.amount = amount;
	}
	
	//getters	
	public String getCustomerID()
	{
		return customerID;
	}
	
	public String getProductID()
	{
		return productID;
	}
	
	public Date getDate()
	{
		return date;
	}
	
	public int getAmount()
	{
		return amount;
	}
	//setters
	public void setCustomerID(String customerID)
	{
		this.customerID = customerID;
	}
	
	public void setProductID(String productID)
	{
		this.productID = productID;
	}
	
	public void setDate(String date) throws OneTimeOrdersException
	{	
		String[] splitDate = date.split("/"); //splits string of date into string for date class
		if((Integer.parseInt(splitDate[0]) > 12) || (Integer.parseInt(splitDate[0]) < 0) || (Integer.parseInt(splitDate[1]) > 31) || ((Integer.parseInt(splitDate[1]) < 0)) 
				|| (Integer.parseInt(splitDate[2]) < 0) || (Integer.parseInt(splitDate[2]) > 2019)) //makes sure mm/dd/yyyy is valid
		{
			throw new OneTimeOrdersException("Sorry you entered an invalid date. Please try again.");
		}
		Date makeDate = new Date(Integer.parseInt(splitDate[2]), Integer.parseInt(splitDate[0]) - 1, Integer.parseInt(splitDate[1])); //month is minus one due to how date class works
		this.date = makeDate;
	}
	
	public void setAmount(int amount)
	{
		this.amount = amount;
	}
	//compares two OneTimeOrderObject objects when sorting for inventory report
	//order of comparing: year -> month -> productID
	//Used for Collections.sort(orders); @see OrderManager - inventoryReport
	public int compareTo(OneTimeOrders compared)
	{
		int comparing;
		comparing = this.date.getYear() - compared.getDate().getYear();
		if(comparing == 0)
		{
			comparing = this.date.getMonth() - compared.getDate().getMonth();
		}
		if(comparing == 0)
		{
			comparing = this.productID.compareToIgnoreCase(compared.getProductID());
		}
			
		return comparing;
	}
	
	public String toString()
	{
		return "Customer ID: " + getCustomerID() + ", Product ID: " + getProductID() + ", Date ordered: " + this.date.toString() + ", Amount: " + getAmount();
	}
}
