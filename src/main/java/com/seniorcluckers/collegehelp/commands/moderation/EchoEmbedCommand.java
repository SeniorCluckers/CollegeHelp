package com.seniorcluckers.collegehelp.commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.seniorcluckers.collegehelp.utils.ChatUtil;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class EchoEmbedCommand extends Command {

    public EchoEmbedCommand() {
        this.name = "echo-em";
        this.arguments = "<channel> <title> <description>";
        this.help = "An echo embed command.";
    }

    @Override
    protected void execute(CommandEvent event) {
        String[] args = event.getArgs().split("\\s+", 3);

        if (args.length >= 3) {
            try {
                TextChannel textChannel = event.getGuild().getTextChannelById(args[0]);
                if (textChannel != null) {
                    String title = args[1];

                    StringBuilder builder = new StringBuilder();
                    for (int i = 2; i < args.length; i++) {
                        builder.append(args[i]);
                    }

                    textChannel.sendMessage(ChatUtil.createEmbed(Color.RED, title, builder.toString())).queue();
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
