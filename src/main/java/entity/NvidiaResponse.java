package entity;

import java.util.List;

public record NvidiaResponse(
        String id,
        String object,
        long created,
        String model,
        List<Choice> choices,
        Usage usage,
        Object promptLogprobs
) {
    public record Choice(
            int index,
            Message message,
            Object logprobs,
            String finish_reason,
            Object stop_reason
    ) {
    }

    public record Message(
            String role,
            String content
    ) {
    }

    public record Usage(
            int prompt_tokens,
            int total_tokens,
            int completion_tokens
    ) {
    }
}

