package com.invoice.system.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Invoice")
public class InvoiceDTO extends BaseDTO {

    @ApiModelProperty(value = "Invoice title")
    private String title;

	@ApiModelProperty(value = "Invoice detailed description")
    private String description;

    @ApiModelProperty(value = "A Vendor who created this Invoice")
    private VendorDTO vendor;
    
    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public VendorDTO getVendor() {
		return vendor;
	}

	public void setVendor(VendorDTO vendor) {
		this.vendor = vendor;
	}
}
