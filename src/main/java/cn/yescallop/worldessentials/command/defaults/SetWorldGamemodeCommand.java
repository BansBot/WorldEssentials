package cn.yescallop.worldessentials.command.defaults;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.lang.TranslationContainer;
import cn.nukkit.level.Level;
import cn.nukkit.utils.TextFormat;
import cn.yescallop.worldessentials.WorldEssentials;
import cn.yescallop.worldessentials.command.CommandBase;

public class SetWorldGamemodeCommand extends CommandBase {

    public SetWorldGamemodeCommand(WorldEssentials plugin) {
        super("setworldgamemode", plugin);
    }

    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(new TranslationContainer("commands.generic.usage", this.usageMessage));
            return false;
        }
        Level level;
        if (args.length > 1) {
            level = plugin.getServer().getLevelByName(args[1]);
            if (level == null) {
                sender.sendMessage(TextFormat.RED + lang.translateString("commands.generic.level.notFound", args[1]));
                return true;
            }
        } else {
            if (!(sender instanceof Player)) {
                sender.sendMessage(TextFormat.RED + lang.translateString("commands.generic.onlyInGame"));
                return true;
            }
            level = ((Player) sender).getLevel();
        }
        int gamemode = Server.getGamemodeFromString(args[0]);
        if (gamemode == -1) {
            sender.sendMessage(TextFormat.RED + lang.translateString("commands.setworldgamemode.unknownGamemode", args[0]));
            return true;
        }
        plugin.setLevelGamemode(level, gamemode);
        for (Player levelPlayer : level.getPlayers().values()) {
            levelPlayer.sendMessage(lang.translateString("commands.setworldgamemode.success.others", Server.getGamemodeString(gamemode)));
        }
        sender.sendMessage(lang.translateString("commands.setworldgamemode.success", new String[]{level.getName(), Server.getGamemodeString(gamemode)}));
        return true;
    }
}