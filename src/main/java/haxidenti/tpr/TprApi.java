package haxidenti.tpr;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import java.util.Random;

public class TprApi {
    private static final int RADIUS = 128000;

    public static Location getRandomLocation(World world) {
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            int x = random.nextInt(RADIUS * 2) - RADIUS;
            int z = random.nextInt(RADIUS * 2) - RADIUS;

            Block block = world.getHighestBlockAt(x, z);
            if (block.getType().equals(Material.WATER)) continue;
            return block.getLocation().add(0, 2, 0);
        }

        return null;
    }

    public static Location randomTeleport(Entity entity) {
        Location randomLocation = getRandomLocation(entity.getWorld());
        if (randomLocation != null) {
            entity.teleport(randomLocation);
        }
        return randomLocation;
    }
}
