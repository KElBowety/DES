public class Runner {
    public static void main(String[] args) {
        DES des = new DES("C0B7A8D05F3A829C", "AABB09182736CCDD");
        System.out.println(des.decrypt());
    }
}
