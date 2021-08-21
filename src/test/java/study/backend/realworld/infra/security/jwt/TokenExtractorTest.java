package study.backend.realworld.infra.security.jwt;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TokenExtractorTest {

    @Test
    void when_null_extract_return_empty() {
        TokenExtractor extractor = new TokenExtractor(null);
        Optional<String> result = extractor.extract();

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void when_empty_string_extract_return_empty() {
        TokenExtractor extractor = new TokenExtractor("");
        Optional<String> result = extractor.extract();

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void when_correct_header_extract_return_string() {
        TokenExtractor extractor = new TokenExtractor("Token header");
        Optional<String> result = extractor.extract();

        assertThat(result.isEmpty()).isFalse();
        assertThat(result.get()).isEqualTo("header");
    }
}