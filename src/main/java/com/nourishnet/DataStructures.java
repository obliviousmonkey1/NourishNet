package com.nourishnet;

import java.io.File;

public class DataStructures {

    public static class StringImagePair {
        private String text;
        private File image;

        public StringImagePair(String text, File image) {
            this.text = text;
            this.image = image;
        }

        public String getText() {
            return text;
        }

        public File getImage() {
            return image;
        }
    }

    public static class StringImageIdPair {
        private String text;
        private String id;
        private File image;

        public StringImageIdPair(String text, String id, File image) {
            this.text = text;
            this.id = id;
            this.image = image;
        }

        public String getText() {
            return text;
        }

        public String getId() {
            return id;
        }

        public File getImage() {
            return image;
        }
    }

    public static class StringIntPair {
        private String text;
        private int number;

        public StringIntPair(String text, int number) {
            this.text = text;
            this.number = number;
        }

        public String getText() {
            return text;
        }

        public int getNumber() {
            return number;
        }

    }

    public static class StringBooleanPair {
        private String extension;
        private Boolean hasImage;

        public StringBooleanPair(String extension, Boolean hasImage){
            this.extension = extension;
            this.hasImage = hasImage;
        }

        public String getExtension() {
            return extension;
        }

        public Boolean getHasImage(){
            return hasImage;
        }
    }
}
