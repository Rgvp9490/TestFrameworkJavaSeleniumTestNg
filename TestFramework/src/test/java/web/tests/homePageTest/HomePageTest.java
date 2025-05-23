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

    @Test
    public void Test2() {
        homePage home = new homePage(driver);
        Logger.info("Starting Test2");
        home.maximizeWindow();
        home.searchYouTube("hello bro");
    }
}


// mvn test -Dtest=HomePageTest    - run class
// mvn test -Dtest=HomePageTest#Test1  - run individual test
// mvn test "-DsuiteXmlFile=src/test/java/suites/experiment.xml"   - run suite
// mvn clean package "exec:java" "-Dexec.args=-c defaultConfig"    - run current config
