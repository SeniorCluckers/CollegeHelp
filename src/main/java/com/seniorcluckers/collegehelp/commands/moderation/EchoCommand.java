package com.seniorcluckers.collegehelp.commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.seniorcluckers.collegehelp.utils.ChatUtil;
import net.dv8tion.jda.api.entities.TextChannel;

public class EchoCommand extends Command {

    public EchoCommand() {
        this.name = "echo";
        this.arguments = "<channel> <message>";
        this.help = "An echo command.";
    }

    @Override
    protected void execute(CommandEvent event) {
        String[] args = event.getArgs().split("\\s+", 2);

        if (args.length >= 2) {
            try {
                TextChannel textChannel = event.getGuild().getTextChannelById(args[0]);
                if (textChannel != null) {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 1; i < args.length; i++) {
                        builder.append(args[i]);
                    }
                    textChannel.sendMessage(builder.toString()).queue();
                } else {
                    event.reply("Error. Channel was not found!");
                }
            } catch (NumberFormatException exception) {
                event.reply("Error. Invalid channel.");
            }
        } else {
            event.reply(ChatUtil.createEmbedMissingArgs(event, this));
        }
    }
}
