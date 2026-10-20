package raisetech.student.management.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {

  //受講生IDは自動採番
  private String studentId;

  @NotBlank(message = "Your name is required.")
  @Size(max = 50, message = "Please enter your name using no more than 50 characters.")
  private String fullName;

  @NotBlank(message = "Furigana is required.")
  @Size(max = 50, message = "Please enter furigana using no more than 50 characters.")
  private String furigana;

  @Size(max = 50, message = "Please enter nickname using no more than 50 characters.")
  private String nickname;

  @NotBlank(message = "Email address is required.")
  @Email(message = "Please enter a valid email address.")
  @Size(max = 100, message = "Please enter your email address using no more than 100 characters.")
  private String emailAddress;

  @NotBlank(message = "City address is required.")
  @Size(max = 100, message = "Please enter your city address using no more than 100 characters.")
  private String cityAddress;

  @NotNull(message = "Age is required.")
  @Min(value = 0, message = "Please enter an age of 0 or older.")
  @Max(value = 120, message = "Please enter an age of 120 or younger.")
  private int age;

  @NotBlank(message = "Gender is required.")
  @Size(max = 10, message = "Please enter your gender in 10 characters or fewer.")
  private String gender;

  @Size(max = 255, message = "Please enter your remark in 255 characters or fewer.")
  private String remark;

  private boolean isDeleted;
}