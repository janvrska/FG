package cz.janvrska.fg.commands;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import cz.janvrska.fg.Plugin;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Wrecker implements CommandExecutor {
    Plugin plugin;

    public Wrecker(Plugin plugin) {
        this.plugin = plugin;
    }

    public static int getRand(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 3 && args[1].length() > 0) {
            Player p = plugin.getServer().getPlayer(args[1]);
            if (p != null && sender instanceof Player) {
                if (sender.hasPermission("fg.wrecker")) {
                    ProtocolManager pm = ProtocolLibrary.getProtocolManager();
                    Location loc = p.getLocation();

                    if (args[0].equals("jump")) {
                        PacketContainer packet = pm.createPacket(PacketType.Play.Server.EXPLOSION);
                        packet.getModifier().writeDefaults();
                        packet.getDoubles()
                                .write(0, loc.getX())
                                .write(1, loc.getY())
                                .write(2, loc.getZ());
                        packet.getFloat().write(0, (float) 3);
                        packet.getFloat().write(2, (float) 10);


                        try {
                            pm.sendServerPacket(p, packet);
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    } else if (args[0].equals("chunk")) {
                        PacketContainer packet = pm.createPacket(PacketType.Play.Server.UNLOAD_CHUNK);
                        packet.getModifier().writeDefaults();
                        packet.getIntegers()
                                .write(0, (int) Math.floor(loc.getX() / 16))
                                .write(1, (int) Math.floor(loc.getZ() / 16));


                        try {
                            pm.sendServerPacket(p, packet);
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    } else if (args[0].equals("fov")) {
                        PacketContainer packet = pm.createPacket(PacketType.Play.Server.ABILITIES);
                        packet.getModifier().writeDefaults();
                        packet.getFloat().write(1, 100F);

                        try {
                            pm.sendServerPacket(p, packet);
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    } else if (args[0].equals("render")) {
                        PacketContainer packet = pm.createPacket(PacketType.Play.Server.VIEW_DISTANCE);
                        packet.getModifier().writeDefaults();
                        packet.getIntegers().write(0, 2);

                        try {
                            pm.sendServerPacket(p, packet);
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    } else if (args[0].equals("explode")) {
                        PacketContainer packet = pm.createPacket(PacketType.Play.Server.EXPLOSION);
                        packet.getModifier().writeDefaults();
                        for (int i = 0; i < 15; i++) {
                            packet.getDoubles()
                                    .write(0, loc.getX())
                                    .write(1, loc.getY())
                                    .write(2, loc.getZ());
                            packet.getFloat().write(0, (float) 3);

                            List<BlockPosition> blocks = new ArrayList<>();

                            for (Block block : getBlocks(loc, 10)) {
                                blocks.add(new BlockPosition(block.getX(), block.getY(), block.getZ()));
                            }

                            packet.getBlockPositionCollectionModifier().write(0, blocks);
                            try {
                                pm.sendServerPacket(p, packet);
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public ArrayList<Block> getBlocks(Location loc, int radius) {
        ArrayList<Block> blocks = new ArrayList<>();
        for (double x = loc.getX() - radius; x <= loc.getX() + radius; x++) {
            for (double y = loc.getY() - radius; y <= loc.getY() + radius; y++) {
                for (double z = loc.getZ() - radius; z <= loc.getZ() + radius; z++) {
                    Location location = new Location(loc.getWorld(), x + getRand(-radius, radius), y + getRand(-radius, radius), z + getRand(-radius, radius));
                    blocks.add(location.getBlock());
                }
            }
        }
        return blocks;
    }
}


