package de.mcdragon.testbot;

import de.mcdragon.testbot.commands.TestCommand;
import de.mcdragon.testbot.interactions.InteractionManager;
import de.mcdragon.testbot.listeners.InteractionListener;
import de.mcdragon.testbot.listeners.JoinLeaveListener;
import de.mcdragon.testbot.listeners.VoiceListener;
import de.mcdragon.testbot.secret.DoNotOpen;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.Arrays;

public class main extends InteractionManager {

    private static JDA jda;

    public static void main(String[] args) throws InterruptedException {
        JDABuilder builder = JDABuilder.create(DoNotOpen.TOKEN, Arrays.asList(GatewayIntent.values()));
        builder.setEnableShutdownHook(true);
        builder.setActivity(Activity.playing("CastCraft 3 mit Hacks ;)"));
        builder.setStatus(OnlineStatus.ONLINE);
        jda = builder.build();
        jda().awaitReady();
        jda.addEventListener(
                new JoinLeaveListener(),
                new VoiceListener(),
                new InteractionListener()
        );

        getCommand("test").setExecutor(new TestCommand());
        getCommand("CommandTest").setData(Commands.slash("test", "Test Command for testing SlashCommands"));
        push();
    }

    public static JDA jda() {
        return jda;
    }

}
