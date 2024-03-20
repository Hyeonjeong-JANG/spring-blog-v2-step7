package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog.user.User;

import java.util.List;

@RequiredArgsConstructor // final이 붙은 친구들의 생성자를 만들어줘
@Controller // new BoardController(IoC에서 BoardRepository를 찾아서 주입) -> IoC 컨테이너 등록
public class BoardController {
    private final BoardService boardService;
    private final HttpSession session;

    // SSR은 DTO를 굳이 만들필요가 없다. 필요한 데이터만 랜더링해서 클라이언트에게 전달할것이니까!!
    // TODO: 글 목록 조회 API 필요 -> @GetMapping("/")
    // TODO: 글 조회 API 필요 -> @GetMapping("/api/board/{id}")
    // TODO: 글 상세보기 API 필요 -> @GetMapping("/api/board/{id}/detail")
    @PostMapping("/api/boards")
    public String save(BoardRequest.SaveDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.글쓰기(reqDTO, sessionUser);
        return "redirect:/";
    }

    @PutMapping("/api/boards/{id}")
    public String update(@PathVariable Integer id, BoardRequest.UpdateDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.글수정(id, sessionUser.getId(), reqDTO);
        return "redirect:/board/" + id;
    }

    @DeleteMapping("/api/boards/{id}")
    public String delete(@PathVariable Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.글삭제(id, sessionUser.getId());
        return "redirect:/";
    }
}
