package cz.janvrska.fg;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DiscordMessager {
    HttpClient httpClient;

    public DiscordMessager() {
        httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
    }

    public void sendMessageToChannelId(String channelId, String message) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://discord.com/api/v8/channels/" + channelId + "/messages"))
                .POST(HttpRequest.BodyPublishers.ofString(message))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bot xxxxxx")
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {

                });
    }
}
