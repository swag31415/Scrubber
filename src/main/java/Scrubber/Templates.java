package Scrubber;

import java.util.function.Function;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Templates {

    public static final Function<HtmlPage, String> GET_TEXT = p -> {
        return p.getBody().asText();
    };

}