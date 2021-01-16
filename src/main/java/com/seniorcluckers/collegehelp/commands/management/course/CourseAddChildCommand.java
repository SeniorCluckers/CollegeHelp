package com.seniorcluckers.collegehelp.commands.management.course;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.seniorcluckers.collegehelp.utils.ChatUtil;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class CourseAddChildCommand extends Command {

    public CourseAddChildCommand() {
        this.name = "add";
        this.help = "create a course";
        this.requiredRole = "server manager";
        this.arguments = "<category-id> <role-name> <course-name>";
    }

    @Override
    protected void execute(CommandEvent event) {
        String[] args = event.getArgs().split("\\s+", 3);

        if (args.length >= 3) {
            net.dv8tion.jda.api.entities.Category category = event.getGuild().getCategoryById(args[0]);
            String role = args[1];
            String course = args[2];

            // Check if there is space for 4 channels
            if (category.getChannels().size() < 50) {
                event.getGuild().createRole().setName(role).setPermissions(Permission.EMPTY_PERMISSIONS).queue((roleCreated -> {
                    category.createTextChannel(course + "-announcements").queue((channel -> {
                        channel.upsertPermissionOverride(event.getGuild().getRolesByName("@everyone", true).get(0)).deny(Permission.VIEW_CHANNEL).queue();
                        channel.upsertPermissionOverride(roleCreated).setAllow(Permission.VIEW_CHANNEL).queue();
                    }));
                    category.createTextChannel(course + "-resources").queue((channel -> {
                        channel.upsertPermissionOverride(event.getGuild().getRolesByName("@everyone", true).get(0)).deny(Permission.VIEW_CHANNEL).queue();
                        channel.upsertPermissionOverride(roleCreated).setAllow(Permission.VIEW_CHANNEL).queue();
                    }));
                    category.createTextChannel(course + "-discussion").queue((channel -> {
                        channel.upsertPermissionOverride(event.getGuild().getRolesByName("@everyone", true).get(0)).deny(Permission.VIEW_CHANNEL).queue();
                        channel.upsertPermissionOverride(roleCreated).setAllow(Permission.VIEW_CHANNEL).queue();
                    }));
                    category.createTextChannel(course + "-help-1").queue((channel -> {
                        channel.upsertPermissionOverride(event.getGuild().getRolesByName("@everyone", true).get(0)).deny(Permission.VIEW_CHANNEL).queue();
                        channel.upsertPermissionOverride(roleCreated).setAllow(Permission.VIEW_CHANNEL).queue();
                    }));
                    category.createTextChannel(course + "-help-2").queue((channel -> {
                        channel.upsertPermissionOverride(event.getGuild().getRolesByName("@everyone", true).get(0)).deny(Permission.VIEW_CHANNEL).queue();
                        channel.upsertPermissionOverride(roleCreated).setAllow(Permission.VIEW_CHANNEL).queue();
                    }));
                    category.createVoiceChannel(course + "-voice-1").queue((channel -> {
                        channel.upsertPermissionOverride(event.getGuild().getRolesByName("@everyone", true).get(0)).deny(Permission.VIEW_CHANNEL).queue();
                        channel.upsertPermissionOverride(roleCreated).setAllow(Permission.VIEW_CHANNEL).queue();
                    }));
                    category.createVoiceChannel(course + "-voice-2").queue((channel -> {
                        channel.upsertPermissionOverride(event.getGuild().getRolesByName("@everyone", true).get(0)).deny(Permission.VIEW_CHANNEL).queue();
                        channel.upsertPermissionOverride(roleCreated).setAllow(Permission.VIEW_CHANNEL).queue();
                    }));
                }));
            }
        } else {
            List<MessageEmbed.Field> fields = new ArrayList<>();
            fields.add(new MessageEmbed.Field("Usage:",event.getClient().getPrefix() + name + " " + arguments, false));
            event.reply(ChatUtil.createEmbed(Color.RED, "You're missing arguments.", "", fields));
        }
    }
}
