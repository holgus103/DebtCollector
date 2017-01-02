package debtcollector.holgus103.debtcollector.enums;

/**
 * Created by Kuba on 02/01/2017.
 */
public enum TransactionDirection{
    TheyOweMe,
    IOweThem,
    Both;

    @Override
    public String toString() {
        return super.toString().replaceAll("(?=\\p{Upper})", " ");
    }

    public static TransactionDirection getEnum(String value) {
        for(TransactionDirection v : values())
            if(v.toString().equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException();
    }

}
