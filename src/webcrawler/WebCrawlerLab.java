package webcrawler;

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
            setDomain(Crawler.SUBTREE);
            setLinkType(Crawler.HYPERLINKS);
            setMaxDepth(maxDepth);
            setRoot(new Link(new URL(url)));
            this.pattern = pattern;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void visit(Page page) {
        whileInPage(page);
        try
        {
            Thread.sleep(500L);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean shouldVisit(Link link) {
        URL url = link.getURL();
        return (url.toString().contains(pattern));
    }

    protected void whileInPage(Page page) {
        URL url = page.getURL();
        System.out.println(url.toString());
    }
}
