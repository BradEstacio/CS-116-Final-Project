
public class OneTimeOrdersTest {

	public static void main(String[] args) 
	{
		try
		{
			//Test 1
			System.out.println("Test 1");
			OneTimeOrders test1 = new OneTimeOrders("HAL Industries", "C2", "4/21/2005", 10);
			System.out.println(test1.toString());
			System.out.println();
			//Test 2
			System.out.println("Test 2");
			OneTimeOrders test2 = new OneTimeOrders("HAL Industries", "C2", "4/21/2005", 10);
			test2.setProductID("C6");
			test2.setDate("7/12/2019");
			test2.setCustomerID("Peak Enterprises");
			test2.setAmount(20);
			System.out.println(test2.toString());
			System.out.println();
			//Test 3
			System.out.println("Test 3");
			OneTimeOrders test3 = new OneTimeOrders("HAL Industries", "C2", "4/21/2005", 10);
			test3.setDate("13/7/2019");
		}
		catch(OneTimeOrdersException e)
		{
			System.out.println(e.getMessage());
		}
	}

}
