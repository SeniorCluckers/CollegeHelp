package com.seniorcluckers.collegehelp.commands.management.course;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.seniorcluckers.collegehelp.utils.ChatUtil;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.PermissionOverride;
import net.dv8tion.jda.api.entities.Role;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class CourseDeleteChildCommand extends Command {

    public CourseDeleteChildCommand() {
        this.name = "delete";
        this.help = "delete a course";
        this.requiredRole = "server manager";
        this.arguments = "<category-id> <role/id> <course-name>";
    }

    @Override
    protected void execute(CommandEvent event) {
        String[] args = event.getArgs().split("\\s+", 3);

        if (args.length >= 3) {
            net.dv8tion.jda.api.entities.Category category = event.getGuild().getCategoryById(args[0]);
            Role role = event.getGuild().getRoleById(args[1].replaceAll("[^0-9]", ""));
            String course = args[2];

            if (category != null) {
                if (role != null) {

                    List<GuildChannel> deleteChannels = new ArrayList<>();
                    for (GuildChannel guildChannel : category.getChannels()) {
                        if (guildChannel.getName().contains(course)) {
                            for (PermissionOverride permissionOverride : guildChannel.getPermissionOverrides()) {
                                if (permissionOverride.getRole().getIdLong() == role.getIdLong()) {
                                    deleteChannels.add(guildChannel);
                                    break;
                                }
                            }
                        }
                    }

                    if (deleteChannels.size() > 0) {
                        for (GuildChannel deleteChannel : deleteChannels) {
                            deleteChannel.delete().queue();
                        }
                        role.delete().queue();
                    } else {
                        event.reply("Not deleting any channels.");
                    }

                } else {
                    event.reply("Role is invalid.");
                }
            } else {
                event.reply("Category is invalid.");
            }
        } else {
            List<MessageEmbed.Field> fields = new ArrayList<>();
            fields.add(new MessageEmbed.Field("Usage:",event.getClient().getPrefix() + name + " " + arguments, false));

            event.reply(ChatUtil.createEmbed(Color.RED, "You're missing arguments.", "", fields));
        }
    }

}
