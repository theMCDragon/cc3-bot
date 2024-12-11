package de.mcdragon.testbot.commands;

import de.mcdragon.testbot.interactions.interfaces.CommandExecutor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class TestCommand implements CommandExecutor {

    @Override
    public void run(SlashCommandInteractionEvent event) {
        event.reply("Test!").queue();
    }

}
