package common.message;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ConfirmationMessage implements Serializable {

    private int confirmedFragmentId;

}
