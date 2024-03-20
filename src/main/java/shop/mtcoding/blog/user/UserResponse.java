package shop.mtcoding.blog.user;

import lombok.Data;

public class UserResponse {
    @Data
    public static class DTO { // 기본적인 애들의 이름은 디티오로 하고 조인하고 그런 애들은 디티오
        private int id;
        private String username;
        private String email;

        // 응답은 생성자를 만든다. 세터로 해도 되는데 그 방식은 좋지 않아. 여기다가 무조건 유저를 받는디./
        public DTO(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
        }
    }
}
