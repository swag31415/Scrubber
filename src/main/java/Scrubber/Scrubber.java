package Scrubber;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Scrubber {

    private static WebClient wClient;

    public Scrubber() {
        Scrubber.wClient = new WebClient(BrowserVersion.CHROME);
    }

    public Scrubber(WebClient wClient) {
        Scrubber.wClient = wClient;
    }

    public <T> T Scrub(Function<HtmlPage, T> scrubberFunc, String url) {
        try {
            return scrubberFunc.apply(Scrubber.wClient.getPage(url));
        } catch (FailingHttpStatusCodeException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> List<T> Scrub(Function<HtmlPage, T> scrubberFunc, String... urls) {
        List<T> Ts = new ArrayList<T>();
        for (String url : urls) {
            Ts.add(Scrub(scrubberFunc, url));
        }
        return Ts;
    }
}