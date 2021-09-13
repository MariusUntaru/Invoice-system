package com.invoice.system.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO extends BaseDTO {

    private String title;

    private List<InvoiceDTO> noticeList;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<InvoiceDTO> getNoticeList() {
		return noticeList;
	}

	public void setNoticeList(List<InvoiceDTO> noticeList) {
		this.noticeList = noticeList;
	}
}
