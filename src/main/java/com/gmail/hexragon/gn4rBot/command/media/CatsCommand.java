package com.gmail.hexragon.gn4rBot.command.media;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;

@Command(
        aliases = {"cats", "cat", "caaaaaaaaats"},
        description = "Get cats! What more do you want!?"
)
public class CatsCommand extends CommandExecutor
{
    @Override
    public void execute(GnarMessage message, String[] args)
    {
        try
        {
            String apiKey = "MTAyODkw";
            
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc;
            
            if (args.length >= 1 && args[0] != null)
            {
                switch (args[0])
                {
                    case "png":
                    case "jpg":
                    case "gif":
                        doc = db.parse(new URL(String.format("http://thecatapi.com/api/images/get?format=xml&type=%s&api_key=%s&results_per_page=1", args[0], apiKey)).openStream());
                        break;
                    default:
                        message.getChannel().sendMessage(String.format("%s ➜ Not a valid picture type. `[png, jpg, gif]`", message.getAuthor().getAsMention()));
                        return;
                }
            }
            else
            {
                doc = db.parse(new URL(String.format("http://thecatapi.com/api/images/get?format=xml&api_key=%s&results_per_page=1", apiKey)).openStream());
            }
            
            String catURL = doc.getElementsByTagName("url").item(0).getTextContent();
            
            message.replyRaw("Random Cat Pictures\nLink: " + catURL);
        }
        catch (Exception e)
        {
            message.reply("Unable to find cats to sooth the darkness of your soul.");
            e.printStackTrace();
        }
    }
}
