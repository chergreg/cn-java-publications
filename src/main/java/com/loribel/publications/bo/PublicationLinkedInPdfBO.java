package com.loribel.publications.bo;

import com.loribel.publications.interfaces.SocialNetwork;

public class PublicationLinkedInPdfBO extends PublicationLinkedInBO {

    private String pdfUri;

    public PublicationLinkedInPdfBO() {
    }

    public String getPdfUri() {
        return pdfUri;
    }

    public void setPdfUri(String pdfUri) {
        this.pdfUri = pdfUri;
    }
    
	@Override
	public String getTypeInfo() {
		return "LinkedInPdf";
	}

}
