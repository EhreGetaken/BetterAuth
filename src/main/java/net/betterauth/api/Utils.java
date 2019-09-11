package net.betterauth.api;

import net.betterauth.BetterAuth;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Utils {

    public static final String PREFIX = BetterAuth.getInstance().getConfig().getString("Settings.Prefix").replaceAll("&", "§");

    public static ArrayList<Player> cachedSqlAuth = new ArrayList<>();

    public static HashMap<Player, String> wordAuth = new HashMap<>();
    public static HashMap<Player, Integer> mathAuth = new HashMap<>();
    public static HashMap<Player, Integer> slotAuth = new HashMap<>();

    public static ArrayList<Player> isPlayerAuth = new ArrayList<>();

    public static HashMap<Player, Integer> tries = new HashMap<>();

    //Messages
    public static String notAuthMessage = BetterAuth.getInstance().getConfig().getString("Messages.NotAuth");
    public static String wordAuthMessage = BetterAuth.getInstance().getConfig().getString("Messages.WordAuth");
    public static String mathAuthMessage = BetterAuth.getInstance().getConfig().getString("Messages.MathAuth");
    public static String slotAuthMessage = BetterAuth.getInstance().getConfig().getString("Messages.SlotAuth");
    public static String notAuthJoinMessage = BetterAuth.getInstance().getConfig().getString("Messages.NotAuthJoin");
    public static String authCompleteMessage = BetterAuth.getInstance().getConfig().getString("Messages.AuthComplete");
    public static String trieRemoveMessage = BetterAuth.getInstance().getConfig().getString("Messages.TrieRemove");
    public static String kickMessage = BetterAuth.getInstance().getConfig().getString("Messages.Kick");

    public static void sendWordAuth(Player player) {

        String alpha = getRandom("string");
        String numb = getRandom("int");
        String word = numb + alpha;



        player.sendMessage(PREFIX + getConvertedMessage(wordAuthMessage, "%w", word));
        wordAuth.put(player, word);

    }

    public static void sendMathAuth(Player player) {

        Integer numb1 = getRandomInt(0, 20);
        Integer numb2 = getRandomInt(0, 20);
        Integer mathResult = numb1 + numb2;

        String msg = getConvertedMessage(mathAuthMessage, "%f", String.valueOf(numb1));
        msg = getConvertedMessage(msg, "%g", String.valueOf(numb2));

        player.sendMessage(PREFIX + msg);
        mathAuth.put(player, mathResult);

    }

    public static void sendSlotAuth(Player player) {
        Integer numb = getRandomInt(1, 9);

        String msg = getConvertedMessage(slotAuthMessage, "%s", String.valueOf(numb));
        player.sendMessage(PREFIX + msg);
        slotAuth.put(player, numb);

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

    public static String getConvertedMessage(String message, String toReplace, String replaceWith) {
        message = message.replace(toReplace, replaceWith);
        message = message.replaceAll("&", "§");
        return message;
    }

    public static String getColoredMessage(String message) {
        message = message.replaceAll("&", "§");
        return message;
    }

}
