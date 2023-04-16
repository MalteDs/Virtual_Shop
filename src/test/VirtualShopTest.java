package test;
import junit.framework.TestCase;
import model.*;
public class VirtualShopTest extends TestCase {
    private VirtualShop virtualShop;

    public void setUpScenario1(){
        virtualShop = new VirtualShop();
    }

    public void setUpScenario2(){
        setUpScenario1();
    }
}
