package demoqa.models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class AddListOfBookRequestBodyModel {

    public String userId;

    private ArrayList<CollectionOfIsbns> collectionOfIsbns;

    public void setIsbnValue(String value) {
        CollectionOfIsbns isbn = new CollectionOfIsbns();
        isbn.setIsbn(value);
        ArrayList<CollectionOfIsbns> isbnData = new ArrayList<>();
        isbnData.add(isbn);
        this.collectionOfIsbns = isbnData;
    }
}
