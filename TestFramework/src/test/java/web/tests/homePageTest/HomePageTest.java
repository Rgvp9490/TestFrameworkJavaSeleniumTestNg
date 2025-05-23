package web.tests.homePageTest;

import framework.init.base;
import framework.performAction.autoweb.Wait;
import org.testng.annotations.Test;
import web.objectRepository.homePage.homePage;
import org.tinylog.Logger;

public class HomePageTest extends base {

    @Test
    public void Test1() {
        homePage home = new homePage(driver);
        Logger.info("Starting Test1");
        home.maximizeWindow();
        home.searchYouTube("Bruno Mars Uptown Funk");
    }
}
