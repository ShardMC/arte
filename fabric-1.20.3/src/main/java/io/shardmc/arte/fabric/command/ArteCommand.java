package io.shardmc.arte.fabric.command;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import io.shardmc.arte.fabric.ArteMod;

import java.io.IOException;

import static net.minecraft.server.command.CommandManager.literal;

public class ArteCommand implements CommandRegistrationCallback {

    private final ArteMod arte;

    public ArteCommand(ArteMod arte) {
        this.arte = arte;
    }

    @Override
    public void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(literal("arte")
                .then(literal("reload").executes(context -> {
                    this.arte.config().reload();
                    new Thread(() -> {
                        this.arte.getPackManager().reload();
                        context.getSource().sendMessage(Text.of("[Arte] Done!"));
                        for (ServerPlayerEntity player : context.getSource().getServer().getPlayerManager().getPlayerList()) {
                            this.arte.getPackManager().apply(player);
                        }
                    }).start();
                    return 1;
                }))
                .then(literal("clean").executes(context -> {
                    new Thread(() -> {
                        try {
                            this.arte.getPackManager().getZipper().clean();
                            context.getSource().sendMessage(Text.of("[Arte] Done!"));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }).start();
                    return 1;
                }))
        );
    }
}
