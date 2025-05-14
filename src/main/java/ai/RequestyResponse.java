package ai;

import java.util.List;

public record RequestyResponse(
        String id,
        String object,
        long created,
        String model,
        Usage usage,
        List<Choice> choices
) {
    public record Usage(
            int prompt_tokens,
            int completion_tokens,
            int total_tokens
    ) {
    }

    public record Choice(
            int index,
            Message message,
            String finish_reason
    ) {
    }

    public record Message(
            String role,
            String content
    ) {
    }
}
