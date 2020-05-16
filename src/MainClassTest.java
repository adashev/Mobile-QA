import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {
   @Test
   public void testGetLocalNumber() {
      MainClass main = new MainClass();
      Assert.assertTrue("метод getLocalNumber неверно рассчитывает сумму", main.getLocalNumber() == 12);
   }

   @Test
   public void testGetClassNumber() {
      MainClass main = new MainClass();
      Assert.assertTrue("метод getClassNumber возвращает число меньше 45", main.getClassNumber() > 45);
   }

}
