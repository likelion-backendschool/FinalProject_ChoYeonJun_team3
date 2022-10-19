package com.ll.ebook.post;

import com.ll.ebook.post.model.entity.PostEntity;
import com.ll.ebook.member.model.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    Optional<PostEntity> findByAuthor(MemberEntity author);
}
