public enum CellState {
    ALIVE("██"),
    DEAD("  ");

    private String description;

    CellState(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
