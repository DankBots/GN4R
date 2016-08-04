package com.gmail.hexragon.gn4rBot.command.mod;

import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import net.dv8tion.jda.MessageHistory;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.entities.TextChannel;
import net.dv8tion.jda.exceptions.PermissionException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Command(
		aliases = {"deletemessages"},
		usage = "(integer)",
		description = "Delete an amount of recent messages from the server.",
		permissionRequired = PermissionLevel.BOT_COMMANDER
)
public class DeleteMessagesCommand extends CommandExecutor
{
	@Override
	public void execute(GnarMessage message, String[] args)
	{
		if (args.length == 0)
		{
			message.reply("Insufficient amount of arguments.");
			return;
		}
		
		MessageHistory messageHistory = message.getChannel().getHistory();
		
		int amount = Integer.valueOf(args[0]);
		amount = Math.min(amount, 100);
		
		if (amount < 2)
		{
			message.reply("You need to delete 2 or more messages to use this command.");
			return;
		}
		
		List<Message> messages = messageHistory.retrieve(amount);
		
		if (args.length == 2)
		{
			if (args[1].contains("-content:"))
			{
				String targetWord = args[1].replaceFirst("-content:","");
				Set<Message> removeSet = messages.stream().filter(msg -> msg.getContent().toLowerCase().contains(targetWord.toLowerCase())).collect(Collectors.toSet());
				
				try
				{
					((TextChannel) message.getChannel()).deleteMessages(removeSet);
				}
				catch (PermissionException e)
				{
					message.reply("GN4R does not have sufficient permission to delete messages.");
				}
				message.reply("Attempted to delete `" + removeSet.size() + "` messages with the word `"+targetWord+"`.");
				return;
			}
		}
		
		((TextChannel) message.getChannel()).deleteMessages(messages);
		message.getChannel().sendMessage(message.getAuthor().getAsMention() + " ➜ Attempted to delete `" + messages.size() + "` messages.");
	}
}