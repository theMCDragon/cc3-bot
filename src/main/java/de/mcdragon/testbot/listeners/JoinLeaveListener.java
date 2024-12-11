package de.mcdragon.testbot.listeners;

import de.mcdragon.testbot.main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteCreateEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class JoinLeaveListener extends ListenerAdapter {

    private final ConcurrentHashMap<Invite, Integer> usages = new ConcurrentHashMap<>();

    public JoinLeaveListener() {
        new Thread(() -> {
            try {
                main.jda().awaitReady();
                for (Guild guild : main.jda().getGuilds())
                    for (Invite invite : guild.retrieveInvites().complete())
                        usages.put(invite, invite.getUses());
                System.out.println(usages);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Thread.currentThread().interrupt();
        }).start();

    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Member member = event.getMember();
        Guild guild = event.getGuild();
        TextChannel textChannel = Objects.requireNonNull(guild.getDefaultChannel()).asTextChannel();
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Willkommen!");
        builder.setDescription(member.getAsMention() + "ist nun ein Teil von CastCraft. Jetzt sind wir **"+guild.getMembers().size()+"** Mitglieder!");
        builder.setTimestamp(OffsetDateTime.now());builder.setTimestamp(OffsetDateTime.now());
        builder.setColor(0xb2de27);
        if (member.getUser().getAvatarUrl() != null)
            builder.setThumbnail(member.getUser().getAvatarUrl());
        else
            builder.setThumbnail(member.getUser().getDefaultAvatarUrl());

        Invite used = null;
        for (Invite i : guild.retrieveInvites().complete())
            if (!usages.containsKey(i)) {
                used = i;
                usages.put(i, i.getUses());
                break;
            }   else if (usages.get(i) < i.getUses()) {
                used = i;
                usages.put(i, i.getUses());
                break;
            }
        if (used != null)
            builder.setFooter("Eingeladen von " + used.getInviter().getName(), used.getInviter().getAvatarUrl());

        textChannel.sendMessageEmbeds(builder.build()).queue();
    }

    @Override
    public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
        User user = event.getUser();
        Guild guild = event.getGuild();
        TextChannel textChannel = Objects.requireNonNull(guild.getDefaultChannel()).asTextChannel();
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Tschüss!");
        builder.setDescription(user.getName() + " ist leider kein Teil von CastCraft mehr. Jetzt sind wir nur noch **"+guild.getMembers().size()+"** Mitglieder!");
        builder.setTimestamp(OffsetDateTime.now());builder.setTimestamp(OffsetDateTime.now());
        builder.setColor(0xf22613);
        if (user.getAvatarUrl() != null)
            builder.setThumbnail(user.getAvatarUrl());
        else
            builder.setThumbnail(user.getDefaultAvatarUrl());
        textChannel.sendMessageEmbeds(builder.build()).queue();
    }

    @Override
    public void onGuildInviteCreate(GuildInviteCreateEvent event) {
        usages.put(event.getInvite(), event.getInvite().getUses());
    }

}
