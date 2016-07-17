package com.gmail.hexragon.gn4rBot.command.media;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;

public class CatsCommand extends CommandExecutor
{
	public CatsCommand(CommandManager manager)
	{
		super(manager);
		setDescription("They are evil, I tell you.");
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args)
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
						event.getChannel().sendMessage(String.format("%s ➤ Not a valid picture type. `[png, jpg, gif]`", event.getAuthor().getAsMention()));
						return;
				}
			}
			else
			{
				doc = db.parse(new URL(String.format("http://thecatapi.com/api/images/get?format=xml&api_key=%s&results_per_page=1", apiKey)).openStream());
			}

			String catURL = doc.getElementsByTagName("url").item(0).getTextContent();

			event.getChannel().sendMessage("Random Cat Pictures\nLink: "+catURL);
		}
		catch (Exception e)
		{
			event.getChannel().sendMessage(String.format("%s ➤ Unable to find cats to sooth the darkness of your soul.", event.getAuthor().getAsMention()));
			e.printStackTrace();
		}
	}
}
