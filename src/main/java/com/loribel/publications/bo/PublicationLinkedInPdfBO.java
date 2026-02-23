package com.loribel.publications.bo;

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
}
