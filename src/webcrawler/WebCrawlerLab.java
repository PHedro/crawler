package webcrawler;

import net.htmlparser.jericho.Source;
import websphinx.Crawler;
import websphinx.DownloadParameters;
import websphinx.Link;
import websphinx.Page;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: phedro
 * Date: 11/15/12
 * Time: 3:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class WebCrawlerLab extends Crawler
{
    private static String pattern = "";

    public WebCrawlerLab(String url, int maxDepth, String pattern)
    {
        super();
        try {
            DownloadParameters params = new DownloadParameters();
            params.changeObeyRobotExclusion(true);
            params.changeUserAgent("MyCrawler Mozilla/5.0 (X11; U; Linux x86_64; en-US; rv:1.8.1.4) WebSPHINX 0.5 contact me_at_mycompany_dot_com");
            setDownloadParameters(params);
            setMaxDepth(maxDepth);
            setRoot(new Link(new URL(url)));
            setDomain(Crawler.SERVER);
            setLinkType(Crawler.ALL_LINKS);
            this.pattern = pattern;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void visit(Page page) {
        whileInPage(page);
    }

    @Override
    public boolean shouldVisit(Link link) {
        URL url = link.getURL();
        return (url.toString().contains(pattern));
    }

    protected void whileInPage(Page page) {
        URL url = page.getURL();

        Source src = new Source(page.getContent());
        String parsedContent =src.getTextExtractor().setIncludeAttributes(false).toString();

        System.out.println("WEBCRAWLER");
        System.out.println(url.toString());
        System.out.println(parsedContent);

    }
}
