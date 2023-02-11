package com.wulian233.vmctmodmce;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Mod(modid = VmctModMCE.MODID,
    name = VmctModMCE.NAME,
    version = VmctModMCE.VERSION)
public class VmctModMCE {
	public final static String MODID = "vmctmodmce";
    public final static String NAME = "VM Chinese Translate group Mod Eternal MC";
    public final static String VERSION = "1.0";
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        EntityPlayer player = event.player;
        World world = player.world;
        File configDir = world.getMinecraftServer().getActiveAnvilConverter().getFile("config","config");
        File vmctConfig = new File(configDir, "vmct.txt");

        String localVersion = readFile(vmctConfig);
        String onlineVersion = getOnlineVersion();

        if (!localVersion.equals(onlineVersion)) {
            player.sendMessage(new TextComponentString("\u611f\u8c22\u4f7f\u7528\u0056\u004d\u6c49\u5316\u7ec4\u7684\u6c49\u5316\u8865\u4e01\u3002\u60a8\u7684\u6c49\u5316\u8865\u4e01\u5df2\u7ecf\u8fc7\u65f6\uff0c\u8bf7\u524d\u5f80\u0068\u0074\u0074\u0070\u0073\u003a\u002f\u002f\u0076\u006d\u0063\u0074\u002d\u0063\u006e\u002e\u0074\u006f\u0070\u002f\u006d\u0063\u0065\u4e0b\u8f7d\u65b0\u7248\u672c\u3002"));
        }
    }

    private String readFile(File file) {
        if (!file.exists()) {
            return "";
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return reader.readLine();
        } catch (IOException e) {
            return "";
        }
    }

    private String getOnlineVersion() {
        try {
            URL url = new URL("https://vmct-cn.top/mce/update.txt");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                return reader.readLine();
            }
        } catch (IOException e) {
            return "";
        }
    }
}