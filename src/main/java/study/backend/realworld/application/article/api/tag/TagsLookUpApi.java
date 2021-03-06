package study.backend.realworld.application.article.api.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.backend.realworld.application.article.application.tag.TagInformationFindExecutor;
import study.backend.realworld.application.article.dto.response.TagsResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TagsLookUpApi {

    private final TagInformationFindExecutor tagInformationFindExecutor;

    @GetMapping("/tags")
    public ResponseEntity<TagsResponse> getTagList() {
        return ResponseEntity.ok(
                TagsResponse.from(tagInformationFindExecutor.findAllTags())
        );
    }
}
