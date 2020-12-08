package pip.pip4back.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class DotDto {

    @NotNull(message = "X is required")
    @Min(value = -4, message = "X must be higher or equal to -4")
    @Max(value = 4, message = "X must be lower or equal to 4")
    private double x;

    @NotNull(message = "Y is required")
    @Min(value = -4, message = "Y must be higher or equal to -4")
    @Max(value = 4, message = "Y must be lower or equal to 4")
    private double y;

    @NotNull(message = "R is required")
    @Min(value = 0, message = "R must be higher or equal to 0")
    @Max(value = 3, message = "R must be lower or equal to 3")
    private double r;

}
