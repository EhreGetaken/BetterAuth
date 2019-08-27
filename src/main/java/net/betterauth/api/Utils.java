package net.betterauth.api;

import net.betterauth.BetterAuth;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Utils {

    public static final String PREFIX = BetterAuth.getInstance().getConfig().getString("Settings.Prefix");

    public static ArrayList<Player> cachedSqlAuth = new ArrayList<>();

    public static HashMap<Player, String> wordAuth = new HashMap<>();
    public static HashMap<Player, Integer> mathAuth = new HashMap<>();

    public static ArrayList<Player> isPlayerAuth = new ArrayList<>();

    public static HashMap<Player, Integer> tries = new HashMap<>();

    public static void sendWordAuth(Player player) {

        String alpha = getRandom("string");
        String numb = getRandom("int");
        String word = numb + alpha;

        player.sendMessage(PREFIX + "§7Please type in this §6§lCode§8: §6§l" + word);
        wordAuth.put(player, word);

    }

    public static void sendMathAuth(Player player) {

        Integer numb1 = getRandomInt(0, 20);
        Integer numb2 = getRandomInt(0, 20);
        Integer mathResult = numb1 + numb2;

        player.sendMessage(PREFIX + "§7What is §6§l" + numb1 + " §8+ §6§l" + numb2 + "§7?");
        mathAuth.put(player, mathResult);

    }

    public static String getRandom(String type) {
        char[] alpha = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        char[] numb = "1234567890".toCharArray();
        Random r = new Random();

        String random = "";
        if (type.equalsIgnoreCase("string")) {
            for (int i = 0; i < 4; i++) {
                random += alpha[r.nextInt(alpha.length)];
            }
            return random;
        } else {
            for (int i = 0; i < 4; i++) {
                random += numb[r.nextInt(numb.length)];
            }
            return random;
        }
    }

    public static int getRandomInt(int lower, int upper) {
        Random random = new Random();

        return random.nextInt((upper - lower) + 1) + lower;

    }

}
