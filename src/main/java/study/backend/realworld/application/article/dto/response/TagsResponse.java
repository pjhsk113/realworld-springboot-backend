package study.backend.realworld.application.article.dto.response;

import lombok.Getter;
import study.backend.realworld.application.article.domain.Tag;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class TagsResponse {
    private final Set<String> tags;

    private TagsResponse(Set<String> tags) {
        this.tags = tags;
    }

    public static TagsResponse from(Set<Tag> tags) {
        return new TagsResponse(
                tags.stream()
                        .map(Objects::toString)
                        .collect(Collectors.toSet())
        );
    }
}
