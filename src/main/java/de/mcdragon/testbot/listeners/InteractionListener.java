package de.mcdragon.testbot.listeners;

import de.mcdragon.testbot.interactions.InteractionManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class InteractionListener extends ListenerAdapter{

        @Override
        public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
            InteractionManager.getCommand(event.getName()).getExecutor().run(event);
        }

}
