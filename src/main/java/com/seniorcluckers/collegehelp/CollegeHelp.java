package com.seniorcluckers.collegehelp;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.seniorcluckers.collegehelp.commands.general.AboutCommand;
import com.seniorcluckers.collegehelp.commands.general.tools.id.model.IDCardCommand;
import com.seniorcluckers.collegehelp.commands.general.tools.WhiteBoardCommand;
import com.seniorcluckers.collegehelp.commands.management.course.CourseCommand;
import com.seniorcluckers.collegehelp.commands.moderation.EchoCommand;
import com.seniorcluckers.collegehelp.commands.moderation.EchoEmbedCommand;
import com.seniorcluckers.collegehelp.listeners.FileDetection;
import com.seniorcluckers.collegehelp.listeners.RantChannelListener;
import com.seniorcluckers.collegehelp.listeners.SuggestionChannelListener;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.apache.commons.io.FileUtils;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.util.Map;

public class CollegeHelp {

    private Map<String, String> tempConfig;
    private final EventWaiter eventWaiter;

    public CollegeHelp() {
        saveConfig();
        loadConfig();

        eventWaiter = new EventWaiter();

        CommandClient commandClient = new CommandClientBuilder()
                .setPrefix("$")
                .setActivity(Activity.listening("C418"))
                .setOwnerId("175074807661068289")
                .setShutdownAutomatically(false)
                .addCommands(
                        new AboutCommand(),
                        new WhiteBoardCommand(),
                        new IDCardCommand(),
                        new CourseCommand(),
                        new EchoEmbedCommand(),
                        new EchoCommand())
                .build();

        try {
            JDABuilder.createDefault(tempConfig.get("bot-token"))
                    .addEventListeners(eventWaiter, commandClient,
                            new RantChannelListener(this),
                            new FileDetection(),
                            new SuggestionChannelListener())
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .build();
        } catch (LoginException e) {
            System.out.println("Error. Invalid token.");
            System.exit(-404);
        }
    }

    public String getString(String key) {
        return tempConfig.get(key);
    }

    private void saveConfig() {
        try {

            InputStream stream = CollegeHelp.class.getResourceAsStream("/config.json");

            File dataDirectory = new File("./data/");
            File config = new File(dataDirectory.getAbsolutePath() + File.separator + "config.json");

            if (!dataDirectory.isDirectory()) {
                dataDirectory.mkdirs();
            }
            if (!config.exists()) {
                FileUtils.copyInputStreamToFile(stream, config);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadConfig() {
        Gson gson = new Gson();
        try {
            BufferedReader br = new BufferedReader(new FileReader("./data/config.json"));
            tempConfig = gson.fromJson(br, Map.class);
            br.close();

            for (Map.Entry<?, ?> entry : tempConfig.entrySet()) {
                System.out.println(entry.getKey() + "=" + entry.getValue());
            }

        } catch (JsonSyntaxException | FileNotFoundException ex) {
            if (ex instanceof JsonSyntaxException) {
                System.out.println("Malformed Json Exception. Please verify your config.json syntax.");
            } else {
                System.out.println("The config.json could not be found.");
            }
            System.exit(-1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public EventWaiter getEventWaiter() {
        return eventWaiter;
    }

    public static void main(String[] args) {
        new CollegeHelp();
    }

}
