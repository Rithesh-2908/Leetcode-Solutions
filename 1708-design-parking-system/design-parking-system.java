class ParkingSystem {
    int[] a;

    public ParkingSystem(int big, int medium, int small) {
        a = new int[]{0, big, medium, small};
    }

    public boolean addCar(int carType) {
        return a[carType]-- > 0;
    }
}