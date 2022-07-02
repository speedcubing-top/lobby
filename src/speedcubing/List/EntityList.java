package speedcubing.List;

import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import org.bukkit.Bukkit;
import speedcubing.Main;
import speedcubing.Member;
import speedcubing.lib.bukkit.BungeePluginMessage;
import speedcubing.lib.bukkit.entity.Hologram;
import speedcubing.lib.bukkit.entity.NPC;
import speedcubing.server.libs.User;

public class EntityList {
    public static Hologram[] Lobbys = new Hologram[12];
    public static NPC[] npcs = new NPC[6];

    public void Load() {
        String VEPEXtextures = "ewogICJ0aW1lc3RhbXAiIDogMTYxNzE4MDQwNzAyNSwKICAicHJvZmlsZUlkIiA6ICIwMGM2Yjk0YTY5YmU0MzY3OTkwOTQxNjFjMjAxOWI3ZiIsCiAgInByb2ZpbGVOYW1lIiA6ICJLQUVWRVJZIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzI1YTUzMTU2Zjk1ODhlZjNkZTQyMGU0ODA5MTYzN2Q3NGYwN2ZmM2MyMzIyNjRkM2Q0MGY3Y2RlOGQ1N2JiODMiCiAgICB9CiAgfQp9";
        String VEPEXsignature = "CY9LJ6UsQiJ4h0x2MlHVyWq08zc8bA5neVud+3Deh0eK9UcNU4eC5rk+5Wghq6Ue8KUUV8Kc+h6Ze4XuujN/OfX6tulvt2YrkLgDWh9dyoPFVhmUiIV7cyYUqHqCbNSvRDjFjMmlnmcgzLMmTYnsaArfxwk/vqnHpigkGMFa7EnfkyoFHHTs5evAPCfr2CXWTdOXJAgsGeb/o54KI159sjOdRjv4lgmdQFq+7SyzBKcGMD0cR5nB4thczXw79U9rfKc9IYFfMKtjTFV1Qxhzhnyugw9yPO5TiZ9qve59H9wf6THFgA7m8QWwVqjrQEIBTE7MSUDUS9jSU/sVPnYKz6F3z+pIQSPSPykh0AHW5T1fP5IerzVgo4ddBuAAQegpv7VX/wybjel02ZYa79bYXMbb2FUfSwcfLj6b6pAGMNTPgrhArr2v2EUZzZwa5Z1BfNAUI7KvQk+6VlYNO/2PfKqaSxxmDlo2C63eXTpVzndcPtuBNmEK9z9Zl1+SYDiEqjKwapPiwT6SsDr6LN4uM+QWJhV7H78KiXDB1ADfg3wf7VTWeIFphnVh/a3LFfRAq/kY88g8PcEHFaoU65nVZEe2K/M9Eis3YzX1mTqDJ8bbsOU2SnwzBv888wlNAdPJtSAuOIkWdoROxEn48YXOzUORpkk58HV1BU9POzdk0iw=";
        String BLENDERTEXTURES = "ewogICJ0aW1lc3RhbXAiIDogMTYyNTc1MTc5MDg0NiwKICAicHJvZmlsZUlkIiA6ICIyNTUyNzc0YTEzNjQ0NDA3YjdlM2QyZjY2ZDkzNjA1YyIsCiAgInByb2ZpbGVOYW1lIiA6ICJzdHVybWxpZWJlbmRlciIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS81MmY4MzJlZDFiMGY5NWNkOWYzZTFmOWI2MGFlMmM4ZGY4Y2FiMDU0YjVkYTRjNjQ5NWFjNDI4ZWYwNDQ5YmQ1IiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=";
        String BLENDERSIGNATURES = "axssK2gBfcvBt6M7wEDf6p5mJiUIqiXU0BeCnW1anmYzd4jicwmmO8E6930APPdECbYNmfl/n+Cy4H2scTBReRaCgH9T5ou3OOJz5XvUuWAA/MlM9vl1sA/PUOeifcKOccoOpY8m1k77tvztcYEKCiIVVLz8viiRavF61lOK5easaeofRL5fXjR1ukodJDo23cEiBQ5OXI5QxgsMjb/oYYqOhRC9muxhTcddRMuc/XV2Gk79cPW5njI6rdKa5I3clkXaayuzBwqUhnzlwefV+x6AZx14yUEmFdyKcyXfBqdbmmQadr5FW+yAKTlvccmOWd0i+hZYjJCV3aRm0Hb2pGqiynhoYcsJAbIUU8t7cXP2tpwfYQeeyEDQ6kNc9ReTyw06qxkK8pOiMzgYiz/Gbc/NF8cZ0VyBpLcmZZNwC/2nFGvYDIUb48W6OldpLaCHsQDOrde+T3C1347G7c3cT7yih0TxVq617w7OV5SsbcW+QSCA/6AoEhQGTEdx+j/eL8TdTUvdxbSESQJ8kldq8RCINxKEPZ1qmU2oVUWUwEFJn9+doN37MluVFdLeUA90ZiTP6uejJQ8Xbh0TounWLQqAff7a5qP5a3IhWb8w5Noz5rGRpsL7Dc/h6HNYT84vJGFbE4wdklyclIM2L63N4Izss4hI6CM4TIu5PzzHR9s=";
        String CAKETEXTURES = "ewogICJ0aW1lc3RhbXAiIDogMTYyNjQwNjg4NDc3MSwKICAicHJvZmlsZUlkIiA6ICIxNTM0ZDZkZjBmNGI0MjhmOGZhMjY1MmMwYTQ4MmI4ZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJfQ2FrZV9wcm9ncmVzU18iLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWRlZjY0ZDZkOGRmNmI3OTZjNTNlZGZlZmEzYjhhMjU0ODMxMDE4ZjFlMTI0ZTQwY2E1YjIxN2EwOWJhYzQ3YiIKICAgIH0KICB9Cn0=";
        String CAKESIGNATURES = "yZxo8GrTQIewN8XkYlXpz1pj4I/reF0HmD6HOwNHmEmpvAFHLybVfrm8/mHdLO4Tr8aNmwtOiw3mmyEVrUW5vsgDA58fofJN76v59j69CZPx8rGzPNtmD4rOidNr80vRNBsFnjubh6waW6CUppMl3qrsvmTqOmHKiVPTJIQIRq9KJhmiF7dtIjuIobtWFEYHNrUp8KwzGFntyOvd//nadsyW8Zfuq3/ODLdWPAXINaL9YxKpXrU9SRQ9S1eECwzNFjzeNLy8zNYV0l/uPlKN3N2sYMaHQ9Okg8EJKA/gZZh6P2HPRsSW431X4pfIeJU/HK1sg8/l01JFUglq8EALcjfE8pxe2C1sp52BzXY6yVATn0pIh4r/qLWJiG3r3nOEz/hOT/xb0qgiV8H00MREt99c08MY/1k9zx1BRzg7bvBs0LNu4OzF9rVAWJi4/oRBXlsg48WHwxw33DTKgCYj/Z43Ehxrc4/EuOp9Hwo7lTLvJLGxtHDCo8Xe2Q880EbYie2bVQFBUBNR0rMi5L0ct/L4MZUNQGtvj3NfL1uu8qWpoXdUdpHndUbjWtt00T89TYsP3zI4YjLkJl22DADgUX2NFqnSbZFmzp0nRylKW4jcop+M6BhyucIquIvI0hudBB/hepJtctthPZade6kUb832FwSdzZT2OTRwgPeT+Ro=";
        String gritextures = "ewogICJ0aW1lc3RhbXAiIDogMTYxMjE2NDM5MzIwMiwKICAicHJvZmlsZUlkIiA6ICI5NGMzZGM3YTdiMmQ0NzQ1YmVlYjQzZDc2ZjRjNDVkYyIsCiAgInByb2ZpbGVOYW1lIiA6ICJUaGVRdWFzb24iLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzRlN2YxNmViYjI4ZDJhY2E0ZjZmMjFlOTNmZGI3NTQ1Njk1MjZjZjYxNzBkODIxN2NjZDlkZTAxZGY0MDA2IiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=";
        String grisignatures = "lcN1iqCxPCoawCAdYtzrYELQ+NASpTFLYCgvsGGYZvEl2PZhcPyfGCQAXh5LJI2y/Uau0o/nNp6SvswquXr0tkhsUBJAjL323QzkOVkaOGxHtpGRk0PNbtJ3ba0lEizuf3su523FJiuM8hmWm75rqR0Q2Eq3P7rEF+7WbQGzo+j55h8UYidY1wcc7n9jjrfXiVlBvwWvSIXAj9P/S5XWH6HJuh2OjFCnlSAAa/RJy/WywobCCSc/IeLFCon1CW5YnjTMGMQagMNaxvVlrd5bUtZE730nLgeLDZma+svnWhsBCKVM4yoz4GeNYnWyYjWIINxORH73b5ROEqtqA1uF0ddaGKjNiG3C+3E0IV2fx+eRPEmKF4yqmYqytr4vcYyEHO7MPKaZjvuMlZrdd3aa2s/tGdqXk0GMfDcdO4G+OxIScKQBKw7Q5CigD4aY2A41QwtE6ip5bThyijAXX1ZDbS+JDfPg0b8NpeQAE7h7wON6ZPLYDYDx7Hado27gqUAieoplakMU5oTdQsihH2EnQ1lCMr9snqSgPoQO7fRfLimpt2d+29eRoWpj4dch+Qo7gS2+cdk21cQCldsqWuA3/foDBfgT84aLAXTsa1yBLrap12qI4cSrpFpBqaUPhM2nThoqgwsH5sXzQfDue7h+NK6fM5KpMeFEC6gzOXXzsrc=";
        String doogle1 = "ewogICJ0aW1lc3RhbXAiIDogMTYxMjU4NDgyNTQ0NywKICAicHJvZmlsZUlkIiA6ICI1NjY3NWIyMjMyZjA0ZWUwODkxNzllOWM5MjA2Y2ZlOCIsCiAgInByb2ZpbGVOYW1lIiA6ICJUaGVJbmRyYSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8zNzdiM2UyNTAzZjk2YmZkNmVlN2IxZmFjMzk4M2JhYjMxOTRlYjc0OGFlZTczYzE1MzA0YWJkN2FlNzBjMGYzIiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=";
        String doogle2 = "GLGEvQbFTIP3Z4ZScEMXyu7Z8/SwnzyMc20+tN/zYXf71q96rTg7NkZp5mrVYSfiQfI7Mjg0uaFF0cbl7GC82kkw3O9tHjeqipQMhvHlo2fvfFlffCZMoVDDlJHvccMOUow/1S/vrjsLJY2fBgBwekemwEAqdtjVyjbv+VAM3/lQpdn44aoELnx2AsMK4tAExtLw72Eg7c7dbwSCNo8KY5BMEdesqXl9wlN8waknTKmxUw/HbweRCbKzBT9aOEd9BpNqUcTj0dAv2D6U52NatLE+tE/4O2/s/aiFkey1NTLeH8HQWM6ksgBSKRsrgbna6w/Z1QltQX+7nc+rYECh1WjEcLgcr83/Kj8seWy3yQkz9mO//OEX1qskqOdSeOMZ29I9CpxjO/5C0HERHya5ZfaJt4hxZcu6gbllB1cSsE696vrppYU7fywtU/47JiNjBiqXSadQEyZbCn/tWHa7u3C+BHK39KzCKAnCxwZLVLn9G/rGjDCbfxCnwjM6JucThYK6pfZRFGJpYkU9YkGW94CWvD3F53mJqRKoEOxQfT4Cm8xZ/RyxsQxzxvmWhGRE8eMwahAHTW710+1ATEVoQgB/0A5iHILN0qo3f9kz0Zz3QPxeHMDf7g8Dkxl/6RCZR87GZn6CEMKHuAqqlt7MjfrIe85upnTcUsdRNfNMLXY=";
        String str1 = "ewogICJ0aW1lc3RhbXAiIDogMTY1NDE2NjIyMzU2NCwKICAicHJvZmlsZUlkIiA6ICI3OTkyMzAzNmI1ODE0NjZhODUzZmMxNWQ5NmVkNDczZCIsCiAgInByb2ZpbGVOYW1lIiA6ICJTdHJhaWdodCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS80Yjk3ZmExZGZhOWMwYzUyYzZiOGM5ZjYzNWM4Nzk5ZTJmZDYyOTFmMWExOGIxMmM1YTQ4NjNlNWY4YzEzYzI5IiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=";
        String str2 = "W6rGFhFLqFLGlSUucvYE7+WmHFcoII9mqQO5aN44XYyXh0m+93McjMfdkBCW7tPKITBw5eciB7UsiQLx9cg91S0ybYe2kRJQlJcX8BWDfPk5HajzIH771TdITd2s6MbrDuai3HwPLUycNZapaQg/QiYGRt+P9bTM3k3JesaoffAZd3KQbcB4We77UE6qxxDVaEVJQXdn70+V/0iGxGhkJUg2gy9pWBzTI3e9mZghwvHLmBsuBL4YXTRG5UYmaKDoPjGoct14Y7SlllN/P/N//AjU0nMmDkYnLPRjGxG+Uv5Ti6o4yAuyk+h8wWwR+i+nEPkRusCOcMC5ZgPmRyUJ5bgxXxO+kWvIdrUv2c5gvGueyMNOfXdJ7zuLshr0eo/J34BPKKrEqd2DBnmeGiAlpMxxAX2gpCuLWEFlpPeLWhAF75BinPPQGx+UFgkQi43Z8Up5vRabwC/ul+ebzW+BlVfUtYNPohXRVUbqHRdSgvhlA2Pz4cP2BSGrenuGoZsbY1Ea8Bg9FxKbU4+eCj6AkzhygflJyYi2LdZxpZrfldapUyMwkjDuMU8MfsXMp/zluS00FqHVn/Xi1ZscUxKuukMzPFP1toc38DDKPCpBfR8FCacbpcaCLloUxcTA7WYFPmyPpFmnUhpNQexrv4G3cPIWEqcTAJb6gThrbB9+axM=";
        for (int i = 0; i < npcs.length; i++) {
            npcs[i] = new NPC(" ", null, true);
            npcs[i].world.add("world");
        }
        for (int i = 0; i < Lobbys.length; i++) {
            Lobbys[i] = new Hologram("", 0, 0, 0, 0, 0).setAutoSpawn(true);
            Lobbys[i].world.add("world");
        }
        //rush
        npcs[0].setAutoSpawn(true).hideNametag().setSkin(VEPEXtextures, VEPEXsignature).setSpawnBodyYaw(180).setLocation(-1.5, 99, 24.5, 128, 0)
                .setClickEvent((player, action) -> {
                    if (player.getItemInHand().getType() == org.bukkit.Material.AIR && action.equals(PacketPlayInUseEntity.EnumEntityUseAction.INTERACT))
                        BungeePluginMessage.switchServer(player, "mlgrush");
                });
        Lobbys[0].setLocation(-1.5, 100.85, 24.5, 0, 0);
        Lobbys[0].setName("§bMLGRush");
        Lobbys[1].setLocation(-1.5, 101.15, 24.5, 0, 0);
        Lobbys[1].setName("§e§l0 Players");
        //reduce
        npcs[1].setAutoSpawn(true).hideNametag().setSkin(BLENDERTEXTURES, BLENDERSIGNATURES).setSpawnBodyYaw(178).setLocation(8.5, 99.0, 22.5, 96, 0.0F)
                .setClickEvent((player, action) -> {
                    if (player.getItemInHand().getType() == org.bukkit.Material.AIR && action.equals(PacketPlayInUseEntity.EnumEntityUseAction.INTERACT))
                        Bukkit.getScheduler().runTask(Main.getPlugin(Main.class), () -> player.openInventory(ItemStackList.GameSelect[Member.getMember(player).lang]));
                });
        Lobbys[2].setLocation(8.5, 100.85, 22.5, 0, 0);
        Lobbys[2].setName("§bReduce");
        Lobbys[3].setLocation(8.5, 101.15, 22.5, 0, 0);
        Lobbys[3].setName("§e§l0 Players");
        //ffa
        npcs[2].setAutoSpawn(true).hideNametag().setSkin(CAKETEXTURES, CAKESIGNATURES).setSpawnBodyYaw(180).setLocation(-7.5, 99.0, 22.5, 160, 0.0F)
                .setClickEvent((player, action) -> {
                    if (player.getItemInHand().getType() == org.bukkit.Material.AIR && action.equals(PacketPlayInUseEntity.EnumEntityUseAction.INTERACT))
                        BungeePluginMessage.switchServer(player, "knockbackffa");
                });
        Lobbys[4].setLocation(-7.5, 100.85, 22.5, 0, 0);
        Lobbys[4].setName("§bKnockBack FFA");
        Lobbys[5].setLocation(-7.5, 101.15, 22.5, 0, 0);
        Lobbys[5].setName("§e§l0 Players");
        //clutch
        npcs[3].setAutoSpawn(true).hideNametag().setSkin(gritextures, grisignatures).setSpawnBodyYaw(180).setLocation(-4.5, 99.0, 23.5, 128, 0.0F)
                .setClickEvent((player, action) -> {
                    if (player.getItemInHand().getType() == org.bukkit.Material.AIR && action.equals(PacketPlayInUseEntity.EnumEntityUseAction.INTERACT))
                        BungeePluginMessage.switchServer(player, "clutch");
                });
        Lobbys[6].setLocation(-4.5, 100.85, 23.5, 0, 0);
        Lobbys[6].setName("§bClutch");
        Lobbys[7].setLocation(-4.5, 101.15, 23.5, 0, 0);
        Lobbys[7].setName("§e§l0 Players");
        //fb
        npcs[4].setAutoSpawn(true).hideNametag().setSkin(doogle1, doogle2).setSpawnBodyYaw(180).setLocation(2.5, 99.0, 24.5, 128, 0.0F)
                .setClickEvent((player, action) -> {
                    if (player.getItemInHand().getType() == org.bukkit.Material.AIR && action.equals(PacketPlayInUseEntity.EnumEntityUseAction.INTERACT))
                        BungeePluginMessage.switchServer(player, "fastbuilder");
                });
        Lobbys[8].setLocation(2.5, 100.85, 24.5, 0, 0);
        Lobbys[8].setName("§bFastBuilder");
        Lobbys[9].setLocation(2.5, 101.15, 24.5, 0, 0);
        Lobbys[9].setName("§e§l0 Players");
        //practice
        npcs[5].setAutoSpawn(true).hideNametag().setSkin(str1, str2).setSpawnBodyYaw(180).setLocation(5.5, 99.0, 23.5, 128, 0.0F)
                .setClickEvent((player, action) -> {
                    if (player.getItemInHand().getType() == org.bukkit.Material.AIR && action.equals(PacketPlayInUseEntity.EnumEntityUseAction.INTERACT))
                        BungeePluginMessage.switchServer(player, "practice");
                });
        Lobbys[10].setLocation(5.5, 100.85, 23.5, 0, 0);
        Lobbys[10].setName("§bPractice");
        Lobbys[11].setLocation(5.5, 101.15, 23.5, 0, 0);
        Lobbys[11].setName("§e§l0 Players");
    }
}
