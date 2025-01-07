package me.whityfox.alertpl;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Alertpl extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("resalert").setExecutor(this);
        getCommand("alert").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("resalert")){
            if (!sender.isOp() && !(sender instanceof ConsoleCommandSender)){
                sender.sendMessage("You have no rights to do this!");
                return false;
            }

            if (args.length < 1) {
                sender.sendMessage("/alert [time]");
                return true;
            }
            if (Bukkit.getOnlinePlayers().isEmpty()){
                Bukkit.spigot().restart();
                return true;
            } else {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendTitle(
                            ChatColor.RED + "ПЕРЕЗАГРУЗКА",
                            ChatColor.YELLOW + "Сервер перезагружается через " + args[0] + " секунд",
                            10, 120, 20
                    );
                }
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.sendTitle(
                                    ChatColor.RED + "ПЕРЕЗАГРУЗКА",
                                    ChatColor.YELLOW + "Сервер перезагружается через 10 секунд",
                                    10, 120, 20
                            );
                        }
                    }
                }.runTaskLater(this, (Integer.parseInt(args[0]) - 10) * 20);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Bukkit.spigot().restart();
                    }
                }.runTaskLater(this, Integer.parseInt(args[0]) * 20);
                return true;
            }

        }
        if (command.getName().equalsIgnoreCase("alert")){

            String input = String.join(" ", args);

            Pattern pattern = Pattern.compile("\"(.*?)\"");
            Matcher matcher = pattern.matcher(input);

            String title = null;
            String subtitle = null;

            if (matcher.find()) {
                title = matcher.group(1);
                if (matcher.find()) {
                    subtitle = matcher.group(1);
                }
            }
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendTitle(
                        title,
                        subtitle,
                        10, 90, 20
                );
            }
        }
        return false;
    }



    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
