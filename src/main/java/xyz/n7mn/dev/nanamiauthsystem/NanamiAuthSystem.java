package xyz.n7mn.dev.nanamiauthsystem;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.util.HashMap;

public final class NanamiAuthSystem extends JavaPlugin {
    private HashMap<String, TokenData> tokenList = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        try {
            JDABuilder.createLight(getConfig().getString("DiscordToken"), GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_EMOJIS)
                    .addEventListeners(new DiscordListener(this, tokenList))
                    .enableCache(CacheFlag.EMOTE)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .setActivity(Activity.competing("HelpCommand : 7m.help"))
                    .build();
        } catch (LoginException e) {
            e.printStackTrace();
            this.onDisable();
        }

        getServer().getPluginManager().registerEvents(new MinecraftListener(this, tokenList), this);
        getLogger().info(this.getDescription().getName() + " "+this.getDescription().getVersion()+" 起動完了");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}