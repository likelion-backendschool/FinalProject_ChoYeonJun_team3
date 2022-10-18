package com.ll.ebook.keyword;

import com.ll.ebook.keyword.model.KeywordEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KeywordService {
    private final KeywordRepository keywordRepository;

    public KeywordEntity saveKeyword(String keywordContent) {
        Optional<KeywordEntity> optKeyword = keywordRepository.findByContent(keywordContent);

        if ( optKeyword.isPresent() ) {
            return optKeyword.get();
        }

        KeywordEntity keyword = KeywordEntity
                .builder()
                .content(keywordContent)
                .build();

        keywordRepository.save(keyword);

        return keyword;
    }
}