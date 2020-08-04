package com.quanpan302.cars.payload;

import com.quanpan302.cars.util.AppConstants;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 *
 */

public class StoreRequest {
    @NotBlank
    @Size(max = AppConstants.DEFAULT_NAME_SIZE)
    private String name;

    @NotNull
    @Size(min = AppConstants.DB_SELECT_LIST_BND_MIN, max = AppConstants.DB_SELECT_LIST_BND_MAX)
    @Valid
    private List<CarRequest> cars;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
