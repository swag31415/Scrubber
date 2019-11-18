package Scrubber;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Function;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Templates {

    public static final Function<HtmlPage, String> GET_TEXT = p -> {
        return p.getBody().asText();
    };

    public static final Function<HtmlPage, String> GET_IMPORTANT_TEXT = p -> {
        double limit = .61803398875;
        HtmlElement element = p.getBody();

        boolean done = false;
        while (!done) {
            done = true;
            for (HtmlElement childElement : element.getHtmlElementDescendants()) {
                if (((double) childElement.asText().length() / (double) element.asText().length()) > limit) {
                    element = childElement;
                    done = false;
                }
            }
        }
        return element.asText();
    };

    public static final Function<HtmlPage, URL[]> GET_LINKS = p -> {
        return p.querySelectorAll("a").stream().map(a -> {
            try {
                return p.getFullyQualifiedUrl(((HtmlAnchor) a).getAttribute("href"));
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
        }).toArray(URL[]::new);
    };
}