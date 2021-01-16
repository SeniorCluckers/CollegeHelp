package com.seniorcluckers.collegehelp.utils;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ChatUtil {

    public static MessageEmbed createEmbed(Guild guild, Color color, String title, String description) {
        return new EmbedBuilder().setColor(color).setTitle(title).setDescription(description).setFooter(guild.getName(), guild.getIconUrl()).build();
    }

    public static MessageEmbed createEmbed(Color color, String title, String description) {
        return new EmbedBuilder().setColor(color).setTitle(title).setDescription(description).build();
    }

    public static MessageEmbed createEmbed(Color color, String title, String description, List<MessageEmbed.Field> fields) {
        EmbedBuilder embedBuilder = new EmbedBuilder().setColor(color).setTitle(title).setDescription(description);

        for (MessageEmbed.Field field : fields) {
            embedBuilder.addField(field);
        }

        return embedBuilder.build();
    }

    public static MessageEmbed createEmbedMissingArgs(CommandEvent event, Command command) {
        List<MessageEmbed.Field> fields = new ArrayList<>();
        fields.add(new MessageEmbed.Field("Usage:",event.getClient().getPrefix() + command.getName() + " "
                + command.getArguments(), false));
        return ChatUtil.createEmbed(Color.RED, "You're missing arguments.", "", fields);
    }

}
