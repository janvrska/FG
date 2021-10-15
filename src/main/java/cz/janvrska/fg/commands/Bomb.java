package cz.janvrska.fg.commands;

import cz.janvrska.fg.Plugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Bomb implements CommandExecutor {
    private Plugin plugin;

    public Bomb(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            /*Location location = ((Player) sender).getLocation();


            Firework fw = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
            FireworkMeta fwm = fw.getFireworkMeta();

            fwm.setPower(4);
            fwm.addEffect(FireworkEffect.builder()
                    .withColor(Color.GRAY)
                    .flicker(true)
                    .trail(true)
                    .with(FireworkEffect.Type.BALL_LARGE)
                    .withFade(Color.WHITE,Color.BLACK,Color.ORANGE,Color.RED).build());
            fw.setCustomName("atomovka");
            fw.setFireworkMeta(fwm);
            fw.setGlowing(true);
            fw.setVelocity(new Vector(0,-2,0));
            fw.setGravity(true);*/
        }
        return true;
    }
}
