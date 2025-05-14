package ai;

public interface AiClient {
    void buildRestClient();

    String sendPromptToAi(String prompt);
}
