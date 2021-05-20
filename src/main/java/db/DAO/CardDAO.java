package db.DAO;

import com.fasterxml.jackson.databind.node.ArrayNode;
import model.Card;


public interface CardDAO {
    public void create(Card card);
    public ArrayNode getCards();
}
