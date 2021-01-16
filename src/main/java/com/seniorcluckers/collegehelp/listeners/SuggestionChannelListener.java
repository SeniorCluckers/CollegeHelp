package com.seniorcluckers.collegehelp.listeners;

import com.seniorcluckers.collegehelp.utils.ChatUtil;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.Color;

public class SuggestionChannelListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (event.getChannel().getName().equalsIgnoreCase("\uD83D\uDCEBsuggestions")) {
            if (!event.getAuthor().isBot()) {
                event.getMessage().delete().queue();

                if (event.getMessage().toString().contains("CHANNEL_PINNED_ADD")) {
                    return;
                }
                event.getChannel().sendMessage(ChatUtil.createEmbed(event.getGuild(), Color.RED, "Suggestion by: " + event.getAuthor().getName(), event.getMessage().getContentRaw())).queue((message) -> {
                    message.addReaction("✅").queue();
                    message.addReaction("❌").queue();
                });
            }
        }
    }

}
