package com.ll.ebook.keyword;

import com.ll.ebook.keyword.model.KeywordEntity;
import com.ll.ebook.post.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KeywordService {
    private final KeywordRepository keywordRepository;

    public KeywordEntity saveKeyword(String keywordContent) {
        Optional<KeywordEntity> optKeyword = keywordRepository.findByContent(keywordContent);

        /**
         * 이미 저장된 키워드라면 새로 저장하지 않고 리턴
         */
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

    public KeywordEntity findById(Long id){
        return keywordRepository.findById(id).orElseThrow(() -> new DataNotFoundException("%d id keyword not found".formatted(id)));
    }
}