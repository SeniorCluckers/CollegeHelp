package com.seniorcluckers.collegehelp.listeners;

import com.seniorcluckers.collegehelp.utils.ChatUtil;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.Color;
import java.io.File;
import java.util.List;

public class FileDetection extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (!event.getAuthor().isBot()) {
            if (event.getMessage().getAttachments().size() > 0) {

                List<Message.Attachment> attachments = event.getMessage().getAttachments();

                for (Message.Attachment attachment : attachments) {
                    // TODO add a check for file extension

                    final String courseName = event.getChannel().getName()
                            .replaceAll("(^[^-]*-[^-]*)(-.*)", "$1");

                    File path = new File("./data/attachments/" + courseName);
                    if (!path.exists()) {
                        path.mkdirs();
                    }
                    String fileName = event.getMessage().getId() + "_" + attachment.getFileName();
                    attachment.downloadToFile("./data/attachments/" + courseName + "/" + fileName).thenAccept(
                            file -> {
                        event.getGuild().getTextChannelsByName(courseName + "-resources", true).get(0)
                                .sendMessage(ChatUtil.createEmbed(Color.RED, attachment.getFileName(),
                                        "Resource By: " + event.getAuthor().getName())).addFile(file).queue();
                    });
                }
            }
        }
    }
}
