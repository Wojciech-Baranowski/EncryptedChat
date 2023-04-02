package common.message;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class FileMessage implements Serializable {

    private Long senderId;
    private int fileFragmentNumber;
    private int numberOfFileFragments;
    private byte[] fileFragment;

}
