package com.seniorcluckers.collegehelp.commands.general;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class AboutCommand extends Command {

    public AboutCommand() {
        this.name = "about";
        this.help = "get information about bot";
    }

    @Override
    protected void execute(CommandEvent event) {
        event.reply("This bot is under construction.");
    }
}
