package com.seniorcluckers.collegehelp.listeners;

import com.seniorcluckers.collegehelp.CollegeHelp;
import com.seniorcluckers.collegehelp.utils.ChatUtil;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.lang3.RandomStringUtils;

import java.awt.Color;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

public class RantChannelListener extends ListenerAdapter {

    private final CollegeHelp collegeHelp;

    private HashSet<String> openRants = new HashSet<>();

    public RantChannelListener(CollegeHelp collegeHelp) {
        this.collegeHelp = collegeHelp;
    }

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {

        if (event.getChannel().getName().equalsIgnoreCase("\uD83D\uDE21rants")) {
            if (!event.getUser().isBot()) {
                if (event.getReactionEmote().getEmote().getName().equalsIgnoreCase("replymessage")) {
                    event.getReaction().removeReaction(event.getUser()).queue();

                    event.getUser().openPrivateChannel().queue(success -> {
                        if (openRants.contains(event.getUserId())) {
                            return;
                        } else {
                            openRants.add(event.getUserId());
                        }
                        success.sendMessage("<@" + event.getUserId() + "> type your rant here securely.").queue();
                        success.sendMessage("You have 60 seconds to type your rant.").queue();

                        collegeHelp.getEventWaiter().waitForEvent(PrivateMessageReceivedEvent.class,
                                e -> e.getAuthor().getIdLong() == event.getMember().getIdLong(),
                                e -> {
                                    openRants.remove(event.getUserId());
                                    success.sendMessage("Your rant has been sent successfully!").queue();
                                    event.getChannel().sendMessage(ChatUtil.createEmbed(event.getGuild(), Color.CYAN,
                                            "Rant #" + RandomStringUtils.random(8, "0123456789acixn"),
                                            e.getMessage().getContentRaw())).queue(message -> {
                                                message.addReaction(":replymessage:778011321190187009").queue();
                                    });
                                }, 60, TimeUnit.SECONDS, () -> {
                                    success.sendMessage("You ran out of time. You can react in the rant channel to try again.").queue();
                                    openRants.remove(event.getUserId());
                                });
                    });
                }
            }
        }
    }

}
