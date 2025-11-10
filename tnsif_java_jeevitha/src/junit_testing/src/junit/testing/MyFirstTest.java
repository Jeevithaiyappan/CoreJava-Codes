package junit.testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MyFirstTest {
	
	@Test //basic testing annotation
	void display()
	{
		System.out.println("Heloo Java coders");
	}
	@BeforeEach
	void beforeEachTest()
	{
		System.out.println("javacode");
	}
}

