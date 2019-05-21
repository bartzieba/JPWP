package model;

public interface Obserwowany {
    void subscribe(Obserwator obserwator);
    void unsubscribe(Obserwator obserwator);
    void inform_All();
}
