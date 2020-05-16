import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {
   @Test
   public void testGetLocalNumber() {
      MainClass main = new MainClass();
      Assert.assertTrue("метод getLocalNumber неверно рассчитывает сумму", main.getLocalNumber() == 12);
   }
}
