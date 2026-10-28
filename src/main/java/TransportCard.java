public class TransportCard {
    private String owner;
    private int balance;
    private int trips;
    private int spent;

    public TransportCard() {
    }

    public TransportCard(String owner, int balance) {
        this.owner = owner;
        if (balance > 0) this.balance = balance;
    }

    public TransportCard(TransportCard other) {
        if (other == null) throw new IllegalArgumentException("Карта не должна быть null");
        this.owner = other.owner;
        this.balance = other.balance;
        this.trips = other.trips;
        this.spent = other.spent;
    }

    public String getOwner() { return owner; }
    public int getBalance() { return balance; }
    public int getTrips() { return trips; }
    public int getSpent() { return spent; }

    public void topUp(int amount) {
        if (amount > 0) balance += amount;
    }

    public boolean pay(int fare) {
        if (fare <= 0 || fare > balance) return false;
        balance -= fare;
        spent += fare;
        trips++;
        return true;
    }

    public boolean isPremium() { return balance >= 500; }

    public int getAverageFare() {
        return trips == 0 ? 0 : spent / trips;
    }

    public boolean transferTo(TransportCard other, int amount) {
        if (other == null || amount <= 0 || amount > balance) return false;
        if (other == this) return true;
        balance -= amount;
        other.balance += amount;
        return true;
    }
}
