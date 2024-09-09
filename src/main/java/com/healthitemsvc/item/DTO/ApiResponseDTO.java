package com.healthitemsvc.item.DTO;

import com.healthitemsvc.item.Util.Meta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponseDTO {
    private Meta meta;
    private Object data;
}
