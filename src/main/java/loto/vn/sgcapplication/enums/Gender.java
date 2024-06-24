package loto.vn.sgcapplication.enums;

public enum Gender {
    F('F'),
    M('M');

    private char value;

    Gender(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }

    public static Gender fromChar(char value) {
        switch (value) {
            case 'M': return M;
            case 'F': return F;
            default: throw new IllegalArgumentException("Unknown gender: " + value);
        }
    }
}
