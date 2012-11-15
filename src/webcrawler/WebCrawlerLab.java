package webcrawler;

import websphinx.Crawler;
import websphinx.DownloadParameters;

/**
 * Created with IntelliJ IDEA.
 * User: phedro
 * Date: 11/15/12
 * Time: 3:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class WebCrawlerLab extends Crawler
{
    public WebCrawlerLab()
    {
        super();
        DownloadParameters params = new DownloadParameters();
        params.changeObeyRobotExclusion(true);
        params.changeUserAgent("MyCrawler Mozilla/5.0 (X11; U; Linux x86_64; en-US; rv:1.8.1.4) WebSPHINX 0.5 contact me_at_mycompany_dot_com");
        setDownloadParameters(params);
        setDomain(Crawler.SUBTREE);
        setLinkType(Crawler.HYPERLINKS);
    }
}
