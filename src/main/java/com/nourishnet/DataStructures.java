package com.nourishnet;

import javafx.scene.image.Image;


public class DataStructures {

    public static class StringImagePair {
        private String text;
        private Image image;

        public StringImagePair(String text, Image image) {
            this.text = text;
            this.image = image;
        }

        public String getText() {
            return text;
        }

        public Image getImage() {
            return image;
        }
    }

    public static class StringImageIdPair {
        private String text;
        private String id;
        private Image image;

        public StringImageIdPair(String text, String id, Image image) {
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

        public Image getImage() {
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

    public static class StringPair {
        private String var;
        private String var2;

        public StringPair(String var, String var2) {
            this.var = var;
            this.var2 = var2;
        }

        public String getVar() {
            return var;
        }

        public String getVar2() {
            return var2;
        }

    }
}
