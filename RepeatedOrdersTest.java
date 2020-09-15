
public class RepeatedOrdersTest 
{

	public static void main(String[] args) 
	{
		try
		{
			//Test 1
			System.out.println("Test 1");
			RepeatedOrders test1 = new RepeatedOrders("Giggle Industries", "B1", "2/3/2003", 8, 1, "2/24/2003");
			System.out.println(test1.toString());
			System.out.println();
			//Test 2
			System.out.println("Test 2");
			RepeatedOrders test2 = new RepeatedOrders("Giggle Industries", "B1", "2/3/2003", 8, 1, "2/24/2003");
			test2.setEndDate("3/24/2003");
			test2.setPeriod(3);
			System.out.println(test2.toString());
			//Test 3
			System.out.println("Test 3");
			RepeatedOrders test3 = new RepeatedOrders("Giggle Industries", "B1", "2/3/2003", 8, 1, "2/24/2003");
			test3.setEndDate("1/3/2003");
		}
		catch(RepeatedOrdersException e)
		{
			System.out.println(e.getMessage());
		}
	}

}
