package webapp;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import webapp.service.CalcListService;

@SpringBootTest
public class CalcTest {

private CalcListService test;
	
	@BeforeEach
	public void setUp() {
		test = new CalcListService();
	}
	
	/*足し算*/
	@Test
	public void test001() {
		/*期待値*/
		BigDecimal expected = BigDecimal.valueOf(30);
		
		BigDecimal a = BigDecimal.valueOf(15);
        BigDecimal b = BigDecimal.valueOf(15);
		/*実際値*/
		BigDecimal actual = test.performCalculation(a, "+", b);
		
		assertThat(actual, is(expected));
	}
	
	/*引き算*/
	@Test
	public void test002() {
		
		BigDecimal expected = BigDecimal.valueOf(15);
		
		BigDecimal a = BigDecimal.valueOf(30);
        BigDecimal b = BigDecimal.valueOf(15);
		
		BigDecimal actual= test.performCalculation(a, "-", b);
		
		assertThat(actual, is(expected));
	}
	
	/*かけ算*/
	@Test
	public void test003() {
		
		BigDecimal expected =  BigDecimal.valueOf(15);
		
		BigDecimal a = BigDecimal.valueOf(3);
        BigDecimal b = BigDecimal.valueOf(5);
		
		BigDecimal actual = test.performCalculation(a, "*", b);
		
		assertThat(actual, is(expected));
	}
	
	/*わり算*/
	@Test
	public void test004() {
		
		BigDecimal expected = BigDecimal.valueOf(100);
		
		
		BigDecimal a = BigDecimal.valueOf(1000);
        BigDecimal b = BigDecimal.valueOf(10);
		
		BigDecimal actual = test.performCalculation(a, "/", b);
		
		assertThat(actual.setScale(0, RoundingMode.HALF_UP), is(expected));
	}
	
}
