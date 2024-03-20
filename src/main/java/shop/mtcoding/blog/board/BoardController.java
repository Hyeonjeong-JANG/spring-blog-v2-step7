package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog._core.utils.ApiUtil;
import shop.mtcoding.blog.user.User;

import java.util.List;

@RequiredArgsConstructor // final이 붙은 친구들의 생성자를 만들어줘
@RestController
public class BoardController {
    private final BoardService boardService;
    private final HttpSession session;

    // SSR은 DTO를 굳이 만들필요가 없다. 필요한 데이터만 랜더링해서 클라이언트에게 전달할것이니까!!
    @GetMapping("/")
    public ResponseEntity<?> main() {
        List<Board> boardList = boardService.글목록조회();
        return ResponseEntity.ok(new ApiUtil<>(boardList));
    }

    // TODO: 글 조회 API 필요 ->
    @GetMapping("/api/board/{id}")
    public ResponseEntity<?> findOne(@PathVariable Integer id) {
        Board board = boardService.글조회(id);
        return ResponseEntity.ok(new ApiUtil<>(board));
    }

    @GetMapping("/api/board/{id}")
    public ResponseEntity<?> detail(@PathVariable Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Board board = boardService.글상세보기(id, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(board));
    }

    // TODO: 글 상세보기 API 필요 -> @GetMapping("/api/board/{id}/detail")
    @PostMapping("/api/boards")
    public ResponseEntity<?> save(@RequestBody BoardRequest.SaveDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Board board = boardService.글쓰기(reqDTO, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(board)); // 제이슨 만들 때 레이지로딩 시 문제가 생길 수 있기 때문에 보드 객체를 리턴하는 것은 위험해.(연관된 객체(댓글)가 있으니까) 무한순환참조에 걸릴 수 있다. 나중에 꼭 디티오를 만들어서 보내야 한다.
    }

    @PutMapping("/api/boards/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody BoardRequest.UpdateDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Board board = boardService.글수정(id, sessionUser.getId(), reqDTO);
        return ResponseEntity.ok(new ApiUtil<>(board));
    }

    @DeleteMapping("/api/boards/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.글삭제(id, sessionUser.getId());
        return ResponseEntity.ok(new ApiUtil<>(null));
    }
}