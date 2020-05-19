import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {
   MainClass main = new MainClass();

   @Test
   public void testGetLocalNumber() {
      Assert.assertTrue("getLocalNumber() неверно рассчитывает сумму", main.getLocalNumber() == 12);
   }

   @Test
   public void testGetClassNumber() {
      Assert.assertTrue("getClassNumber() возвращает число меньше 45", main.getClassNumber() > 45);
   }

   @Test
   public void testGetClassString() {
      Assert.assertTrue("getClassString() возвращает строку, не содержащую подстроку hello или Hello", main.getClassString().startsWith("hello")
            || main.getClassString().startsWith("Hello"));
   }

}
