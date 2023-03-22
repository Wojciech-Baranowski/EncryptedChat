package common.message;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class FileMessage {

    private Long senderId;
    private int fileFragmentNumber;
    private int numberOfFileFragments;
    private int rawFileSize;
    private byte[] fileFragment;

}
