package io.github.devaprasadh.emailsenderapi.model;

import java.util.List;

import javax.validation.constraints.AssertTrue;

import org.springframework.util.CollectionUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailSendRequest {
	private String from;

	private List<String> to;

	private List<String> cc;

	private List<String> bcc;

	private String subject;

	private String text;

	private String html;

	@AssertTrue
	public boolean isRecipientSpecified() {
		return !CollectionUtils.isEmpty(to) || !CollectionUtils.isEmpty(cc) || !CollectionUtils.isEmpty(bcc);
	}
}
