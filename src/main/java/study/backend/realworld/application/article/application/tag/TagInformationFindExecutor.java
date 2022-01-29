package study.backend.realworld.application.article.application.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.backend.realworld.application.article.domain.Tag;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class TagInformationFindExecutor {

    private final TagInformationFindProcessor tagInformationFindProcessor;

    public Set<Tag> findAllTags() {
        return tagInformationFindProcessor.findAllTags();
    }
}
