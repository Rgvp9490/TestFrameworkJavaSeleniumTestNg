package framework.locatorReader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.nio.file.Paths;
import java.time.Duration;

public class locatorHelper {
    public static WebElement getElement(WebDriver driver, Class<?> pageClass, String key) {
        try {
            // Get class name and build JSON path
            String pageName = pageClass.getSimpleName();
            String jsonPath = Paths.get("src", "test", "java", "web", "objectRepository", pageName, pageName + ".json").toString();

            System.out.println(" show me the path " + jsonPath);

            // Load and parse JSON
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(new File(jsonPath));
            System.out.println("🧾 JSON content: " + rootNode.toPrettyString());

            JsonNode locatorNode = rootNode.get(key);
            System.out.println("🔑 Looking for key: " + key);
            System.out.println("📌 Found node: " + locatorNode);

            if (locatorNode == null)
                throw new RuntimeException("Locator not found in JSON for key: " + key);

            String by = locatorNode.get("by").asText();
            String value = locatorNode.get("value").asText();

            By locator = getBy(by, value);
            return new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));

        } catch (Exception e) {
            throw new RuntimeException("Failed to locate element: " + key, e);
        }
    }

    private static By getBy(String by, String value) {
        return switch (by.toLowerCase()) {
            case "id" -> By.id(value);
            case "name" -> By.name(value);
            case "xpath" -> By.xpath(value);
            case "css" -> By.cssSelector(value);
            case "classname" -> By.className(value);
            case "linktext" -> By.linkText(value);
            case "partiallinktext" -> By.partialLinkText(value);
            case "tagname" -> By.tagName(value);
            default -> throw new IllegalArgumentException("Unsupported locator type: " + by);
        };
    }
}
