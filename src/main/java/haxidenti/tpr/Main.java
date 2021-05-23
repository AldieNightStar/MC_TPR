package haxidenti.tpr;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Instant;
import java.util.HashMap;

public class Main extends JavaPlugin implements Listener {

    private HashMap<String, Long> periods = new HashMap<>(64);

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender sender,
                            Command command,
                            String label,
                            String[] args) {
        if (command.getName().equalsIgnoreCase("tpr")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                long now = Instant.now().toEpochMilli();

                Long period = periods.get(player.getName());
                if (period != null && period > 0 && now < period) {
                    sender.sendMessage(ChatColor.RED + "You can teleport once per each 5 seconds");
                    return true;
                }

                ChatColor yellow = ChatColor.YELLOW;
                ChatColor gold = ChatColor.GOLD;

                Location loc = TprApi.randomTeleport(player);
                if (loc != null) {
                    periods.put(player.getName(), Instant.now().toEpochMilli() + 5000);
                    player.sendMessage(gold + "You teleported to X: " + yellow + loc.getX() + gold + " Y: " + yellow + loc.getY() + gold + " Z: " + yellow + loc.getZ());
                } else {
                    player.sendMessage(gold + "I can't find place without water right now :(");
                }
            }
            return true;
        }
        return false;
    }
}
