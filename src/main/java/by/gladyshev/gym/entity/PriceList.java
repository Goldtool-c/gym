package by.gladyshev.gym.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PriceList {
    private int[] month = new int[4];
    private int[] n3month = new int[4];
    private int[] year = new int[4];
}
