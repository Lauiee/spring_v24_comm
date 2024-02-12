package comm.v24comm.web;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginDto {

    @NotEmpty
    private String loginId; // 아이디
    @NotEmpty
    private String password; // 비밀번호

    public LoginDto() {
    }

    public LoginDto(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
