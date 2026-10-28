import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TransportCardTest {
    @Test void defaultConstructorLeavesFieldsEmpty() {
        TransportCard c = new TransportCard();
        assertNull(c.getOwner()); assertEquals(0, c.getBalance());
        assertEquals(0, c.getTrips()); assertEquals(0, c.getSpent());
    }
    @Test void constructorRejectsNegativeBalance() {
        TransportCard c = new TransportCard("Ivanov", -50);
        assertEquals("Ivanov", c.getOwner()); assertEquals(0, c.getBalance());
    }
    @Test void topUpIgnoresNonPositiveAmount() {
        TransportCard c = new TransportCard("Ivanov", 300);
        c.topUp(0); c.topUp(-100); assertEquals(300, c.getBalance());
    }
    @Test void topUpAddsPositiveAmount() {
        TransportCard c = new TransportCard("Ivanov", 300);
        c.topUp(200); assertEquals(500, c.getBalance());
    }
    @Test void isPremiumIsTrueExactlyAt500() {
        assertTrue(new TransportCard("A", 500).isPremium());
        assertFalse(new TransportCard("B", 499).isPremium());
    }
    @Test void payReducesBalanceAndCountsTrip() {
        TransportCard c = new TransportCard("A", 500);
        assertTrue(c.pay(35)); assertEquals(465, c.getBalance());
        assertEquals(1, c.getTrips()); assertEquals(35, c.getSpent());
    }
    @Test void payRejectsAmountGreaterThanBalance() {
        TransportCard c = new TransportCard("A", 100);
        assertFalse(c.pay(1000)); assertEquals(100, c.getBalance());
        assertEquals(0, c.getTrips());
    }
    @Test void averageFareUsesIntegerDivision() {
        TransportCard c = new TransportCard("A", 500);
        c.pay(35); c.pay(35); c.pay(40); assertEquals(36, c.getAverageFare());
    }
    @Test void averageFareIsZeroWithoutTrips() {
        assertEquals(0, new TransportCard().getAverageFare());
    }
    @Test void copyConstructorMakesIndependentCopy() {
        TransportCard original = new TransportCard("A", 500);
        original.pay(50);
        TransportCard copy = new TransportCard(original);
        assertEquals(original.getOwner(), copy.getOwner());
        assertEquals(original.getBalance(), copy.getBalance());
        assertEquals(original.getTrips(), copy.getTrips());
        assertEquals(original.getSpent(), copy.getSpent());
        copy.topUp(1000); assertEquals(450, original.getBalance());
    }
    @Test void transferMovesMoneyBetweenCards() {
        TransportCard a = new TransportCard("A", 300), b = new TransportCard("B", 100);
        assertTrue(a.transferTo(b, 50)); assertEquals(250, a.getBalance()); assertEquals(150, b.getBalance());
    }
    @Test void transferRejectsInvalidAmountWithoutChanges() {
        TransportCard a = new TransportCard("A", 300), b = new TransportCard("B", 100);
        assertFalse(a.transferTo(b, 301)); assertFalse(a.transferTo(b, 0));
        assertEquals(300, a.getBalance()); assertEquals(100, b.getBalance());
    }
    @Test void transferToSelfDoesNotChangeBalance() {
        TransportCard a = new TransportCard("A", 300);
        assertTrue(a.transferTo(a, 50)); assertEquals(300, a.getBalance());
    }
}
