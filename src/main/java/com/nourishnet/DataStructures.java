package com.nourishnet;

import javax.swing.ImageIcon;

public class DataStructures {

    public static class StringImagePair {
        private String text;
        private ImageIcon image;

        public StringImagePair(String text, ImageIcon image) {
            this.text = text;
            this.image = image;
        }

        public String getText() {
            return text;
        }

        public ImageIcon getImage() {
            return image;
        }
    }

    public static class StringImageIdPair {
        private String text;
        private String id;
        private ImageIcon image;

        public StringImageIdPair(String text, String id, ImageIcon image) {
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

        public ImageIcon getImage() {
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
