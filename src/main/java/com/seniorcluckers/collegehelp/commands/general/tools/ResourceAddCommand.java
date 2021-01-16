package com.seniorcluckers.collegehelp.commands.general.tools;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class ResourceAddCommand extends Command {

    public ResourceAddCommand() {
        this.name = "resource";
        this.help = "react using resource emoji to move items to resource channel";
    }

    @Override
    protected void execute(CommandEvent event) {

    }
}
