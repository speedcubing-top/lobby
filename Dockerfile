# Build context: repo root
#   docker build -f lobby/Dockerfile -t ghcr.io/speedcubing-top/lobby .
# Prerequisites: run k8s/start.sh first to build and stage all JARs.
FROM eclipse-temurin:21-jre
WORKDIR /server

# Accept Minecraft EULA
RUN echo "eula=true" > eula.txt

# Bake in the Minecraft server configs.
# - online-mode=false  : Velocity handles auth (BungeeCord legacy forwarding)
# - server-name=lobby  : Bukkit.getServerName() → "lobby"; must match mc_servers.name in DB
# - bungeecord: true   : spigot.yml; required for IP forwarding from Velocity
COPY k8s/server/lobby/server.properties server.properties
RUN printf '\nserver-name=lobby\n' >> server.properties
COPY k8s/server/lobby/spigot.yml spigot.yml
COPY k8s/server/lobby/bukkit.yml bukkit.yml

# Language files (read from /storage/lang/ by the language system at boot)
COPY server-language/0_deutsch.json           /storage/lang/0_deutsch.json
COPY server-language/1_english.json           /storage/lang/1_english.json
COPY server-language/2_mandarin.json          /storage/lang/2_mandarin.json
COPY server-language/3_chinese_simplified.json /storage/lang/3_chinese_simplified.json

# CubingPaper server + world + plugin JARs (staged under k8s/server/lobby/)
COPY k8s/server/lobby/cubingpaper.jar                              cubingpaper.jar
COPY k8s/server/lobby/world world
COPY k8s/server/lobby/world_nether world_nether
COPY k8s/server/lobby/world_the_end world_the_end
COPY k8s/server/lobby/plugins/speedcubingLib-1.0-SNAPSHOT.jar    plugins/speedcubingLib.jar
COPY k8s/server/lobby/plugins/speedcubingServer-1.0-SNAPSHOT.jar plugins/speedcubingServer.jar
COPY k8s/server/lobby/plugins/Lobby-1.0-SNAPSHOT.jar             plugins/Lobby.jar
COPY k8s/server/lobby/plugins/LuckPerms-Bukkit.jar               plugins/LuckPerms-Bukkit.jar
COPY k8s/server/lobby/plugins/LuckPerms/config.yml               plugins/LuckPerms/config.yml
COPY k8s/server/lobby/plugins/spark-bukkit.jar                   plugins/spark-bukkit.jar
COPY k8s/server/lobby/plugins/spark/config.json                  plugins/spark/config.json
COPY k8s/scripts/run-lobby.sh run-lobby.sh
RUN chmod +x run-lobby.sh

# /storage/server.json → mounted at runtime via ConfigMap (k8s) or volume (compose)

EXPOSE 25565
CMD ["./run-lobby.sh"]
