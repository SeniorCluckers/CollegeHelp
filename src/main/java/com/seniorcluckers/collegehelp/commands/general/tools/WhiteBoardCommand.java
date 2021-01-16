package com.seniorcluckers.collegehelp.commands.general.tools;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.UUID;

public class WhiteBoardCommand extends Command {

    public WhiteBoardCommand() {
        this.name = "whiteboard";
        this.help = "generates a whiteboard link";
        this.aliases = new String[] {"wb"};
    }

    @Override
    protected void execute(CommandEvent event) {
        event.getMessage().delete().queue();

        UUID uuid = UUID.randomUUID();
        event.reply(new EmbedBuilder().setTitle("WhiteBoard Generated").setDescription("You can access your whiteboard using the following link: \n" +
                "https://witeboard.com/" + uuid).setFooter(event.getGuild().getName(), event.getGuild().getIconUrl()).build());
    }
}
