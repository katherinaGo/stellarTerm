package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendFields {

    //    private String recipient;
    private String amount;
    private String asset;
    private String memoText;
}