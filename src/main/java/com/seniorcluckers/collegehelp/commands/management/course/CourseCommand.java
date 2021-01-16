package com.seniorcluckers.collegehelp.commands.management.course;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.seniorcluckers.collegehelp.utils.ChatUtil;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.Color;
import java.util.ArrayList;

public class CourseCommand extends Command {

    public CourseCommand() {
        this.name = "course";
        this.help = "manage courses to create or delete";
        this.children = new Command[]{new CourseAddChildCommand(), new CourseDeleteChildCommand()};
        this.requiredRole = "server manager";
    }

    @Override
    protected void execute(CommandEvent event) {

        ArrayList<MessageEmbed.Field> fields = new ArrayList<>();

        for (Command childCommand : children) {
            fields.add(new MessageEmbed.Field(event.getClient().getPrefix() + childCommand.getName() + " " + childCommand.getArguments(), childCommand.getHelp(), false));
        }

        event.reply(ChatUtil.createEmbed(Color.RED,"Course Commands", "Use the following commands to create a course.", fields));
    }
}
