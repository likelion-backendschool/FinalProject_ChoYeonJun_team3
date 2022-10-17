package com.ll.ebook.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/post")
public class PostController {

    /**
     * 글 리스트
     * - 제목과 글의 해시태그들을 볼 수 있다.
     * - 해시태그를 클릭하면 내가 작성한 글 중 해당 해시태그와 관련된 글들을 볼 수 있다.
     */
    @GetMapping("/list")
    public void getList(){

    }

    /**
     * 글 상세
     * - 글의 제목, 내용, 해시태그를 모두 출력
     * - 글의 내용은 마크다운 해석이 되어야 한다.
     */
    @GetMapping()
    public void getDetail(@RequestParam Long id){

    }

    /**
     * 글 작성/수정
     * - 마크다운 원문과 렌더링 결과(HTML)까지 같이 저장한다.
     */
    @GetMapping("/write")
    public void getWrite(){

    }

    @PostMapping("/write")
    public void write(){

    }

    @GetMapping("/modify")
    public void getModify(){

    }

    @PostMapping("/modify")
    public void modify(){

    }

    /**
     * 글 삭제
     * - 글이 삭제되면 글 리스트로 리다이렉트 한다.
     * - 삭제버튼 눌렀을 때 confirm 창으로 삭제여부를 한 번 더 물어본다.
     */
    @GetMapping
    public void delete(){

    }

}
