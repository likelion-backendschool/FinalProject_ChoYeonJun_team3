package com.ll.ebook.hashtag;

import com.ll.ebook.hashtag.model.HashTagEntity;
import com.ll.ebook.keyword.KeywordService;
import com.ll.ebook.keyword.model.KeywordEntity;
import com.ll.ebook.post.model.entity.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HashTagService {
    private final KeywordService keywordService;
    private final HashTagRepository hashTagRepository;

    public void applyHashTags(PostEntity postEntity, String hashTagContents) {
        List<HashTagEntity> oldHashTags = getHashTags(postEntity);

        List<String> keywordContents = Arrays.stream(hashTagContents.split("#"))
                .map(String::trim)
                .filter(s -> s.length() > 0)
                .collect(Collectors.toList());

        List<HashTagEntity> needToDelete = new ArrayList<>();

        for (HashTagEntity oldHashTag : oldHashTags) {
            boolean isContains = keywordContents.stream().anyMatch(s -> s.equals(oldHashTag.getKeywordEntity().getContent()));

            if (!isContains) {
                needToDelete.add(oldHashTag);
            }
        }
        /**
         * 이미 존재하는 해시태그 중복 피하기 위해 삭제
         */
        needToDelete.forEach(hashTag -> {
            hashTagRepository.delete(hashTag);
        });

        keywordContents.forEach(keywordContent -> {
            saveHashTag(postEntity, keywordContent);
        });
    }

    private HashTagEntity saveHashTag(PostEntity postEntity, String keywordContent) {
        KeywordEntity keyword = keywordService.saveKeyword(keywordContent);

        Optional<HashTagEntity> opHashTag = hashTagRepository.findByPostEntityAndKeywordEntityId(postEntity, keyword.getId());

        if (opHashTag.isPresent()) {
            return opHashTag.get();
        }

        HashTagEntity hashTag = HashTagEntity.builder()
                .postEntity(postEntity)
                .keywordEntity(keyword)
                .build();

        hashTagRepository.save(hashTag);

        return hashTag;
    }

    public List<HashTagEntity> getHashTags(PostEntity postEntity) {
        return hashTagRepository.findAllByPostEntity(postEntity);
    }
}
