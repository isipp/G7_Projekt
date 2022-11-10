package client.main;

import MessagesBase.MessagesFromClient.ERequestState;
import MessagesBase.MessagesFromClient.PlayerRegistration;
import MessagesBase.ResponseEnvelope;
import MessagesBase.UniquePlayerIdentifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class Registration {
    private String playerId = "";
    private String serverBaseUrl;
    private String gameId;
    private WebClient baseWebClient;

    public Registration(String serverBaseUrl, String gameId,WebClient baseWebClient ){
        this.serverBaseUrl = serverBaseUrl;
        this.gameId = gameId;
        this.baseWebClient = baseWebClient;
    }

    public String begin(){

        PlayerRegistration playerReg = new PlayerRegistration("Nikita", "Glebov",
                "glebovn96");
        Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.POST).uri("/" + gameId + "/players")
                .body(BodyInserters.fromValue(playerReg))
                .retrieve().bodyToMono(ResponseEnvelope.class);

        ResponseEnvelope<UniquePlayerIdentifier> resultReg = webAccess.block();

        if (resultReg.getState() == ERequestState.Error) {
            System.err.println("Client error, errormessage: " + resultReg.getExceptionMessage());
        } else {
            UniquePlayerIdentifier uniqueID = resultReg.getData().get();
            playerId = uniqueID.getUniquePlayerID();
        }

        return playerId;
    }

    public String getPlayerId() {
        return playerId;
    }
}
