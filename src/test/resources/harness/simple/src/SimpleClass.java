import com.mpdeimos.chacy.Chacy;

@Chacy.Type
public class SimpleClass
{
	public void publicMethod()
	{
		System.out.println("bar");
	}

	protected void protectedMethod()
	{
		System.out.println("bar");
	}
	
	protected void privateMethod()
	{
		System.out.println("bar");
	}
	
	/* package*/ void packagePrivateMethod()
	{
		System.out.println("bar");
	}
}
