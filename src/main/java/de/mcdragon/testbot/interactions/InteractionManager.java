package de.mcdragon.testbot.interactions;

import de.mcdragon.testbot.interactions.classes.Command;
import de.mcdragon.testbot.main;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InteractionManager {

    public static final ConcurrentHashMap<String, Command> commands = new ConcurrentHashMap<>();

    public static Command getCommand(String name) {
        if (!commands.containsKey(name))
            commands.put(name, new Command());
        return commands.get(name);
    }

    public static void push() {
        CommandListUpdateAction update = main.jda().updateCommands();

        for (Map.Entry<String,  Command> entry : commands.entrySet())
            update.addCommands(entry.getValue().getData()).queue();

        update.queue();
    }

}
