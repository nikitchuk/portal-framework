package com.automation.portal.framework.utilz;

import java.io.File;

public class DocumentFile {

    private static final String basePath = "src/main/resources/images/";
    private static File file;

    public static void main(String[] args) {

        DocumentFile doc = new DocumentFile();

        doc.setDocument(DocumentType.DOCUMENT1, Extension.JPG);

        System.out.println(doc.getDocument());

    }

    public void setDocument(DocumentType type, Extension extension) {
        try {
            file = new File(basePath + extension.get() + "/" + type.get() + "." + extension.get());
        } catch (Exception e) {
            throw new RuntimeException("Unable to create file obj");
        }
    }

    public String getDocument() {
        return file.getAbsolutePath();
    }

    public enum DocumentType {

        DOCUMENT1("image1");

        private String image;

        DocumentType(String image) {
            this.image = image;
        }

        public String get() {
            return image;
        }
    }

    public enum Extension {

        JPG("jpg"),
        GIF("gif"),
        PNG("png"),
        PDF("pdf");

        private String ext;

        Extension(String ext) {
            this.ext = ext;
        }

        public String get() {
            return ext;
        }

        public Extension extensionByName(String name) throws Exception {
            for (Extension ex : values()) {
                if (ex.get().equalsIgnoreCase(name)) {
                    return ex;
                }
            }
            throw new Exception("Wrong format");
        }

    }

}




