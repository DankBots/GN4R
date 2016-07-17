package com.gmail.hexragon.gn4rBot.command.ai;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

public class CleverbotCommand extends CommandExecutor
{
	ChatterBotFactory factory = null;
	ChatterBot bot = null;
	ChatterBotSession session = null;

	public CleverbotCommand(CommandManager manager)
	{
		super(manager);
		setDescription("Talk to Clever-Bot.");
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{

		try
		{
			if (factory == null)
			{
				factory = new ChatterBotFactory();
				bot = factory.create(ChatterBotType.CLEVERBOT);
				session = bot.createSession();
				event.getChannel().sendMessage(String.format("%s ➤ Clever-Bot session created for the server.", event.getAuthor().getAsMention()));
			}

			String input = StringUtils.join(args, " ");

			String output = session.think(input);
			event.getChannel().sendMessage("**[CleverBot]** ─ `" + output + "`");
		}
		catch (Exception e)
		{
			event.getChannel().sendMessage("CleverBot has encountered an exception. Resetting CleverBot.");
			factory = null;
		}
	}
}
