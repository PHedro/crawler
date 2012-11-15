package rss;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.TextExtractor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: phedro
 * Date: 11/15/12
 * Time: 9:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class RSSLab
{

    private URL feedUrl = null;
    private XmlReader reader = null;

    public List getEntriesFrom(String url)
    {
        List result = null;
        try
        {
            this.feedUrl = new URL(url);
            this.reader = new XmlReader(this.feedUrl);

            SyndFeed feed = new SyndFeedInput().build(this.reader);

            result = feed.getEntries();
        }
        catch (MalformedURLException e)
        {
            System.out.println("URL mal formada " + url);
            e.printStackTrace();
        }
        catch (IOException e)
        {
            System.out.println("XML em  " + url);
            e.printStackTrace();
        }
        catch (FeedException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public List getEntriesFromUrls(List<String> urls)
    {
        List result = null;
        if(urls != null)
        {
            for(String url : urls)
            {
                List partial = getEntriesFrom(url);
                if (partial != null)
                {
                    if(result == null)
                    {
                        result = new ArrayList();
                    }
                    result.addAll(partial);
                }
            }
        }
        return result;
    }

    public void getEntriesFromUrlsAndSave(List<String> urls)
    {
        List entries = getEntriesFromUrls(urls);
        saveEntries(entries);
    }

    public void getEntriesFromUrlAndSave(String url)
    {
        List entries = getEntriesFrom(url);
        saveEntries(entries);
    }

    public void saveEntries(List entries)
    {
        if(entries != null)
        {
            int fails = entries.size();
            int sucess = 0;

            for(Object obj : entries)
            {
                if(obj instanceof SyndEntry)
                {
                    if(saveEntry((SyndEntry)obj))
                    {
                        sucess++;
                    }
                }
            }
            fails -= sucess;

            System.out.println("Foram salvos " + sucess + " feeds e " + fails + " falharam.");
        }
        System.out.println("Nenhum feed disponivel. ");
    }

    public boolean saveEntry(SyndEntry entry)
    {
        boolean result = false;

        if(entry != null)
        {
            String title = entry.getTitle();
            String content = entry.getDescription().getValue();
            String author = entry.getAuthor();
            String data = entry.getPublishedDate().toString();
            String link = entry.getLink();
            String source = "RSS";


            Source src = new Source(content);
            String parsedContent =src.getTextExtractor().setIncludeAttributes(false).toString();

            System.out.println(source + title + parsedContent + author + data + link);
        }

        return result;
    }

    public void close()
    {
        try
        {
            if(this.reader != null)
                this.reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
