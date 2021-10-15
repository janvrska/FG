package cz.janvrska.fg.commands;

import cz.janvrska.fg.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;

public class AdminChat implements CommandExecutor {

    public List<Player> admins = new ArrayList<>();
    Plugin plugin;
    ScoreboardManager manager;
    Scoreboard emptyScoreBoard;
    Scoreboard board;

    public AdminChat(Plugin plugin) {
        this.plugin = plugin;
        manager = Bukkit.getScoreboardManager();
        emptyScoreBoard = manager.getNewScoreboard();
        board = manager.getNewScoreboard();

        Objective objective = board.registerNewObjective("test", "dummy", "AdminChat");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score score = objective.getScore(ChatColor.GREEN + "Zapnuto");
        score.setScore(1);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (p.hasPermission("fg.a")) {
                boolean turned = false;
                for (int i = 0; i < admins.size(); i++) {
                    if (admins.get(i).getName().equals(p.getName())) {
                        admins.remove(i);
                        turned = true;
                        p.sendMessage("AdminChat vypnut");
                        deleteScoreBoard(p);
                        p.performCommand("sb on");
                        break;
                    }
                }

                if (!turned) {
                    admins.add(p);
                    p.sendMessage("AdminChat zapnut");
                    p.performCommand("sb off");
                    setScoreBoard(p);
                }
            } else {
                p.sendMessage("Na toto nemáš dostatečné oprávnění");
            }
        }
        return true;
    }

    public boolean isPlayerToggled(Player p) {
        for (Player player : admins) {
            if (p.getName().equals(player.getName())) {
                return true;
            }
        }
        return false;
    }

    public void setScoreBoard(Player player) {
        player.setScoreboard(board);
    }

    public void deleteScoreBoard(Player player) {
        player.setScoreboard(emptyScoreBoard);
    }
}
