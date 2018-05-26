package sample.FormBuyOrEdit;

public class Good {
    private String name ;
    private int count ;
    private int price ;

    public Good(){}  ;

    public Good(String name, int count, int price) {
        this.name = name;
        this.count = count ;
        this.price = price ;
    }

    public int getCount() {
        return count;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

